package com.backend.app.repository;


import com.backend.app.domain.entity.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.token=:TOKEN")
    Optional<AppUser> findByToken(@Param("TOKEN") String token);

}
