package org.trb.repository;

import org.trb.model.SavingsTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Long> {

    List<SavingsTransaction> findAll();
}

