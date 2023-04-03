package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.mongo.data.BusTicketData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BusTicketMongoDBRepository extends ReactiveMongoRepository<BusTicketData, String> {
}
