package org.mall.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sxs
 * @since 2023/2/14
 */
@ToString
@Setter
@Getter
public class GithubDTO {
    private String access_token;
    private String scope;
    private String token_type;
    private String error;
    private String error_description;
    private String error_uri;
}
