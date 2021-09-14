package com.example.actuator.springbootactuator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.actuator.springbootactuator.jwtBancos;
import com.example.actuator.springbootactuator.jwtController;
import com.example.actuator.springbootactuator.jwtProveedores;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class OrderController {

    private static final String ORDER_SERVICE = "orderService";
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/api/transacciones")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder(String token){
    	String response="";
    	if(validarToken(token)) {
         response = restTemplate.getForObject("http://localhost:8080/api/transacciones", String.class);    	
        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
    		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    }
    
    public ResponseEntity<String> orderFallback(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }
    
    @PostMapping("/api/transacciones")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> createOrder2(@RequestBody Object persona, String token){
    	String response="";
    	if(validarToken(token)) {
    		   response = restTemplate.postForObject("http://localhost:8080/api/transacciones", persona, String.class);     	
    	        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
   		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    	    	}
    
    public ResponseEntity<String> orderFallback2(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }
    
    
    
    @GetMapping("/api/bancos")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> listBancos(String token){
    	String response="";
    	if(validarTokenBancos(token)) {
         response = restTemplate.getForObject("http://localhost:8080/api/bancos", String.class);    	
        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
    		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    }
    
    public ResponseEntity<String> orderFallback3(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }

    
    
    @GetMapping("/api/proveedores")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<String> listTransacciones(String token){
    	String response="";
    	if(validarTokenProveedores(token)) {
         response = restTemplate.getForObject("http://localhost:8080/api/proveedores", String.class);    	
        return new ResponseEntity<String>(response, HttpStatus.OK);
    	}else {
    		 return new ResponseEntity<String>(response, HttpStatus.OK);
    	}
    }
    
    public ResponseEntity<String> orderFallback4(Exception e){
        return new ResponseEntity<String>("Service is dow", HttpStatus.OK);

    }

    

    public boolean validarTokenProveedores(String token) {
		jwtProveedores op= new jwtProveedores();
		if(op.verifyToken(token)) {
			return true;
		}else {
			return false;
		}		
	}
    
    public boolean validarTokenBancos(String token) {
		jwtBancos op= new jwtBancos();
		if(op.verifyToken(token)) {
			return true;
		}else {
			return false;
		}		
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
