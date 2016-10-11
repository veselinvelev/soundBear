package com.soundbear.model.app;

public class Playlist {
	private int playlistId;
	private String playlistName;
	private int userId;
	
	public Playlist(int playlistId, String playlistName, int userId) {
		this.playlistId = playlistId;
		this.playlistName = playlistName;
		this.userId = userId;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Playlist [playlistId=" + playlistId + ", playlistName=" + playlistName + ", userId=" + userId + "]\n";
	}

	
}
