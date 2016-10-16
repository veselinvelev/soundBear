package com.soundbear.model.api;

import java.io.BufferedInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@Component
public class ApiDAO {

	private static final int MIN_TIME_BETWEEN_REQUESTS = 300;
	private static final String A_TAG_START = "<a";

	public TrackInfo getTrackInfo(String songName, String artist) {

		songName = songName.replace(' ', '+');
		artist = artist.replace(' ', '+');

		String trackURL = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=57ee3318536b23ee81d6b27e36997cde&artist="
				+ artist + "&track=" + songName + "&format=json";

		String gsonString = getJsonString(trackURL).trim();

		if (gsonString.contains("error")) {
			return null;
		}

		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(gsonString));
		reader.setLenient(true);
		TrackInfo track = null;
		try {
			track = gson.fromJson(reader, TrackInfo.class);
		} catch (com.google.gson.JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}

		String summary = track.getTrack().getWiki().getSummary();
		if (summary != null && summary.contains(A_TAG_START)) {
			int start = summary.indexOf(A_TAG_START);
			summary = summary.substring(0, start - 1) + ".";
			track.getTrack().getWiki().setSummary(summary);

		}

		try {
			Thread.sleep(MIN_TIME_BETWEEN_REQUESTS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return track;
	}

	private String getJsonString(String url) {
		HttpURLConnection connection = null;
		String gsonString = null;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();

			connection.connect();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				try (BufferedInputStream bis = new BufferedInputStream(connection.getInputStream())) {
					byte[] bytes = new byte[connection.getContentLength()];

					bis.read(bytes);

					gsonString = new String(bytes, "UTF-8").trim();

					gsonString += " \"}}} ";
					gsonString = gsonString.substring(0, gsonString.length());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return gsonString;

	}

}
