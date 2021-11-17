package com.backend.app.repository;

import com.backend.app.domain.entity.CurrencyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<CurrencyOrder, Long> {

    @Query("SELECT o FROM CurrencyOrder o WHERE o.id=:ID AND o.orderState!=3")
    Optional<CurrencyOrder> findByIdArchivedFalse(@Param("ID") long id);
}
