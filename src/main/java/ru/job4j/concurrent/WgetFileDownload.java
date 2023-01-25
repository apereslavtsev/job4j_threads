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
	
	public WgetFileDownload(String url, int speed) {	
		this.url = url;
		this.speed = speed;
	}

	@Override
	public void run() {
        String file = url;
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];                     
            int bytesRead;
			while ((bytesRead  = in.read(dataBuffer, 0, 1024)) != -1) {
				Long startDate = Calendar.getInstance().getTimeInMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Long downloadTime = Calendar.getInstance().getTimeInMillis() - startDate;                
                if (speed > downloadTime) {
                	Thread.sleep(speed - downloadTime);
                }                
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }		
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		String sUrl = args[0];		  
		URL url = new URL(sUrl);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();				
        if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
        	throw new IllegalArgumentException("url: " + url + " does not exist");
        }
        int speed = Integer.parseInt(args[1]);        
        Thread wget = new Thread(new WgetFileDownload(sUrl, speed));
        wget.start();
        wget.join();
	}	
}
