package com.security.springsecurityoauthclient.model.repository;

import com.security.springsecurityoauthclient.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Richa Pokhrel
 */
@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
