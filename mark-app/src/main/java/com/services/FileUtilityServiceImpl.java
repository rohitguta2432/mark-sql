package com.services;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.MarkWebException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Slf4j
@Service("fileUtilityServiceImpl")
public class FileUtilityServiceImpl implements FileUtilityService {

	private static  String locationFormat = "%s/%s/";	
	private final  String fileNameFormat = "%s.%s";
	
	@Value("${media.location}")
	private String mediaLocation;
	
	@Override
	public  void saveFile(MultipartFile multiPartFile, String folderId, String folderType, String fileName) {

	    if (multiPartFile.isEmpty()) {
	      return;
	    }
	    String assetLocation = String.format(locationFormat, mediaLocation, folderId);
	    isFileLocationValid(assetLocation);
	    String fileLocation = String.format(locationFormat, assetLocation, folderType);
	    isFileLocationValid(fileLocation);
	    fileLocation += fileName;
	    File file = new File(fileLocation);
	    try {
	      multiPartFile.transferTo(file);
	      log.info("multipart file saved.");
	    } catch (IllegalStateException | IOException e) {
	      String message =
	          String.format("Error while saving file with name : %s for client id : %s",
	              multiPartFile.getOriginalFilename(), folderId);
	      log.error("error while multipart file saving.");
	      throw new MarkWebException(message, e);
	    }
	  }
	
	@Override
	public  String deleteFile(String folderId, String folderType, String hashedFileName) {
		
	    String assetLocation = String.format(locationFormat, mediaLocation, folderId);
	    String fileLocation = String.format(locationFormat, assetLocation, folderType);
	    File fileToBeDeleted = new File(fileLocation += hashedFileName);
	    if (fileToBeDeleted.exists()) {
	      fileToBeDeleted.delete();
	    }
	    log.info("multipart file deleted.");
	    return hashedFileName;
	  }
	
	@Override
	public  String getFileName(String fileName) {
		log.info("get hashed multipart file name"+String.format(fileNameFormat, UUID.fromString(UUID.randomUUID().toString()).toString(),
		        FilenameUtils.getExtension(fileName)));
		return String.format(fileNameFormat, UUID.fromString(UUID.randomUUID().toString()).toString(),
	        FilenameUtils.getExtension(fileName));
	  }
	
	private  void isFileLocationValid(String fileLocation) {
	    File file = new File(fileLocation);
	    if (!file.exists()) {
	      file.mkdirs();
	    }
	  }
}
