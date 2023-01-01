package com.shoppingapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.model.document.ProductInfo;
import com.shoppingapp.model.document.Users;
import com.shoppingapp.security.JWT.JwtUtil;
import com.shoppingapp.service.ProductService;
import com.shoppingapp.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;


    @Test
    void testFindAllValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");
        when(productService.findAll()).thenReturn(new ArrayList<>());

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("ROLE_CUSTOMER");
        when(userService.getuserByloginId((String) any())).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shopping/all")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testFindAllInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        when(userService.getuserByloginId((String) any())).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shopping/all")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testCreateValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

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
        Optional<ProductInfo> ofResult = Optional.of(productInfo1);
        when(productService.save((ProductInfo) any())).thenReturn(productInfo);
        when(productService.findId((String) any())).thenReturn(ofResult);

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("ROLE_ADMIN");
        when(userService.getuserByloginId((String) any())).thenReturn(users);

        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setOrderQuantity(1);
        productInfo2.setProductDescription("Product Description");
        productInfo2.setProductFeatures("Product Features");
        productInfo2.setProductId("42");
        productInfo2.setProductName("Product Name");
        productInfo2.setProductPrice(BigDecimal.valueOf(42L));
        productInfo2.setProductQuantity(1);
        productInfo2.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo2.setUpdateTime(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        String content = (new ObjectMapper()).writeValueAsString(productInfo2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shopping/product/add")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Product With Product Id Already Exists"));
    }

    @Test
    void testCreateInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        when(userService.getuserByloginId((String) any())).thenReturn(users);

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
        String content = (new ObjectMapper()).writeValueAsString(productInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shopping/product/add")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Token Expired Or UnAuthorized "));
    }

    @Test
    void testDeleteValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

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
        when(productService.delete((String) any())).thenReturn(true);
        when(productService.findId((String) any())).thenReturn(ofResult);

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("ROLE_ADMIN");
        when(userService.getuserByloginId((String) any())).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/shopping/product/delete/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testDeleteInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        when(userService.getuserByloginId((String) any())).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/shopping/product/delete/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Token Expired Or UnAuthorized "));
    }


    @Test
    void testEditValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

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
        when(productService.update((ProductInfo) any())).thenReturn(productInfo1);
        doNothing().when(productService).sendMessage((ProductInfo) any());
        when(productService.findId((String) any())).thenReturn(ofResult);

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("ROLE_ADMIN");
        when(userService.getuserByloginId((String) any())).thenReturn(users);

        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setOrderQuantity(1);
        productInfo2.setProductDescription("Product Description");
        productInfo2.setProductFeatures("Product Features");
        productInfo2.setProductId("42");
        productInfo2.setProductName("Product Name");
        productInfo2.setProductPrice(BigDecimal.valueOf(42L));
        productInfo2.setProductQuantity(1);
        productInfo2.setProductStatus("Product Status");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productInfo2.setUpdateTime(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        String content = (new ObjectMapper()).writeValueAsString(productInfo2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/shopping/product/update/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":\"42\",\"productName\":\"Product Name\",\"productPrice\":42,\"productDescription\":\"Product"
                                        + " Description\",\"productStatus\":\"Product Status\",\"orderQuantity\":1,\"productQuantity\":1,\"productFeatures"
                                        + "\":\"Product Features\",\"updateTime\":0}"));
    }

    @Test
    void testEditInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(jwtUtil.extractUsername((String) any())).thenReturn("testnametest");

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        when(userService.getuserByloginId((String) any())).thenReturn(users);

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
        String content = (new ObjectMapper()).writeValueAsString(productInfo);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/shopping/product/update/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Token Expired Or UnAuthorized "));
    }

    @Test
    void testOrderPlacedValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);

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
        when(productService.order((String) any())).thenReturn("Order");
        when(productService.findId((String) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shopping/product/Order/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Order"));
    }

    @Test
    void testOrderPlacedInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(productService.order((String) any())).thenReturn("Order");
        when(productService.findId((String) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shopping/product/Order/{id}", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Product With Product Id Doesn't Exists"));
    }


    @Test
    void testShowOneValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(true);
        when(productService.findOne((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/shopping/products/search/{productName}", "Product Name")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testShowOneInValid() throws Exception {
        when(jwtUtil.validateToken((String) any())).thenReturn(false);
        when(productService.findOne((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/shopping/products/search/{productName}", "Product Name")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}

