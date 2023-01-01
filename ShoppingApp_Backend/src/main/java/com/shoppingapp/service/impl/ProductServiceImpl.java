package com.shoppingapp.service.impl;


import com.shoppingapp.model.document.ProductInfo;
import com.shoppingapp.repository.ProductInfoRepository;
import com.shoppingapp.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    private KafkaTemplate<String,ProductInfo> kafkaTemplate;



    public static final String TOPIC_NAME = "shopping";
    public static final String GROUP_ID = "product";
    @Override
    public Optional<ProductInfo> findId(String productId) {
        Optional<ProductInfo> productInfo = productInfoRepository.findById(productId);
        return productInfo;
    }

    @Override
    public List<ProductInfo> findOne(String productName) {
        if(productName == null){
          return productInfoRepository.findAll() ;
        }
        return productInfoRepository.findByProductName(productName) ;
    }


    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findAll();
    }


    @Override
    public ProductInfo update(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }


    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }


    @Override
    public boolean delete(String productId) {

        productInfoRepository.deleteById(productId);

        return true;
    }
    public String ProductStatus(int ProductQuantity){
        if(ProductQuantity <= 0){
            return "OUT OF STOCK";
        } else if (ProductQuantity <= 5) {
            return "HURRY UP TO PURCHASE";
        }else {
            return "AVAILABLE";
        }
    }

    @Override
    @Transactional
    public String order(String productId){
        ProductInfo productInfo = productInfoRepository.findById(productId).get();
        if(productInfo.getProductQuantity() == 0){
            return " Sorry Product Is Out of Stock";
        }else{
            productInfo.setOrderQuantity(productInfo.getOrderQuantity() + 1);
            productInfo.setProductQuantity(productInfo.getProductQuantity() - 1);
            productInfo.setProductStatus(ProductStatus(productInfo.getProductQuantity() - 1));
//            sendMessage(productInfo);
            productInfoRepository.save(productInfo);
            return  "Order Placed Successfully";
        }
    }

//     @Override
//    public void sendMessage(ProductInfo productInfo)
//    {
//        log.info(String.format("Message sent -> %s", productInfo));
//        this.kafkaTemplate.send(TOPIC_NAME, productInfo);
//
//    }
//
//    @KafkaListener(topics = TOPIC_NAME,
//            groupId = GROUP_ID)
//    @Override
//    public void consume(ProductInfo productInfo)
//    {
//        log.info(String.format("Message recieved -> %s", productInfo));
////        productInfoRepository.save(productInfo);
//    }
}
