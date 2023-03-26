package org.mall.thirdpart.service;

/**
 * @author sxs
 * @since 2023/2/11
 */
public interface SmsService {
    void sendCode(String phone, String code);
}
