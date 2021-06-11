package com.backend.app.repository;

import com.backend.app.domain.DataPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, Long> {

}
