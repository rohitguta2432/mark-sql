package com.api.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.api.exception.MarkException;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */

@Slf4j
@Component
public class FileUtility {

  @Value("${mark.media.location}")
  private String mediaLocation;

  private static final String locationFormat = "%s/%s/";
  private final String fileNameFormat = "%s.%s";

  public void saveFile(MultipartFile multiPartFile, String clientId, String folder, String fileName) {

    if (ObjectUtils.isEmpty(multiPartFile) || multiPartFile.isEmpty()) {
      log.info("No media file to save");
      return;
    }
    String assetLocation = String.format(locationFormat, mediaLocation, clientId);
    isFileLocationValid(assetLocation);
    String fileLocation = String.format(locationFormat, assetLocation, folder);
    isFileLocationValid(fileLocation);
    fileLocation += fileName;
    File file = new File(fileLocation);
    try {
      multiPartFile.transferTo(file);
    } catch (IllegalStateException | IOException e) {
      String message =
          String.format("Error while saving file with name : %s for client id : %s",
              multiPartFile.getOriginalFilename(), clientId);
      log.error(message, e);
      throw new MarkException(message, e);
    }
  }

  public void deleteFile(String clientId, String folder, String fileName) {
    String assetLocation = String.format(locationFormat, mediaLocation, clientId);
    String fileLocation = String.format(locationFormat, assetLocation, folder);
    File fileToBeDeleted = new File(fileLocation += fileName);
    if (fileToBeDeleted.exists()) {
      fileToBeDeleted.delete();
    }
  }

  public String getFileName(String fileName) {
    return String.format(fileNameFormat, UUID.fromString(UUID.randomUUID().toString()).toString(),
        FilenameUtils.getExtension(fileName));

  }

  private void isFileLocationValid(String fileLocation) {
    File file = new File(fileLocation);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

}
