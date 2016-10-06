package com.soundbear.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.soundbear.repository.SongDAO;
import com.soundbear.repository.SongRepository;

@Component
public class SongController {
	
	@Autowired
	SongDAO songRepository;
	
	
	

	
	
	

}
