package com.soundbear.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.soundbear.repository.UserRepository;

public class DBCleaner extends Thread {

	@Autowired
	UserRepository userRepository;

	public DBCleaner() {
		setDaemon(true);
	}

	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(400000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		//	userRepository.clearInactive(new Date());

		}

	}

}
