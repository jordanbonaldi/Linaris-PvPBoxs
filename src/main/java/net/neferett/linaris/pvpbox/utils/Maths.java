package net.neferett.linaris.pvpbox.utils;

import java.util.Random;

public class Maths {

	public static int Rand(int min, int max){
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
	
}
