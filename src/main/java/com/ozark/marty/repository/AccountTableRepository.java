package com.ozark.marty.repository;

import com.ozark.marty.table.AccountTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountTableRepository extends JpaRepository<AccountTable, String> {

    @Query("""
            select t from AccountTable t inner join Users u on t.userID.id = u.id
            where u.id = :userID 
            """)
    List<AccountTable> findAllAccountsByUserID(Integer userID);

    Optional<AccountTable> findByAccountID(String accountID);






}
