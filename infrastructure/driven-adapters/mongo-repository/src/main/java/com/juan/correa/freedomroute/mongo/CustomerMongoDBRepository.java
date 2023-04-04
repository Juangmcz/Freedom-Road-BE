package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.mongo.data.CustomerData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerMongoDBRepository extends ReactiveMongoRepository<CustomerData, String> {

    Mono<CustomerData> findByEmail(String email);
}
