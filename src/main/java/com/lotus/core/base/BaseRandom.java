package com.lotus.core.base;

import java.util.Random;

import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
public class BaseRandom {

	private final Random random = new Random(System.currentTimeMillis());
	
	public int generateCode() {
		return random.nextInt();
	}
	
	
}
