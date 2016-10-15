package com.soundbear.repository;

import java.util.List;

import com.soundbear.model.app.Playlist;

public interface PlaylistDAO {

	void addPlaylist(Playlist playlist);

	void addSong(int playlistId, int songId);
	
	Playlist getPlaylist(int playlistId);

	List<Playlist> listPlaylists(int userId);

	void deletePlaylist(int playlistId);

	void deleteSong(int playlistId, int songId);

	boolean isSongInPlaylist(int playlistId, int songId);

	boolean isPlaylistTaken(String playlistName, int userId);
}