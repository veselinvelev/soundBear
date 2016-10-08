package com.soundbear.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.soundbear.model.app.User;
import com.soundbear.repository.SongDAO;

@Controller
public class SongController {
	public static final String BUCKET_NAME = "soundbear";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	SongDAO songRepository;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void  upload(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request ){
		
		User user = (User) session.getAttribute(UserController.LOGGED_USER);
		
		String artist = request.getParameter("artist").trim();
		//String songName = request.getParameter(arg0)
		
		System.err.println(request.getParameter("name") + artist+" =================");
		
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
		InputStream is = null;
		
		try {
			 is = multipartFile.getInputStream(); 
			
			//save on s3 with public read access
			s3client.putObject(new PutObjectRequest(BUCKET_NAME,request.getParameter("name") , is,new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
			
			//get referance to the song object	
			S3Object s3Object = s3client.getObject(new GetObjectRequest(BUCKET_NAME,request.getParameter("name")));
			
			//song url
			String songURL = s3Object.getObjectContent().getHttpRequest().getURI().toString();
			
			//add to model
			//redirectAttributes.addAttribute("songURL",songURL);
			
			System.out.println(songURL);
			
			is.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	
	
	

}
