package com.soundbear.model.json.reponse;

import java.util.ArrayList;
import java.util.List;

import com.soundbear.model.app.Song;

public class MySongsResponse {
	private List<Song> mySongs = new ArrayList<Song>();

	public void setMySongs(List<Song> mySongs) {
		this.mySongs = mySongs;
	}
}
