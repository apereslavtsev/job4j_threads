package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class WgetFileDownload implements Runnable {
	private final String url;
	private final int speed;
	private final String target;

	public WgetFileDownload(String url, String target, int speed) {
		this.url = url;
		this.target = target;
		this.speed = speed;
	}

	@Override
	public void run() {
		String file = url;
		try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(target)) {
			int downloadData = 0;
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			Long startTime = Calendar.getInstance().getTimeInMillis();
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
				downloadData += bytesRead;
				if (speed <= downloadData) {
					Long currentTime = Calendar.getInstance().getTimeInMillis();
					if (1000 > (currentTime - startTime)) {
						Thread.sleep(1000 - (currentTime - startTime));
					}
					startTime = Calendar.getInstance().getTimeInMillis();
					downloadData = 0;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		if (args.length < 3) {
			throw new IllegalArgumentException("The program expects 3 arguments: url, target, speed");
		}
		String sUrl = args[0];
		URL url = new URL(sUrl);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IllegalArgumentException("url: " + url + " does not exist");
		}
		String target = args[1];
		int speed = Integer.parseInt(args[2]);
		Thread wget = new Thread(new WgetFileDownload(sUrl, target, speed));
		wget.start();
		wget.join();
	}
}
