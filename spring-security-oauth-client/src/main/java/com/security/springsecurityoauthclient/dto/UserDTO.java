package com.security.springsecurityoauthclient.dto;

import lombok.*;

/**
 * @author Richa Pokhrel
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String matchingPassword;
}
