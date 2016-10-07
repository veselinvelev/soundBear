package com.soundbear.repository;

import org.springframework.stereotype.Component;

import com.soundbear.model.app.Song;

@Component
public interface SongDAO {
	
	public void addArtist(String artistName);
	
	public void addSong(Song song);

}
