package com.soundbear.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soundbear.model.app.Song;
import com.soundbear.model.app.User;
import com.soundbear.model.json.reponse.BaseResponse.ResponseStatus;
import com.soundbear.model.json.reponse.SearchResponse;
import com.soundbear.model.json.request.SearchRequest;
import com.soundbear.repository.SongDAO;
import com.soundbear.repository.UserDAO;
import com.soundbear.utils.UserUtil;

@RestController
public class SearchController {

	@Autowired
	private UserDAO userRepository;
	@Autowired
	private SongDAO songRepository;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public SearchResponse validateRegisterForm(@RequestBody SearchRequest request) {

		String search = request.getSearch();

		ArrayList<User> users = new ArrayList<>(userRepository.listUsers(search));
		ArrayList<Song> songs = new ArrayList<>(songRepository.listSongs(search));

		UserUtil.maskPassword(users);

		SearchResponse response = new SearchResponse();
		response.setStatus(ResponseStatus.OK);
		response.setUsers(users);
		response.setSongs(songs);

		return response;
	}

}
