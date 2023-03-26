package org.mall.auth.service;

/**
 * @author sxs
 * @since 2023/2/14
 */
public interface OAuthService {
    String github(String code);
}
