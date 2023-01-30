package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.business.loginUser.LoginUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserPersistence extends CrudRepository<LoginUser, Integer> {
    Optional<LoginUser> findByUsername(String username);
}
