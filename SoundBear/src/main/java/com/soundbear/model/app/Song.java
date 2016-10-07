package com.soundbear.model.app;

public class Song {
	private int songId;
	private String songName;
	private String path;
	private int userId;
	private String genre;
	private String artist;
	
	public Song(int songId, String songName, String path, int userId, String genre, String artist) {
		this.songId = songId;
		this.songName = songName;
		this.path = path;
		this.userId = userId;
		this.genre = genre;
		this.artist = artist;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "Song [songId=" + songId + ", songName=" + songName + ", path=" + path + ", userId=" + userId
				+ ", genre=" + genre + ", artist=" + artist + "]\n";
	}
	
}
