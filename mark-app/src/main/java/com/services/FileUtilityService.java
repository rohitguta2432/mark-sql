package com.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUtilityService {

	void saveFile(MultipartFile multiPartFile, String clientId, String folder, String fileName);

	String deleteFile(String clientId, String folder, String fileName);

	String getFileName(String fileName);

}
