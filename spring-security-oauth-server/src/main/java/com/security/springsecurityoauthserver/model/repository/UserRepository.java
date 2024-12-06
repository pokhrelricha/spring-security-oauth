package com.security.springsecurityoauthserver.model.repository;

import com.security.springsecurityoauthserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Richa Pokhrel
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username =:username and u.deleted != 'Y'")
    User findByUsername(String username);
}
