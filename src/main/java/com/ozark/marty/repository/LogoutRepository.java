package com.ozark.marty.repository;

import com.ozark.marty.entities.Logout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogoutRepository extends JpaRepository<Logout, Integer> {

    @Query("""
            select t from Logout t inner join Users u on t.user.id = u.id
            where u.id = :userID and (t.expired = false or t.revoked = false) 
            """)
    List<Logout> findAllValidTokensByUserID(Integer userID);

    Optional<Logout> findByToken(String token);
}
