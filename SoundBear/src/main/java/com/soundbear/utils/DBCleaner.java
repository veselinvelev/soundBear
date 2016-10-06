package com.soundbear.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.soundbear.repository.UserDAO;

@Component
@Scope("singleton")
public class DBCleaner extends Thread {

	private static final int INTERVAL_OF_DB_CLEAN = 10800000;
	
	
	@Autowired
	UserDAO userRepository;
	public DBCleaner() {
		setDaemon(true);
	}


	@Override
	public void run() {

		while(true){
			try {
				Thread.sleep(INTERVAL_OF_DB_CLEAN);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("===============================CLEANING==================");
			System.out.println(Thread.currentThread().getName());
			userRepository.clearInactive();

		}

	}

}
