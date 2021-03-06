package com.soundbear.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.soundbear.model.api.ApiDAO;
import com.soundbear.model.api.TrackInfo;
import com.soundbear.model.app.Song;
import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.SongInfoResponse;
import com.soundbear.model.json.reponse.SongsResponse;
import com.soundbear.repository.SongDAO;
import com.soundbear.utils.AWSConstants;
import com.soundbear.utils.Pages;
import com.soundbear.utils.SongUtil;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@RestController
public class SongController {

	public static final String ARTIST = "artist";

	@Autowired
	private ApiDAO api;
	@Autowired
	private HttpSession session;

	@Autowired
	private SongDAO songRepository;

	

	@RequestMapping(value = "/listMySongs", method = RequestMethod.GET)
	public SongsResponse listMySongs(HttpServletResponse resp, HttpServletRequest req) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int id = 0;
		User user = null;
		ArrayList<Song> userSongs = null;

		if (req.getParameter("id") != null) {
			id = Integer.parseInt(req.getParameter("id"));
			userSongs = (ArrayList<Song>) songRepository.listSongs(id);
		} else {
			user = (User) session.getAttribute(UserUtil.LOGGED_USER);
			userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());
		}

		userSongs.sort(SongUtil.getComaparator(ARTIST));

		SongsResponse response = new SongsResponse();

		response.setSongs(userSongs);

		return response;
	}

	@RequestMapping(value = "/sortMySongs/{sortCriteria}", method = RequestMethod.GET)
	public SongsResponse sortMySongs(@PathVariable("sortCriteria") String criteria, HttpServletResponse resp,
			HttpServletRequest req) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				resp.sendRedirect(req.getContextPath().toString());
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		ArrayList<Song> userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());

		userSongs.sort(SongUtil.getComaparator(criteria));

		SongsResponse response = new SongsResponse();

		response.setSongs(userSongs);

		return response;
	}

	@RequestMapping(value = "/deleteSong", method = RequestMethod.GET)
	public SongsResponse deleteSong(HttpServletRequest request, HttpServletResponse response) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				response.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		int songId = Integer.parseInt(request.getParameter("sid"));

		songRepository.deleteSong(songId);

		ArrayList<Song> userSongs = (ArrayList<Song>) songRepository.listSongs(user.getUserId());

		userSongs.sort(SongUtil.getComaparator(ARTIST));

		SongsResponse resp = new SongsResponse();

		resp.setSongs(userSongs);

		return resp;

	}

	@RequestMapping(value = "/showSongInfo", method = RequestMethod.GET)
	public SongInfoResponse showSongInfo(HttpServletRequest request, HttpServletResponse httpResponse) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int playlistId = Integer.parseInt(request.getParameter("pid"));
		SongInfoResponse response = new SongInfoResponse();
		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		ArrayList<TrackInfo> songsInfo = new ArrayList<TrackInfo>();

		for (Song song : playlistSongs) {
			songsInfo.add(api.getTrackInfo(song.getSongName(), song.getArtist()));
		}
		response.setSongsInfo(songsInfo);

		playlistSongs.sort(SongUtil.getComaparator(SongController.ARTIST));

		return response;
	}

	

}
