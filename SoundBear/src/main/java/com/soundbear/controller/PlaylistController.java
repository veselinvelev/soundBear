package com.soundbear.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/addPlaylist/{playlistName}", method = RequestMethod.GET)
	public @ResponseBody PlaylistsResponse addPlaylist(@PathVariable("playlistName") String playlistName) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		if (ValidatorUtil.isStringValid(playlistName)) {
			playlistRepository.addPlaylist(new Playlist(0, playlistName, user.getUserId()));
		}

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/deletePlaylist/{playlistId}", method = RequestMethod.GET)
	public @ResponseBody PlaylistsResponse deletePlaylist(@PathVariable("playlistId") int playlistId) {

		User user = (User) session.getAttribute(UserUtil.LOGGED_USER);

		playlistRepository.deletePlaylist(playlistId);

		ArrayList<Playlist> userPlaylists = (ArrayList<Playlist>) playlistRepository.listPlaylists(user.getUserId());

		PlaylistsResponse response = new PlaylistsResponse();

		response.setPlaylists((userPlaylists));

		return response;
	}

	@RequestMapping(value = "/openPlaylist", method = RequestMethod.GET)
	public String openPlaylist(HttpServletRequest request) {

	/*	if (ValidatorUtil.isSessionOver(session)) {
			
			System.out.println("====================================================================================");
				System.out.println("----------------------------------------------------------------------------------------");
				
				System.err.println("URL" + request.getRequestURL().toString());
				System.err.println("URI" + request.getRequestURI().toString());
				
				System.err.println("Context" + request.getContextPath().toString());
				
		
		 try {
					//request.getRequestDispatcher("login").forward(request,response);
					response.sendRedirect(request.getContextPath().toString());
					//return "";
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}*/
		
		//session

		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		Playlist playlist = playlistRepository.getPlaylist(playlistId);

		request.setAttribute("playlist", playlist);
	/*	try {
			response.sendRedirect(request.getContextPath().toString() + "/playlist");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			response.sendRedirect(request.getContextPath()+"/playlist");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return Pages.PLAYLIST;
	}
	
	@RequestMapping(value = "/showSongs", method = RequestMethod.GET)
	public @ResponseBody SongsResponse showSongs (HttpServletRequest request) {
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));

		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		
		playlistSongs.sort(SongUtil.getComaparator(SongController.ARTIST));

		SongsResponse response = new SongsResponse();

		response.setSongs(playlistSongs);

		return response;
	}
	
	@RequestMapping(value = "/sortPlaylistSongs", method = RequestMethod.GET)
	public @ResponseBody SongsResponse sortPlaylistSongs (HttpServletRequest request) {
		
		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		String criteria = request.getParameter("criteria");

		ArrayList<Song> playlistSongs = (ArrayList<Song>) songRepository.listSongsByPlaylist(playlistId);
		
		playlistSongs.sort(SongUtil.getComaparator(criteria));

		SongsResponse response = new SongsResponse();

		response.setSongs(playlistSongs);

		return response;
	}
	
	@RequestMapping(value = "/deleteSongFromPlaylist", method = RequestMethod.GET)
	public @ResponseBody SongsResponse deleteSongFromPlaylist (HttpServletRequest request) {
		
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
	public @ResponseBody BaseResponse addSongToPlaylist (HttpServletRequest request) {
		
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
