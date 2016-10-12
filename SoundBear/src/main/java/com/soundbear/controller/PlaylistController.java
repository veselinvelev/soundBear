package com.soundbear.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soundbear.model.app.Playlist;
import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.PlaylistsResponse;
import com.soundbear.repository.PlaylistDAO;
import com.soundbear.repository.SongDAO;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class PlaylistController {
	@Autowired
	private HttpSession session;

	@Autowired
	SongDAO songRepository;

	@Autowired
	PlaylistDAO playlistRepository;

	@RequestMapping(value = "/listPlaylists", method = RequestMethod.GET)
	public @ResponseBody PlaylistsResponse listPlaylists() {

		User user = (User) session.getAttribute(UserController.LOGGED_USER);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/addPlaylist/{playlistName}", method = RequestMethod.GET)
	public @ResponseBody PlaylistsResponse addPlaylist(@PathVariable("playlistName") String playlistName) {
		
		User user = (User) session.getAttribute(UserController.LOGGED_USER);

		if(ValidatorUtil.isStringValid(playlistName)){
			playlistRepository.addPlaylist(new Playlist(0, playlistName, user.getUserId()));
		}

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}
	
	@RequestMapping(value = "/deletePlaylist/{playlistId}", method = RequestMethod.GET)
	public @ResponseBody PlaylistsResponse deletePlaylist(@PathVariable("playlistId") int playlistId) {
		
		User user = (User) session.getAttribute(UserController.LOGGED_USER);

		playlistRepository.deletePlaylist(playlistId);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}
	
}
