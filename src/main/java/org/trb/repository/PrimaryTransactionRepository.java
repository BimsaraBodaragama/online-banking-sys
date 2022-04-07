package org.trb.repository;

import org.trb.model.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();
}
