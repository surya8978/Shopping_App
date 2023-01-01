package com.shoppingapp.controller;

import com.shoppingapp.model.document.ProductInfo;
import com.shoppingapp.model.document.Users;
import com.shoppingapp.security.JWT.JwtUtil;
import com.shoppingapp.service.ProductService;
import com.shoppingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("shopping")
public class ProductController {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductInfo>> findAll(@RequestHeader(name = "Authorization") String token) {
        log.info("Entering into AllProducts method");
        if (null != token && jwtUtil.validateToken(token)) {
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/products/search/{productName}")
    public ResponseEntity<List<ProductInfo>> showOne(@RequestHeader(name = "Authorization") String token,
                                                     @PathVariable("productName") String productName) {
        if (null != token && jwtUtil.validateToken(token)) {
            return new ResponseEntity<>(productService.findOne(productName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }
    @GetMapping("/products/searchone/{productId}")
    public ResponseEntity<Optional<ProductInfo>> showOneByid(@RequestHeader(name = "Authorization") String token,
                                                     @PathVariable("productId") String productId) {
        if (null != token && jwtUtil.validateToken(token)) {
            return new ResponseEntity<>(productService.findId(productId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/product/add")
    public ResponseEntity<?> create(@RequestHeader(name = "Authorization") String token,
                                    @RequestBody ProductInfo product) {
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if (null != token && jwtUtil.validateToken(token) && user.getRole().equals("ROLE_ADMIN")) {
            Optional<ProductInfo> productIdExists = productService.findId(product.getProductId());

            if (productIdExists.stream().count() == 0) {
                return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product With Product Id Already Exists", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("Token Expired Or UnAuthorized ", HttpStatus.UNAUTHORIZED);
        }

    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> edit(@RequestHeader(name = "Authorization") String token,
                                  @PathVariable("id") String productId,
                                  @RequestBody ProductInfo product) {
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if (null != token && jwtUtil.validateToken(token) && user.getRole().equals("ROLE_ADMIN")) {
            Optional<ProductInfo> productIdExists = productService.findId(productId);
            if (productIdExists.stream().count() == 0) {
                return new ResponseEntity<>("Product With Product Id Doesn't Exists", HttpStatus.FORBIDDEN);
            } else {
                product.setProductId(productId);
                ProductInfo productInfo = productService.update(product);
//                productService.sendMessage(productInfo);
                return new ResponseEntity<>(productInfo, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Token Expired Or UnAuthorized ", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String token,
                                    @PathVariable("id") String productId) {
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if (null != token && jwtUtil.validateToken(token) && user.getRole().equals("ROLE_ADMIN")) {
            Optional<ProductInfo> productIdExists = productService.findId(productId);
            if (productIdExists.stream().count() == 0) {
                return new ResponseEntity<>("Product With Product Id Doesn't Exists", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(productService.delete(productId), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Token Expired Or UnAuthorized ", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/product/Order/{id}")
    public ResponseEntity<?> OrderPlaced(@RequestHeader(name = "Authorization") String token,
                                         @PathVariable("id") String productId) {
        if (null != token && jwtUtil.validateToken(token)) {
            Optional<ProductInfo> productIdExists = productService.findId(productId);
            if (productIdExists.stream().count() == 0) {
                return new ResponseEntity<>("Product With Product Id Doesn't Exists", HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(productService.order(productId), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Token Expired Or UnAuthorized ", HttpStatus.UNAUTHORIZED);
        }

    }
}