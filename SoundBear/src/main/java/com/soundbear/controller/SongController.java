package com.soundbear.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.soundbear.model.app.Song;
import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.SongsResponse;
import com.soundbear.repository.SongDAO;
import com.soundbear.utils.AWSConstants;
import com.soundbear.utils.Pages;
import com.soundbear.utils.SongUtil;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class SongController {

	public static final String ARTIST = "artist";

	@Autowired
	private HttpSession session;

	@Autowired
	SongDAO songRepository;

	@RequestMapping(value = "/songUpload", method = RequestMethod.POST)
	public String songUplaod(@RequestParam("song") MultipartFile multipartFile, HttpServletRequest request) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		String artist = request.getParameter("artist").trim();
		String songName = request.getParameter("name").trim();
		String genre = request.getParameter("genre").trim();

		if (ValidatorUtil.isStringValid(artist) && ValidatorUtil.isStringValid(songName)
				&& ValidatorUtil.isStringValid(genre)) {

			

			new Thread() {
				AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

				@Override
				public void run() {
					InputStream is = null;

					try {
						is = multipartFile.getInputStream();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					String songCloudName = (songName.replaceAll(AWSConstants.AWS_FILE_NAME_REGEX, "") + user.getUserId()
							+ ("" + LocalDateTime.now().withNano(0)).replaceAll("[T:-]", ""));

					// save song on s3 with public read access
					s3client.putObject(new PutObjectRequest(AWSConstants.BUCKET_NAME, songCloudName, is, new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead));

					// get referance to the song object
					S3Object s3Object = s3client.getObject(new GetObjectRequest(AWSConstants.BUCKET_NAME, songCloudName));

					// get song url
					String songURL = s3Object.getObjectContent().getHttpRequest().getURI().toString();

					songRepository.addSong(new Song(0, songName, songURL, user.getUserId(), genre, artist));

					try {
						is.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

		}
		else {

		}

		return Pages.UPLOAD;
	}

	@RequestMapping(value = "/listMySongs", method = RequestMethod.GET)
	public @ResponseBody SongsResponse listMySongs(HttpServletResponse resp, HttpServletRequest req) {

		

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		int id = 0;
		User user = null;
		ArrayList<Song> userSongs = null;
		
		if (req.getParameter("id") != null ) {
			id = Integer.parseInt(req.getParameter("id"));
			userSongs = (ArrayList<Song>) songRepository.listSongs(id);
		}else{
			user = (User) session.getAttribute(UserUtil.LOGGED_USER);
			userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());
		}

		
		userSongs.sort(SongUtil.getComaparator(ARTIST));

		SongsResponse response = new SongsResponse();

		response.setSongs(userSongs);

		return response;
	}

	@RequestMapping(value = "/sortMySongs/{sortCriteria}", method = RequestMethod.GET)
	public @ResponseBody SongsResponse sortMySongs(@PathVariable("sortCriteria") String criteria, HttpServletResponse resp, HttpServletRequest req ) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		if (user == null) {
			
				try {
					req.getRequestDispatcher(Pages.LOGIN).forward(req, resp);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
		
		}

		ArrayList<Song> userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());
		
		userSongs.sort(SongUtil.getComaparator(criteria));

		SongsResponse response = new SongsResponse();

		response.setSongs(userSongs);

		return response;
	}
	


}
