package org.mall.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.auth.constant.AuthConstant;
import org.mall.auth.dto.GithubDTO;
import org.mall.auth.service.OAuthService;
import org.mall.common.constant.CommonConstant;
import org.mall.common.constant.ErrorCode;
import org.mall.member.dto.GithubUserDTO;
import org.mall.member.feign.IMemberClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Slf4j
@Controller
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;
    private final IMemberClient memberClient;

    private final StringRedisTemplate redisTemplate;

    public OAuthController(OAuthService oAuthService, IMemberClient memberClient, StringRedisTemplate redisTemplate) {
        this.oAuthService = oAuthService;
        this.memberClient = memberClient;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/github")
    public String github(@NotEmpty String code, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://github.com/login/oauth/access_token?client_id=13f28954a65a99b9cc57&client_secret=762b33babf27b70736841016cf4160a391224ece&code=" + code;
        try {
            //此 code 交换为访问令牌：
            ResponseEntity<GithubDTO> response = restTemplate.postForEntity(url, null, GithubDTO.class);
            if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
                GithubDTO body = response.getBody();
                String access_token = body.getAccess_token();
                if (StringUtils.isBlank(access_token)) {
                    return "redirect:http://auth.mall.org/login.html";
                }
                //用access_token获取用户信息
                String userUrl = "https://api.github.com/user";
                String Authorization = "Bearer "+access_token;
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Authorization", Authorization);
                ResponseEntity<GithubUserDTO> response2 = restTemplate.exchange(userUrl, HttpMethod.GET, new HttpEntity(httpHeaders), GithubUserDTO.class);
                if (response2.getStatusCodeValue() == HttpStatus.OK.value()) {
                    GithubUserDTO githubUser = response2.getBody();
                    //去mall-member
                    //TODO feign
                    org.mall.common.pojo.ResponseEntity responseEntity = memberClient.loginByGithub(githubUser);
                    if (!responseEntity.getCode().equals(ErrorCode.OK.getCode())) {
                        log.error(responseEntity.toString());
                        return "redirect:http://auth.mall.org/login.html";
                    }
                    //成功调用feign 向redis插入token
                    redisTemplate.opsForValue().set(AuthConstant.GITHUB_ACCESS_TOKEN,access_token);
                    session.setAttribute(CommonConstant.LOGIN_SESSION_KEY,responseEntity.getData());
                    log.info("用户登录成功：{}",responseEntity.getData().toString());
                }else {
                    log.error(response2.toString());
                    return "redirect:http://auth.mall.org/login.html";
                }
            }else {
                log.error(response.toString());
                return "redirect:http://auth.mall.org/login.html";
            }
        } catch (Exception e) {
            log.error(e.toString());
            return "redirect:http://auth.mall.org/login.html";
        }
        return "redirect:http://mall.org";
    }


}
