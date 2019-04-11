package com.api.services.helpers;

import com.common.models.MarkUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */


@Service("tokenUtils")
public class TokenUtils {

  @Value("${mark.token.secret}")
  private String secret;

  @Value("${mark.token.expiration}")
  private long expiration;

  /**
   * <b>This method returns user name from token</b>
 * @param token as string
 * @return user name as string from token
 */
public String getUsernameFromToken(String token) {
    String username = null;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      if(!ObjectUtils.isEmpty(claims)){
    	  username = claims.getSubject();
      }else{
    	  throw new AccessDeniedException(String.format("Invalid or expired authorization token : %s",
    	          token));
      }
      
    } catch (Exception e) {
      throw new AccessDeniedException(String.format("Invalid or expired authorization token : %s",
          token));
    }
    return username;
  }
  
  /**
   * <b>This method returns business unit id from token</b>
 * @param token as String
 * @return business unit id as String
 */
public String getCrnFromToken(String token) {
	    String crn = null;
	    try {
	      final Claims claims = this.getClaimsFromToken(token);
	      if(!ObjectUtils.isEmpty(claims)){
	    	  crn = claims.getId();
	      }
	      
	    } catch (Exception e) {
	      throw new AccessDeniedException(String.format("Invalid or expired authorization token : %s",
	          token));
	    }
	    return crn;
	  }

  /**
   * <b>This method returns created date of token from token </b>
 * @param token as String
 * @return Date 
 */
public Date getCreatedDateFromToken(String token) {
    Date created;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      created = new Date((Long) claims.get("created"));
    } catch (Exception e) {
      created = null;
    }
    return created;
  }

  /**
   * <b>This method returns expiration date of token </b>
 * @param token as String
 * @return Date
 */
public Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = this.getClaimsFromToken(token);
      expiration = claims.getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  private Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  private Date generateCurrentDate() {
    return new Date(System.currentTimeMillis());
  }

  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + this.expiration * 1000);
  }

  /**
   * <b>This method checks token expiration</b>
 * @param token as String
 * @return true if token alive, otherwise false
 */
public boolean isTokenExpired(String token) {
    final Date expiration = this.getExpirationDateFromToken(token);
    return expiration.after(this.generateCurrentDate());
  }

  /**
   * <b>This method generate token</b>
 * @param userDetails as HitechUser
 * @return String 
 */
public String generateToken(MarkUser userDetails) {
    Map<String, Object> claims = new HashMap<String, Object>();
    claims.put("sub", userDetails.getUsername());
    claims.put("created", this.generateCurrentDate());
    claims.put("jti", userDetails.getCrn());
    
    return this.generateToken(claims);
  }

  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
        .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, this.secret).compact();
  }
}

