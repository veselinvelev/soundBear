package com.soundbear.model.json.reponse;

import java.util.ArrayList;
import java.util.List;

import com.soundbear.model.app.Song;

public class SongsResponse {
	private List<Song> songs = new ArrayList<Song>();

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
