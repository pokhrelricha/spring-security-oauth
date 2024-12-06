package com.security.springsecurityoauthclient.controller;

import com.security.springsecurityoauthclient.dto.PasswordDTO;
import com.security.springsecurityoauthclient.dto.UserDTO;
import com.security.springsecurityoauthclient.event.RegistrationCompleteEvent;
import com.security.springsecurityoauthclient.model.entity.User;
import com.security.springsecurityoauthclient.model.entity.VerificationToken;
import com.security.springsecurityoauthclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Richa Pokhrel
 */
@Slf4j
@RestController
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Registers a new user and triggers an account verification email.
     *
     * @param userDTO The user model containing registration details.
     * @param request   The HTTP servlet request.
     * @return A success message indicating the registration process was initiated.
     */
    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO userDTO, final HttpServletRequest request) {
        User user = userService.registerUser(userDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Registration Successful. Check your email for verification.";
    }

    /**
     * Verifies a user account using a registration token.
     *
     * @param token The verification token sent to the user's email.
     * @return A message indicating whether the verification was successful or failed.
     */
    @GetMapping("/verify-registration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        return result.equalsIgnoreCase("valid") ? "User Verified Successfully" : "Invalid or Expired Token";
    }

    /**
     * Resends the account verification token to the user's email.
     *
     * @param oldToken The expired or invalid verification token.
     * @param request  The HTTP servlet request.
     * @return A message indicating that the verification link has been sent.
     */
    @GetMapping("/resend/verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent Successfully";
    }

    /**
     * Initiates the password reset process for a user.
     *
     * @param passwordDTO The password model containing the user's email.
     * @param request       The HTTP servlet request.
     * @return A URL for resetting the password if the email exists, or an empty string otherwise.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordDTO.getEmail());
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            return passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return "Invalid Email Address";
    }

    /**
     * Saves the new password after successful token validation.
     *
     * @param token          The password reset token.
     * @param passwordDTO  The password model containing the new password.
     * @return A message indicating whether the password reset was successful or failed.
     */
    @PostMapping("/save/password")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordDTO passwordDTO) {
        String result = userService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            return "Invalid or Expired Token";
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordDTO.getNewPassword());
            return "Password Reset Successfully";
        }
        return "Invalid Token";
    }

    /**
     * Changes the user's password after validating the old password.
     *
     * @param passwordDTO The password model containing the old and new passwords.
     * @return A message indicating whether the password change was successful or failed.
     */
    @PostMapping("/change/password")
    public String changePassword(@RequestBody PasswordDTO passwordDTO) {
        User user = userService.findUserByEmail(passwordDTO.getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDTO.getOldPassword())) {
            return "Invalid Old Password";
        }

        userService.changePassword(user, passwordDTO.getNewPassword());
        return "Password Changed Successfully";
    }

    /**
     * Sends an email with a password reset link.
     *
     * @param user           The user who requested the password reset.
     * @param applicationUrl The application base URL.
     * @param token          The password reset token.
     * @return The password reset URL.
     */
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;
        log.info("Click the link to reset your password: {}", url);
        return url;
    }

    /**
     * Sends an email with a new account verification link.
     *
     * @param user           The user who requested the new verification token.
     * @param applicationUrl The application base URL.
     * @param verificationToken The new verification token.
     */
    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account: {}", url);
    }

    /**
     * Constructs the application base URL from the HTTP servlet request.
     *
     * @param request The HTTP servlet request.
     * @return The application base URL.
     */
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
