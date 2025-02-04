package com.hunt.dao;

import com.hunt.entity.EmailToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTokenDAO extends CrudRepository<EmailToken, String> {
    List<EmailToken> findByEmailAddressOrderByCreatedAt(String emailAddress);

}
