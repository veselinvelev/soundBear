package com.soundbear.model.json.reponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.soundbear.model.app.Song;
import com.soundbear.model.app.User;

public class SearchResponse extends BaseResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1754781928733465989L;
	
	
	
	private Collection<User> users;
	private Collection<Song> songs;
	
	
	public void setUsers(ArrayList<User> users) {
		this.users = Collections.unmodifiableList(users);
	}
	public void setSongs(ArrayList<Song> songs) {
		this.songs =  Collections.unmodifiableList(songs);
	}
	

}
