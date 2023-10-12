package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gateway.dto.ProductDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String PRODUCT_URL = "http://localhost:8081/product";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService accessLogService;

    @Autowired
    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService accessLogService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<?> getCustomers() throws JsonProcessingException {
//        akses API customer dan dapatkan datanya
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<>() {});
//            ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//            kirim accesslog
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL, response.getStatusCodeValue(), LocalDateTime.now(),"Successful"); //Create an access log
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL, ex.getRawStatusCode(), LocalDateTime.now(), "Error: " + ex.getLocalizedMessage());
            accessLogService.logAccess(accessLog);
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    //    Buat
    @PostMapping("/createProduct")
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("POST", PRODUCT_URL, response.getStatusCodeValue(), LocalDateTime.now(),"Successful"); //Create an access log
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            AccessLog accessLog = new AccessLog("POST", PRODUCT_URL, ex.getRawStatusCode(), LocalDateTime.now(), "Error: " + ex.getLocalizedMessage());
            accessLogService.logAccess(accessLog);
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    //    delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductId(@PathVariable String id) throws JsonProcessingException {
        try {
            ResponseEntity<Void> response = restTemplateUtil.delete(PRODUCT_URL+"/"+id);
//            ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//            kirim accesslog
            AccessLog accessLog = new AccessLog("DELETE", PRODUCT_URL+"/"+id, response.getStatusCodeValue(), LocalDateTime.now(),"Successful"); //Create an access log
            accessLogService.logAccess(accessLog);
            return ResponseEntity.notFound().build();
        } catch (HttpClientErrorException ex) {
            AccessLog accessLog = new AccessLog("DELETE", PRODUCT_URL+"/"+id, ex.getRawStatusCode(), LocalDateTime.now(), "Error: " + ex.getLocalizedMessage());
            accessLogService.logAccess(accessLog);
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.notFound().build();
        }
    }

//    put product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct( @PathVariable String id, @RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.put(PRODUCT_URL+"/"+id, productDTO, ProductDTO.class);
            AccessLog accessLog = new AccessLog("PUT", PRODUCT_URL+"/"+id, response.getStatusCodeValue(), LocalDateTime.now(),"Successful"); //Create an access log
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            AccessLog accessLog = new AccessLog("PUT", PRODUCT_URL+"/"+id, ex.getRawStatusCode(), LocalDateTime.now(), "Error: " + ex.getLocalizedMessage());
            accessLogService.logAccess(accessLog);
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    //    get id product
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL+"/"+id, new ParameterizedTypeReference<>() {});
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL+"/"+id, response.getStatusCodeValue(), LocalDateTime.now(),"Successful"); //Create an access log
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException ex) {
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL+"/"+id, ex.getRawStatusCode(), LocalDateTime.now(), "Error: " + ex.getLocalizedMessage());
            accessLogService.logAccess(accessLog);
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
}
