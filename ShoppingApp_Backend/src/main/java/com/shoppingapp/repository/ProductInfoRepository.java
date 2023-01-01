package com.shoppingapp.repository;

import com.shoppingapp.model.document.ProductInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;


public interface ProductInfoRepository extends MongoRepository<ProductInfo, String> {
    @Query("{'productName':{'$regex':'?0','$options':'i'}}")
    List<ProductInfo> findByProductName(String Name);



}
