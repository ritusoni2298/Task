package com.emailpart.emaildemo.repository;

import com.emailpart.emaildemo.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    public ConfirmationToken findByConfirmationToken(String confirmationToken);

}
