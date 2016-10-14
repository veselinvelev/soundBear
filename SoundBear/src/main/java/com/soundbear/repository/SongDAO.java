package com.soundbear.repository;

import java.util.List;

import com.soundbear.model.app.Song;

public interface SongDAO {

	void addSong(Song song);

	int getGenreId(String genreName);

	int getArtistId(String artistName);

	void addArtist(String artistName);

	List<Song> listSongs();

	List<Song> listSongs(String search);

	List<Song> listSongs(int userId);

	List<Song> listSongsByGenre(String genreName);

	List<Song> listSongsByPlaylist(int playlistId);

}