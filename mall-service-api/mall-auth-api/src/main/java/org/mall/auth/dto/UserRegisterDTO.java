package org.mall.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author sxs
 * @since 2023/2/11
 */
@ToString
@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 6,max = 18,message = "用户名必须是6-18位字符")
    private String userName;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码必须是6-18位字符")
    private String password;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号格式错误")
    private String phone;
    @NotBlank(message = "验证码不能为空")
    private String code;

}
