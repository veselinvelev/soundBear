package com.soundbear.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soundbear.model.app.Playlist;
import com.soundbear.model.app.Song;
import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.BaseResponse;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.PlaylistsResponse;
import com.soundbear.model.json.reponse.SongsResponse;
import com.soundbear.repository.PlaylistDAO;
import com.soundbear.repository.SongDAO;
import com.soundbear.utils.Pages;
import com.soundbear.utils.SongUtil;
import com.soundbear.utils.UserUtil;
import com.soundbear.utils.ValidatorUtil;

@RestController
public class PlaylistController {
	@Autowired
	private HttpSession session;

	@Autowired
	SongDAO songRepository;

	@Autowired
	PlaylistDAO playlistRepository;

	@RequestMapping(value = "/listPlaylists", method = RequestMethod.GET)
	public  PlaylistsResponse listPlaylists(HttpServletResponse httpResponse) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/addPlaylist/{playlistName}", method = RequestMethod.GET)
	public  PlaylistsResponse addPlaylist(@PathVariable("playlistName") String playlistName,HttpServletRequest request,HttpServletResponse httpResponse) {

		if (ValidatorUtil.isSessionOver(session)){
			 try {
					httpResponse.sendRedirect(request.getContextPath().toString());
					return null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);
		
		PlaylistsResponse response = new PlaylistsResponse();
		
		if(playlistRepository.isPlaylistTaken(playlistName, user.getUserId())){
			response.setStatus(ResponseStatus.NO);
			
		}
		else{
			if (ValidatorUtil.isStringValid(playlistName)) {
				playlistRepository.addPlaylist(new Playlist(0, playlistName, user.getUserId()));
			}
			
			response.setStatus(ResponseStatus.OK);
		}

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/deletePlaylist/{playlistId}", method = RequestMethod.GET)
	public  PlaylistsResponse deletePlaylist(@PathVariable("playlistId") int playlistId,HttpServletRequest request, HttpServletResponse httpResponse) {

		if (ValidatorUtil.isSessionOver(session)){
			 try {
					httpResponse.sendRedirect(request.getContextPath().toString());
					return null;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		playlistRepository.deletePlaylist(playlistId);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}


	@RequestMapping(value = "/showSongs", method = RequestMethod.GET)
	public  SongsResponse showSongs (HttpServletRequest request, HttpServletResponse httpResponse) {
		
		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));

		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		
		playlistSongs.sort(SongUtil.getComaparator(SongController.ARTIST));

		SongsResponse response = new SongsResponse();

		response.setSongs(playlistSongs);

		return response;
	}
	
	@RequestMapping(value = "/sortPlaylistSongs", method = RequestMethod.GET)
	public  SongsResponse sortPlaylistSongs (HttpServletRequest request, HttpServletResponse httpResponse) {
		
		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		String criteria = request.getParameter("criteria");

		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		
		playlistSongs.sort(SongUtil.getComaparator(criteria));

		SongsResponse response = new SongsResponse();

		response.setSongs(playlistSongs);

		return response;
	}
	
	@RequestMapping(value = "/deleteSongFromPlaylist", method = RequestMethod.GET)
	public  SongsResponse deleteSongFromPlaylist (HttpServletRequest request, HttpServletResponse httpResponse) {
		
		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		int songId = Integer.parseInt(request.getParameter("sid"));
		
		playlistRepository.deleteSong(playlistId, songId);

		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		
		playlistSongs.sort(SongUtil.getComaparator(SongController.ARTIST));

		SongsResponse response = new SongsResponse();

		response.setSongs(playlistSongs);

		return response;
	}

	@RequestMapping(value = "/addSongToPlaylist", method = RequestMethod.GET)
	public  BaseResponse addSongToPlaylist (HttpServletRequest request, HttpServletResponse httpResponse) {
		
		if (ValidatorUtil.isSessionOver(session)) {
			try {
				httpResponse.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		int songId = Integer.parseInt(request.getParameter("sid"));
		
		BaseResponse response = new BaseResponse();
		
		if(playlistRepository.isSongInPlaylist(playlistId, songId)){
			response.setStatus(ResponseStatus.NO);
		}
		else{
			response.setStatus(ResponseStatus.OK);
			
			playlistRepository.addSong(playlistId, songId);
		}

		return response;
	}
	
	
}
