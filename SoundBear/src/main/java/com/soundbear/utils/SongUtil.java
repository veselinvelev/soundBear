package com.soundbear.utils;

import java.util.Comparator;

import com.soundbear.model.app.Song;

public class SongUtil {
	public static Comparator<Song> getComaparator(String criteria) {
		switch (criteria) {
		case "song":
			return (s1, s2) -> s1.getSongName().toLowerCase().compareTo(s2.getSongName().toLowerCase());

		case "genre":
			return (s1, s2) -> s1.getGenre().compareTo(s2.getGenre());

		default:
			return (s1, s2) -> s1.getArtist().toLowerCase().compareTo(s2.getArtist().toLowerCase());
		}
	}
}
