package org.trb.repository;

import org.trb.model.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PrimaryTransactionRepository extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();

    Optional<Collection<PrimaryTransaction>> findByDateBetween(Date fromDate,
                                                               Date toDate);
}
