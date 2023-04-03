package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.mongo.data.PurchaseOrderData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PurchaseOrderMongoDBRepository extends ReactiveMongoRepository<PurchaseOrderData, String> {
}
