package com.shoppingapp.service;


import com.shoppingapp.model.document.ProductInfo;
import com.shoppingapp.service.impl.ProductServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductInfo> findOne(String productName);
    List<ProductInfo> findAll();
    Optional<ProductInfo> findId(String productId);
    ProductInfo update(ProductInfo productInfo);
    ProductInfo save(ProductInfo productInfo);
    public boolean delete(String productId);

    String order(String productId);

//    void sendMessage(ProductInfo productInfo);
//
//    @KafkaListener(topics = ProductServiceImpl.TOPIC_NAME,
//            groupId = ProductServiceImpl.GROUP_ID)
//    void consume(ProductInfo productInfo);
}
