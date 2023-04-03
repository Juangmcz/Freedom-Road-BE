package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.mongo.data.CustomerData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerMongoDBRepository extends ReactiveMongoRepository<CustomerData, String> {
}
