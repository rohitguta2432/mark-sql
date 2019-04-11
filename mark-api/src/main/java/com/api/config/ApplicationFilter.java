package com.api.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Slf4j
@Component
public class ApplicationFilter implements Filter {

	@Value("${mark.auth.header}")
	private String authHeader;

	@Value("${mark.secret.key}")
	private String secretKey;

	
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, " + authHeader);
		
		
		/*HttpServletRequest httpRequest = (HttpServletRequest) request;
		try {
			System.out.println("Filter validate."+httpRequest.getRequestURI());
			validateSecretToken(httpRequest.getHeader(authHeader));
		} catch (Exception e) {
			writeErrorToResponse(e, httpResponse);
			return;
		}*/
		filterChain.doFilter(request, response);
	}

	/*private void validateSecretToken(String secretKey) {

		if (StringUtils.isEmpty(secretKey)) {
			log.info("No secret token found in header, hence cannot process request.");
			throw new IllegalArgumentException(
					"No secret token found in header, hence cannot process request.");
		}

		if (!this.secretKey.equals(secretKey)) {
			log.info("Secret key in header does not matches to configured secret key.");
			throw new IllegalArgumentException(
					"Secret key in header does not matches to configured secret key.");
		}
	}*/

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	/*private void writeErrorToResponse(Exception e, ServletResponse servletResponse)
			throws IOException {

		System.out.println("init handler.");
		
		Response<String> response = new Response<String>(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), null);

		try (PrintWriter writer = servletResponse.getWriter()) {
			writer.write(OBJECT_MAPPER.writeValueAsString(response));
			writer.flush();
		}

	}*/

}
