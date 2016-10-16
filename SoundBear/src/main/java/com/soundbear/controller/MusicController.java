package com.soundbear.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.soundbear.model.app.Playlist;
import com.soundbear.repository.PlaylistDAO;
import com.soundbear.utils.Pages;
import com.soundbear.utils.ValidatorUtil;

@Controller
public class MusicController {

	@Autowired
	PlaylistDAO playlistRepository;
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/openPlaylist", method = RequestMethod.GET)
	public String openPlaylist(HttpServletRequest request, HttpServletResponse response) {

		if (ValidatorUtil.isSessionOver(session)) {
			try {
				response.sendRedirect(Pages.LOGIN);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int playlistId = Integer.parseInt(request.getParameter("pid"));
		
		Playlist playlist = playlistRepository.getPlaylist(playlistId);

		request.setAttribute("playlist", playlist);
		
		return Pages.PLAYLIST;
	}
	
	
	
}
