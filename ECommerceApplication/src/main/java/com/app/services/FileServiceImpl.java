package com.app.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileServiceImpl implements FileService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		String originalFileName = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
		String filePath = path + File.separator + fileName;

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		Files.copy(file.getInputStream(), Paths.get(filePath));

		log.info("Image uploaded successfully!");

		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;

		InputStream inputStream = new FileInputStream(filePath);

		log.info("file retrived successfully!");

		return inputStream;
	}

}
