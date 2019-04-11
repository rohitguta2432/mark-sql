
package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.MarkWebException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Component
@Slf4j
public class HttpUtil {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final String hitechRestBaseUrl;

	@Autowired
	public HttpUtil(@Value("${hitech.rest.base.url}") String hitechRestBaseUrl) {
		this.hitechRestBaseUrl = hitechRestBaseUrl;
	}

	public static HttpClient getHttpClientAcceptAllSsL() {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
					new NoopHostnameVerifier());
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postOrPut(String url, Object payloadData, Map<String, String> headers, HttpMethod method) {

		if (StringUtils.isEmpty(url)) {
			log.info("URL cannot be empty/null");
			throw new IllegalArgumentException("URL cannot be empty/null");
		}

		if (ObjectUtils.isEmpty(payloadData)) {
			log.info("Payload cannot be empty/null");
			throw new IllegalArgumentException("Payload cannot be empty/null");
		}

		String finalUrl = hitechRestBaseUrl + url;
		HttpClient httpClient;
		if (finalUrl.contains("http:")) {
			httpClient = HttpClientBuilder.create().build();
		} else {
			httpClient = this.getHttpClientAcceptAllSsL();
		}

		HttpEntityEnclosingRequestBase postRequest;

		if (HttpMethod.POST == method) {
			postRequest = new HttpPost(finalUrl);
		} else {
			postRequest = new HttpPut(finalUrl);
		}

		if (!CollectionUtils.isEmpty(headers)) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				postRequest.setHeader(entry.getKey(), entry.getValue());
			}
		}

		try {
			StringEntity payloadEntity = new StringEntity(getObjectAsString(payloadData), Charset.forName("UTF8"));
			postRequest.setEntity(payloadEntity);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while making request to url");
			throw new MarkWebException("Error while making request to url");
		}

		HttpResponse response;
		try {
			response = httpClient.execute(postRequest);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while obtaining response from url");
			throw new MarkWebException("Error while obtaining response from url");
		}

		if (response == null) {
			log.error(String.format("No response obtained for url"));
			throw new MarkWebException("No response obtained for url request");
		}
		return parseHttpResponse(response, url);
	}

	public String get(String url, Map<String, String> headers, HttpMethod method) throws UnsupportedEncodingException {

		if (StringUtils.isEmpty(url)) {
			log.info("URL cannot be empty/null");
			throw new IllegalArgumentException("URL cannot be empty/null");
		}

		String finalUrl = hitechRestBaseUrl + url;

		HttpClient httpClient;
		if (finalUrl.contains("http:")) {
			httpClient = HttpClientBuilder.create().build();
		} else {
			httpClient = this.getHttpClientAcceptAllSsL();
		}

		HttpGet getRequest;

		if (HttpMethod.GET == method) {
			getRequest = new HttpGet(finalUrl);
		} else {
			getRequest = new HttpGet(finalUrl);
		}

		if (!CollectionUtils.isEmpty(headers)) {
			headers.forEach((headerName, headerValue) -> {
				getRequest.setHeader(headerName, headerValue);
			});
		}

		HttpResponse response;
		try {
			response = httpClient.execute(getRequest);
		} catch (IOException e) {
			log.error("Error while obtaining response from url");
			throw new MarkWebException("Error while obtaining response from url");
		}

		if (response == null) {
			log.error("No response obtained for url request");
			throw new MarkWebException("No response obtained for url request");
		}
		return parseHttpResponse(response, url);

	}

	public void fetchDataAndWriteToStream(String url, Map<String, String> headers, OutputStream outputStream) {

		String finalUrl = hitechRestBaseUrl + url;
		HttpGet httpGet = new HttpGet(finalUrl);
		if (!CollectionUtils.isEmpty(headers)) {
			headers.forEach((headerName, headerValue) -> {
				httpGet.setHeader(headerName, headerValue);
			});
		}
		try (CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse response = httpclient.execute(httpGet);) {
			HttpEntity entity = response.getEntity();
			entity.writeTo(outputStream);
		} catch (MarkWebException | IOException e) {
			log.error("No response obtained for url request");
			throw new MarkWebException("No response obtained for url request");
		}

	}

	private String parseHttpResponse(HttpResponse response, String url) {
		StringBuilder responseAsString = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				responseAsString.append(line);
			}
		} catch (MarkWebException | UnsupportedOperationException | IOException e) {
			log.error("Error while parsing response for URL");
			throw new MarkWebException("Error while parsing response for URL");
		}
		return responseAsString.toString();
	}

	private String getObjectAsString(Object payload) throws IOException {
		return OBJECT_MAPPER.writeValueAsString(payload);
	}
	
	
}
