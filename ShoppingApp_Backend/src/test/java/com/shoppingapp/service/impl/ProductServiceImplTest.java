package com.shoppingapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shoppingapp.model.document.ProductInfo;
import com.shoppingapp.repository.ProductInfoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private KafkaTemplate<String, ProductInfo> kafkaTemplate;

    @MockBean
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    void testFindId() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<ProductInfo> ofResult = Optional.of(productInfo);
        when(productInfoRepository.findById((String) any())).thenReturn(ofResult);
        Optional<ProductInfo> actualFindIdResult = productServiceImpl.findId("42");
        assertSame(ofResult, actualFindIdResult);
        assertTrue(actualFindIdResult.isPresent());
        assertEquals("42", actualFindIdResult.get().getProductPrice().toString());
        verify(productInfoRepository).findById((String) any());
    }

    @Test
    void testFindOne() {
        ArrayList<ProductInfo> productInfoList = new ArrayList<>();
        when(productInfoRepository.findByProductName((String) any())).thenReturn(productInfoList);
        List<ProductInfo> actualFindOneResult = productServiceImpl.findOne("Product Name");
        assertSame(productInfoList, actualFindOneResult);
        assertTrue(actualFindOneResult.isEmpty());
        verify(productInfoRepository).findByProductName((String) any());
    }

    @Test
    void testFindAll() {
        ArrayList<ProductInfo> productInfoList = new ArrayList<>();
        when(productInfoRepository.findAll()).thenReturn(productInfoList);
        List<ProductInfo> actualFindAllResult = productServiceImpl.findAll();
        assertSame(productInfoList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(productInfoRepository).findAll();
    }

    @Test
    void testUpdate() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        when(productInfoRepository.save((ProductInfo) any())).thenReturn(productInfo);

        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setOrderQuantity(1);
        productInfo1.setProductDescription("Product Description");
        productInfo1.setProductFeatures("Product Features");
        productInfo1.setProductId("42");
        productInfo1.setProductName("Product Name");
        productInfo1.setProductPrice(BigDecimal.valueOf(42L));
        productInfo1.setProductQuantity(1);
        productInfo1.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo1.setUpdateTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        ProductInfo actualUpdateResult = productServiceImpl.update(productInfo1);
        assertSame(productInfo, actualUpdateResult);
        assertEquals("42", actualUpdateResult.getProductPrice().toString());
        verify(productInfoRepository).save((ProductInfo) any());
    }

    @Test
    void testSave() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        when(productInfoRepository.save((ProductInfo) any())).thenReturn(productInfo);

        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setOrderQuantity(1);
        productInfo1.setProductDescription("Product Description");
        productInfo1.setProductFeatures("Product Features");
        productInfo1.setProductId("42");
        productInfo1.setProductName("Product Name");
        productInfo1.setProductPrice(BigDecimal.valueOf(42L));
        productInfo1.setProductQuantity(1);
        productInfo1.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo1.setUpdateTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        ProductInfo actualSaveResult = productServiceImpl.save(productInfo1);
        assertSame(productInfo, actualSaveResult);
        assertEquals("42", actualSaveResult.getProductPrice().toString());
        verify(productInfoRepository).save((ProductInfo) any());
    }

    @Test
    void testDelete() {
        doNothing().when(productInfoRepository).deleteById((String) any());
        assertTrue(productServiceImpl.delete("42"));
        verify(productInfoRepository).deleteById((String) any());
    }

    @Test
    void testOrder() {
        ProducerRecord<String, ProductInfo> producerRecord = new ProducerRecord<>("Topic", new ProductInfo());

        when(kafkaTemplate.send((String) any(), (ProductInfo) any())).thenReturn(new AsyncResult<>(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1, 10L, 3, 3))));

        ProductInfo productInfo = new ProductInfo();
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<ProductInfo> ofResult = Optional.of(productInfo);

        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setOrderQuantity(1);
        productInfo1.setProductDescription("Product Description");
        productInfo1.setProductFeatures("Product Features");
        productInfo1.setProductId("42");
        productInfo1.setProductName("Product Name");
        productInfo1.setProductPrice(BigDecimal.valueOf(42L));
        productInfo1.setProductQuantity(1);
        productInfo1.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo1.setUpdateTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(productInfoRepository.save((ProductInfo) any())).thenReturn(productInfo1);
        when(productInfoRepository.findById((String) any())).thenReturn(ofResult);
        assertEquals("Order Placed Successfully", productServiceImpl.order("42"));

    }



    @Test
    void testSendMessage() {
        ProducerRecord<String, ProductInfo> producerRecord = new ProducerRecord<>("Topic", new ProductInfo());

        when(kafkaTemplate.send((String) any(), (ProductInfo) any())).thenReturn(new AsyncResult<>(
                new SendResult<>(producerRecord, new RecordMetadata(new TopicPartition("Topic", 1), 1L, 1, 10L, 3, 3))));

        ProductInfo productInfo = new ProductInfo();
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productServiceImpl.sendMessage(productInfo);
        verify(kafkaTemplate).send((String) any(), (ProductInfo) any());
    }

    @Test
    void testConsume() {
        ProductInfo productInfo = mock(ProductInfo.class);
        doNothing().when(productInfo).setOrderQuantity((Integer) any());
        doNothing().when(productInfo).setProductDescription((String) any());
        doNothing().when(productInfo).setProductFeatures((String) any());
        doNothing().when(productInfo).setProductId((String) any());
        doNothing().when(productInfo).setProductName((String) any());
        doNothing().when(productInfo).setProductPrice((BigDecimal) any());
        doNothing().when(productInfo).setProductQuantity((Integer) any());
        doNothing().when(productInfo).setProductStatus((String) any());
        doNothing().when(productInfo).setUpdateTime((Date) any());
        productInfo.setOrderQuantity(1);
        productInfo.setProductDescription("Product Description");
        productInfo.setProductFeatures("Product Features");
        productInfo.setProductId("42");
        productInfo.setProductName("Product Name");
        productInfo.setProductPrice(BigDecimal.valueOf(42L));
        productInfo.setProductQuantity(1);
        productInfo.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo.setUpdateTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productServiceImpl.consume(productInfo);
        verify(productInfo).setOrderQuantity((Integer) any());
        verify(productInfo).setProductDescription((String) any());
        verify(productInfo).setProductFeatures((String) any());
        verify(productInfo).setProductId((String) any());
        verify(productInfo).setProductName((String) any());
        verify(productInfo).setProductPrice((BigDecimal) any());
        verify(productInfo).setProductQuantity((Integer) any());
        verify(productInfo).setProductStatus((String) any());
        verify(productInfo).setUpdateTime((Date) any());
    }
}

