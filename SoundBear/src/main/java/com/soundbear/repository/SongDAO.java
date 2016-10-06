package com.soundbear.repository;

import org.springframework.stereotype.Component;

@Component
public interface SongDAO {
	
	public void addArtist(String artistName);

}
