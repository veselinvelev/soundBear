package com.soundbear.model.json.reponse;

import java.util.ArrayList;
import java.util.List;

import com.soundbear.model.app.Playlist;

public class PlaylistsResponse extends BaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2408892846861796545L;
	private List<Playlist> playlists = new ArrayList<Playlist>();

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}
