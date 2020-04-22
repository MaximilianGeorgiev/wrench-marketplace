package com.sampleproject.wrenchmarketplace.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.services.ImageService;

@Controller
public class ImageController {
	private ImageService imageService;

	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	/*
	 * Stores the file physically in /resources/uploads Pushes a new ImageRoute to
	 * the database Will be troublesome when someone else downloads the project Will
	 * fix one day
	 */

	public String handleFileUpload(MultipartFile file) {
		String absolutePathToUploads = "C:\\Repositories\\wrench-marketplace\\wrench-marketplace\\src\\main\\resources\\uploads\\";
		String filePath = absolutePathToUploads + file.getOriginalFilename();

		try {
			File dest = new File(filePath);
			file.transferTo(dest);
			imageService.save(new ImageRoute(0, filePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filePath;
	}
}
