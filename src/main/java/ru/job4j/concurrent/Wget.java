package ru.job4j.concurrent;

import java.util.Iterator;

public class Wget {
	public static void main(String[] args) {
		Thread thread = new Thread(
				() -> {
					for (int i = 0; i < 100; i++) {
						System.out.print("\rLoading : " + i  + "%");
					}
				}
		);
		
		thread.start();
		try {
			thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}