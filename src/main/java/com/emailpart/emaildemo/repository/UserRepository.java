package com.emailpart.emaildemo.repository;

import com.emailpart.emaildemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmailIdIgnoreCase(String emailId);

}
