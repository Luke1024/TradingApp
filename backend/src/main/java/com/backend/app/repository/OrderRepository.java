package com.backend.app.repository;

import com.backend.app.domain.entity.CurrencyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<CurrencyOrder, Long> {

    Optional<CurrencyOrder> findByIdArchivedFalse(@Param("ID") long id);
}
