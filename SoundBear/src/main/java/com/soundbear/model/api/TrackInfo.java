package com.soundbear.model.api;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TrackInfo {
	@SerializedName("track")
	private Track track;

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	@Override
	public String toString() {
		return "TrackInfo [track=" + track + "]";
	}

	public class Track {

		@SerializedName("name")
		private String name;
		@SerializedName("wiki")
		private Wiki wiki;

		@SerializedName("album")
		private Album album;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Track [name=" + name + ", wiki="  + ", image="  + "]";
		}

		public Wiki getWiki() {
			return wiki;
		}

		public void setWiki(Wiki wiki) {
			this.wiki = wiki;
		}



		public Album getAlbum() {
			return album;
		}

		public void setAlbum(Album album) {
			this.album = album;
		}

	}

	public class Wiki {
		@SerializedName("summary")
		private String summary;
//		@SerializedName("content")
//		private String content;

		@Override
		public String toString() {
			return "Wiki [summary=" + summary + "]";
		}

		public String getSummary() {

			return summary;
		}

		public void setSummary(String summary) {

			this.summary = summary;
		}

//		public String getContent() {
//			return content;
//		}
//
//		public void setContent(String content) {
//			this.content = content;
//		}

	}

	public class Album {
		@SerializedName("image")
		List<Image> image;

		public List<Image> getImage() {
			return image;
		}

		public void setImage(List<Image> image) {
			this.image = image;
		}

		@Override
		public String toString() {
			String result = null;
			for (Image image2 : image) {
				result += image2 + " ";
			}

			return result;
		}

	}

	public class Image {

		@SerializedName("#text")
		String text;

		@SerializedName("size")
		String size;

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return "Image [size=" + size + ", text=" + text + "]";
		}
	}

}
