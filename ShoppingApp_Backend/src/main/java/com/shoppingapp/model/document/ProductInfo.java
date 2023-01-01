package com.shoppingapp.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
public class ProductInfo implements Serializable {

    @Id
    private String productId;

    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String productStatus;
    private Integer orderQuantity;
    private Integer productQuantity;
    private String productFeatures;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private Date updateTime = new Date();

}
