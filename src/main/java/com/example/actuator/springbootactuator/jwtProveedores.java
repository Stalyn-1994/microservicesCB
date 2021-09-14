package com.example.actuator.springbootactuator;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RequestMapping(path="/api/proveedores")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
public class jwtProveedores {
	private static final String SECRET_KEY="84DAF5295E5BB1DF";
	
	@GetMapping("/gettoken")
	public String getToken() {
		 Calendar fecha = Calendar.getInstance();
		  // Incrementa 30 segundos la fecha 
		  fecha.add(Calendar.SECOND, 120);
		  Date expirationDate= fecha.getTime();
			Date issuedTime = new Date(System.currentTimeMillis());
			String token= Jwts.builder()
					.setSubject("Token")
					.setIssuedAt(issuedTime)
					.setIssuer("Proveedores")
					.setExpiration(expirationDate)
					.claim("TEST_CLAIM","Proveedores")
					.signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();			
			return token;
	}	
	
	
	public boolean verifyToken( String token) {
		System.out.println("Token Verification initiated proveedores");
		System.out.println(token);
		boolean response=false;
		try {
			Claims claims= Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(token)
					.getBody();
			if(claims.get("TEST_CLAIM").equals("Proveedores")) {
				response =true;
			}
			
		}catch(ExpiredJwtException e){
			System.out.println("Token Was Expired");
			System.out.println(e.getClaims().getIssuer());
		}catch(Exception e) {
			response=false;
			System.out.println("error:"+e);
			System.out.println("Error while parsing token");
		}
		
		return response;
	}

}
