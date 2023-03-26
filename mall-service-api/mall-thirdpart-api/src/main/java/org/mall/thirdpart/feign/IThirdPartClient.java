package org.mall.thirdpart.feign;

import org.mall.common.pojo.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sxs
 * @since 2023/2/11
 */
@FeignClient("mall-third-part")
public interface IThirdPartClient {


    @GetMapping("sms/sendCode")
    ResponseEntity sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code);
}
