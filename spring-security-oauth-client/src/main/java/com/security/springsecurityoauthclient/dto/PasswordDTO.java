package com.security.springsecurityoauthclient.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Richa Pokhrel
 */
@Getter
@Setter
public class PasswordDTO {

    private String email;
    private String oldPassword;
    private String newPassword;
}
