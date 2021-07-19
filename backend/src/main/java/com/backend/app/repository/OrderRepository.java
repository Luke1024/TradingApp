package com.backend.app.repository;

import com.backend.app.domain.entity.CurrencyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<CurrencyOrder, Long> {
}
