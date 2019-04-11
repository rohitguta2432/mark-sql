package com.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.web.MarkWebException;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Rohit Raj
 *
 */
@Component
@Slf4j
public class AuthorizationUtil {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final HttpUtil httpUtil;

	@Autowired
	public AuthorizationUtil(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}

	public JsonNode generateAuthToken(String userName, String password) {

		Map<String, String> headers = new HashMap<>();
		headers.put(MarkConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers.put(Constants.ADMIN_REQUEST, "TRUE");
		String response = httpUtil.postOrPut(URLConstants.AUTHORIZATION_URL, getAuthPayload(userName, password),
				headers, HttpMethod.POST);
		JsonNode responseData;
		try {
			responseData = parseResponse(response);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while parsing authorization token response");
			throw new MarkWebException("Error while parsing authorization token response");
		}

		if (responseData == null) {
			log.error("No response obtained from Authorization api");
			throw new MarkWebException("No response obtained from Authorization api");
		}
		return responseData;
	}

	private JsonNode parseResponse(String response) throws JsonParseException, JsonMappingException, IOException {
		return OBJECT_MAPPER.readTree(response);
	}

	private Map<String, String> getAuthPayload(String userName, String password) {
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MarkConstant.U_KEY, userName);
		dataMap.put(MarkConstant.P_KEY, password);
		return dataMap;
	}

	public JsonNode makeRequestPost(String url, Object entity) {
		Map<String, String> headers = new HashMap<>();
		headers.put(MarkConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers.put(Constants.ADMIN_REQUEST, "TRUE");
		headers.put(Constants.X_AUTH_HEADER, "admin");

		String response = httpUtil.postOrPut(url, entity, headers, HttpMethod.POST);
		JsonNode responseData;
		try {
			responseData = parseResponse(response);

		} catch (IOException e) {
			e.printStackTrace();
			log.error("No response obtained from Authorization api");
			throw new MarkWebException("No response obtained from Authorization api");
		}

		if (responseData == null) {
			log.error("No response obtained from Authorization api");
			throw new MarkWebException("No response obtained from Authorization api");
		}
		return responseData;
	}

	public JsonNode makeRequestGet(String url) {
		Map<String, String> headers = new HashMap<>();
		headers.put(MarkConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		headers.put(Constants.ADMIN_REQUEST, "TRUE");
		headers.put(Constants.X_AUTH_HEADER, "admin");
		try {
			String response = httpUtil.get(url, headers, HttpMethod.GET);
			JsonNode responseData;

			responseData = parseResponse(response);

			if (responseData == null) {
				log.error("No response obtained from Authorization api");
				throw new MarkWebException("No response obtained from Authorization api");
			}
			return responseData;

		} catch (MarkWebException | IOException e) {
			e.printStackTrace();
			log.error("Error while making request to rest-api url");
			throw new MarkWebException("Error while making request to rest-api url");
		}

	}
	
}
