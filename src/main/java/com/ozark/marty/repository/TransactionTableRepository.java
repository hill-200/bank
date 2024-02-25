package com.ozark.marty.repository;

import com.ozark.marty.table.AccountTable;
import com.ozark.marty.table.TransactionTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionTableRepository extends JpaRepository<TransactionTable, Integer> {


}
