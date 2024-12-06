package com.security.springsecurityoauthclient.service;

import com.security.springsecurityoauthclient.dto.UserDTO;
import com.security.springsecurityoauthclient.model.entity.User;
import com.security.springsecurityoauthclient.model.entity.VerificationToken;

import java.util.Optional;

/**
 * @author Richa Pokhrel
 */
public interface UserService {

    User registerUser(UserDTO userDTO);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
