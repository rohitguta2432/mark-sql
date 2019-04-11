package com.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.common.constants.Constants;
import com.api.dto.Response;
import com.api.exception.AuthenticationTokenException;
import com.common.models.AuthenticationToken;
import com.common.models.MarkUser;
import com.api.services.AuthenticationTokenService;
import com.api.services.helpers.TokenUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


/**
 * @author Rohit Raj
 *
 */
@Slf4j
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Value("${mark.auth.header}")
  private String tokenHeader;

  @Value("${mark.token.inactivity.time}")
  private int inactivityTime;

  @Autowired
  private TokenUtils tokenUtils;
  
  @Autowired
  private AuthenticationTokenService authenticationTokenService;
  
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String authToken = httpRequest.getHeader(this.tokenHeader);
    boolean isAdminRequest = httpRequest.getHeader(Constants.ADMIN_REQUEST) == null ? false : true;

    if (!StringUtils.isEmpty(authToken)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
    	
    	String username = "";
    	  if(isAdminRequest){
    		username =   authToken;
    	  }else{
    		  username = this.tokenUtils.getUsernameFromToken(authToken);
    		  
    		  MarkUser userDetails = (MarkUser) userDetailsService.loadUserByUsername(username);
    	        if (!isAdminRequest) {
    	          validateTokenForInactivity(authToken, userDetails);
    	        }
    	        UsernamePasswordAuthenticationToken authentication =
    	            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
    	        SecurityContextHolder.getContext().setAuthentication(authentication);
    	}
        
      } catch (Exception e) {
        log.error(e.getMessage());
        writeErrorToResponse(e, response);
        return;
      }
    }
    chain.doFilter(request, response);
  }

  private void validateTokenForInactivity(String authToken, MarkUser userDetails) {

	  AuthenticationToken existingAuthenticationToken =
		        authenticationTokenService.getAuthTokenByUserId(userDetails.getId());

		    if (existingAuthenticationToken == null) {
		    	log.info("No auth token found in DB.");
		    	throw new AuthenticationTokenException("No auth token found in DB.");
		    }

		    long timeDIff =
		        System.currentTimeMillis() - existingAuthenticationToken.getLastAccessed();

		    if (TimeUnit.MILLISECONDS.toMinutes(timeDIff) >= inactivityTime) {
		    	log.info("Token expired for user : ", userDetails.getId(), " because of inactivity.");
		    	authenticationTokenService.deleteAuthenticationToken(userDetails.getId());
		    	throw new AuthenticationTokenException(
		    			"User auth token expired because of inactivity on APP, please generate a new auth token");
		    } else {
		    	authenticationTokenService.updateLastAccessedTime(existingAuthenticationToken);
		    }
	  
	  log.debug("Token validating.");
  }

  private void writeErrorToResponse(Exception e, ServletResponse servletResponse)
      throws IOException {

    int statusCode = 401;

    if (e instanceof AuthenticationTokenException) {
//      statusCode = 900;
      statusCode = 401;
    }
    Response<String> response = new Response<String>(statusCode, e.getMessage(), null);

    try (PrintWriter writer = servletResponse.getWriter()) {
      writer.write(OBJECT_MAPPER.writeValueAsString(response));
      writer.flush();
    }

  }
}
