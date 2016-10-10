package com.soundbear.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.soundbear.model.app.Song;

@Component
public interface SongDAO {
	
	public void addArtist(String artistName);
	
	public void addSong(Song song);
	
	int getGenreId(String genreName);
	
	int getArtistId(String artistName);
	
	List<Song> listSongs();
	
	List<Song> listSongs(String key);
	
	List<Song> listSongs(int userId);
	
	List<Song> listSongsByGenre(String genreName);
	
}
