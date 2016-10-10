package com.soundbear.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
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
import com.soundbear.model.json.reponse.MySongsResponse;
import com.soundbear.repository.SongDAO;
import com.soundbear.utils.Pages;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class SongController {
	public static final String BUCKET_NAME = "soundbear";

	@Autowired
	private HttpSession session;

	@Autowired
	SongDAO songRepository;

	@RequestMapping(value = "/songUpload", method = RequestMethod.POST)
	public String songUplaod(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

		User user = (User) session.getAttribute(UserController.LOGGED_USER);

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

					String songCloudName = (songName.replaceAll("[^a-zA-Z0-9]", "") + user.getUserId()
							+ ("" + LocalDateTime.now().withNano(0)).replaceAll("[T:-]", ""));

					// save song on s3 with public read access
					s3client.putObject(new PutObjectRequest(BUCKET_NAME, songCloudName, is, new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead));

					// get referance to the song object
					S3Object s3Object = s3client.getObject(new GetObjectRequest(BUCKET_NAME, songCloudName));

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
	public @ResponseBody MySongsResponse listMySongs() {

		User user = (User) session.getAttribute(UserController.LOGGED_USER);

		ArrayList<Song> userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());

		MySongsResponse response = new MySongsResponse();

		response.setMySongs(userSongs);

		return response;
	}

	/*@RequestMapping(value = "/sortMySongs/{sortCriteria}", method = RequestMethod.GET)
	public @ResponseBody MySongsResponse sortMySongs(@PathVariable("sortCriteria") String criteria) {

		User user = (User) session.getAttribute(UserController.LOGGED_USER);

		ArrayList<Song> userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());
		
		userSongs.sort((s1,s2)->s1.getArtist().compareTo(s2.getArtist()));

		MySongsResponse response = new MySongsResponse();

		response.setMySongs(userSongs);

		return response;
	}

	private Comparator<Song> f (){
		return (s1,s2)->s1.getArtist().compareTo(s2.getArtist());
	}
	
	private Comparator<Song> getComaparator(String criteria) {
		switch (criteria) {
		case "song":
			return (s1,s2)->s1.getArtist().compareTo(s2.getArtist());

		case "genre":
			return new Comparator<Song>() {

				@Override
				public int compare(Song s1, Song s2) {
					return s1.getGenre().compareTo(s2.getGenre());
				}
			};

		default:
			return new Comparator<Song>() {

				@Override
				public int compare(Song s1, Song s2) {
					return s1.getArtist().compareTo(s2.getArtist());
				}
			};
		}
	}*/

}
