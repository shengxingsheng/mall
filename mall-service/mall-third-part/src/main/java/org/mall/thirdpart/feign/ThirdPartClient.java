package org.mall.thirdpart.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.thirdpart.service.SmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @since 2023/2/11
 */
@RestController
public class ThirdPartClient implements IThirdPartClient{

    private final SmsService smsService;

    public ThirdPartClient(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    @GetMapping("/sms/sendCode")
    public ResponseEntity sendCode(@RequestParam("phone") String phone, @RequestParam("code")String code) {
        smsService.sendCode(phone, code);
        return ResponseEntity.ok();
    }
}
