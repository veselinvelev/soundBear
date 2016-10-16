package com.soundbear.model.json.reponse;

import java.util.ArrayList;

import com.soundbear.model.api.TrackInfo;

public class SongInfoResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6577660823484077187L;
	
	
	private ArrayList<TrackInfo> songsInfo;


	public void setSongsInfo(ArrayList<TrackInfo> songsInfo) {
		this.songsInfo = new ArrayList(songsInfo);
	}
	
	
	
	

}
