package org.mall.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.auth.constant.AuthConstant;
import org.mall.auth.dto.UserRegisterDTO;
import org.mall.auth.service.LoginService;
import org.mall.common.constant.CommonConstant;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.Assert;
import org.mall.common.util.ErrorCodeUtil;
import org.mall.common.util.RandomCode;
import org.mall.member.dto.UserLoginDTO;
import org.mall.member.entity.Member;
import org.mall.member.feign.IMemberClient;
import org.mall.thirdpart.feign.IThirdPartClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final IThirdPartClient thirdPartClient;
    private final IMemberClient memberClient;

    private final StringRedisTemplate redisTemplate;

    public LoginServiceImpl(IThirdPartClient thirdPartClient, IMemberClient memberClient, StringRedisTemplate redisTemplate) {
        this.thirdPartClient = thirdPartClient;
        this.memberClient = memberClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendCode(String phone) {
        Assert.notBlank(phone, "手机号不能为空");
        if (!Pattern.matches("^1[3-9]\\d{9}$", phone)) {
            throw new BusinessException(ErrorCode.USER_PHONE_ERROR, "手机号格式错误");
        }
        //TODO 接口防刷
        String oldCode = redisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(oldCode)) {
            if (System.currentTimeMillis() - Long.valueOf(oldCode.split("_")[1]) < 60 * 1000) {
                throw new BusinessException(ErrorCode.USER_SEND_CODE_BUSY);
            }
        }
        String code = RandomCode.getSixNumCode() + "_" + System.currentTimeMillis();
        try {
            //TODO feign远程调用
            ResponseEntity response = thirdPartClient.sendCode(phone, code);
            if (ErrorCode.OK.getCode().equals(response.getCode())) {
                //验证码五分钟内有效
                redisTemplate.opsForValue().set(AuthConstant.SMS_CODE_CACHE_PREFIX + phone, code, 5, TimeUnit.MINUTES);
                log.info("发送验证码成功：{}，{}", phone, code);
            } else {
                log.error(response.toString());
                throw new BusinessException(ErrorCode.THIRD_PART_SERVICE_ERROR, "系统繁忙请稍后重试");
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new BusinessException(ErrorCode.THIRD_PART_SERVICE_ERROR, "系统繁忙请稍后重试");
        }
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        //取出并删除key 防止重复注册
        String code = redisTemplate.opsForValue().getAndDelete(AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterDTO.getPhone());
        Assert.notBlank(code, ErrorCode.USER_CODE_ERROR, null);
        if (StringUtils.isBlank(code) || !code.split("_")[0].equals(userRegisterDTO.getCode())) {
            throw new BusinessException(ErrorCode.USER_CODE_ERROR);
        }
        redisTemplate.delete(AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterDTO.getPhone());

        Member member = new Member();
        member.setUsername(userRegisterDTO.getUserName());
        member.setPassword(userRegisterDTO.getPassword());
        member.setMobile(userRegisterDTO.getPhone());
        //TODO feign远程调用 注册
        ResponseEntity response = memberClient.register(member);
        if (!response.getCode().equals(ErrorCode.OK.getCode())) {
            log.error(response.toString());
            throw new BusinessException(ErrorCodeUtil.getErrorCode(response.getCode()));
        }

    }

    @Override
    public void login(UserLoginDTO userLoginDTO, HttpSession httpSession) {
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        ResponseEntity response = null;
        if (Pattern.matches("^1[3-9]\\d{9}$", account)) {
            //TODO feign
            response = memberClient.getByPhone(account);
        } else {
            //TODO feign
            response = memberClient.getByUsername(account);
        }
        if (response.getCode().equals(ErrorCode.OK.getCode())) {
            Member member = (Member) response.getData();
            Assert.notNull(member,ErrorCode.USER_ACCOUNT_ERROR,"");
            if (!new BCryptPasswordEncoder().matches(password, member.getPassword())) {
                throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
            }
            httpSession.setAttribute(CommonConstant.LOGIN_SESSION_KEY,member);
            log.info("用户登陆成功：{}", member.toString());
        } else {
            throw new BusinessException(ErrorCodeUtil.getErrorCode(response.getCode()));
        }

    }


}
