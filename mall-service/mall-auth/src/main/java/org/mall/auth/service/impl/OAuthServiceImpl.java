package org.mall.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mall.auth.dto.GithubDTO;
import org.mall.auth.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;

/**
 * @author sxs
 * @since 2023/2/14
 */
@Service
@Slf4j
public class OAuthServiceImpl implements OAuthService {

    @Override
    public String github(@NotEmpty String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://github.com/login/oauth/access_token";
        HashMap<String, String> map = new HashMap<>();
        map.put("client_id","13f28954a65a99b9cc57");
        map.put("client_secret","762b33babf27b70736841016cf4160a391224ece");
        map.put("code",code);
        try {
            //此 code 交换为访问令牌：
            ResponseEntity<GithubDTO> response = restTemplate.postForEntity(url, null, GithubDTO.class, map);
            if (response.getStatusCodeValue() == 200) {
                System.out.println(response.getBody().toString());
            }else {
                log.error("{}",response.getStatusCodeValue());
                return "redirect:http://auth.mall.org/login.html";
            }
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return "redirect:http://auth.mall.org/login.html";
        }
        return "redirect:http://mall.org";
    }
}
