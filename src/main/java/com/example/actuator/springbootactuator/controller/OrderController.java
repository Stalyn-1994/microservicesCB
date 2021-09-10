package com.example.actuator.springbootactuator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.actuator.springbootactuator.jwtController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class OrderController {

    private static final String ORDER_SERVICE = "orderService";
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/rest/dataTrainee")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder(String token){
    	String response="";
    	if(validarToken(token)) {
         response = restTemplate.getForObject("http://gateway:8080/api/transaccion/rest/dataTrainee", String.class);    	
        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
    		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    }
    public ResponseEntity<String> orderFallback(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }
    
    @PostMapping("/rest/dataTrainee")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder2(@RequestBody Object persona, String token){
    	String response="";
    	if(validarToken(token)) {
    		   response = restTemplate.postForObject("http://gateway:8080/api/transaccion/rest/dataTrainee", persona, String.class); //.getForObject("http://localhost:9095/rest/dataTrainee", String.class);    	
    	        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
   		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    	
      
    	}
    public ResponseEntity<String> orderFallback2(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }
    

    public boolean validarToken(String token) {
		jwtController op= new jwtController();
		if(op.verifyToken(token)) {
			return true;
		}else {
			return false;
		}		
	}



}
