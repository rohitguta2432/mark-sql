package com.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */

@Component
public class UrlUtil {

  @Value("${mark.media.url}")
  private String mediaUrl;

  private String urlFormat = "%s/%s/%s";

  public String getUrl(String fileLocation, String fileName) {
    return String.format(urlFormat, mediaUrl, fileLocation, fileName);
  }

}
