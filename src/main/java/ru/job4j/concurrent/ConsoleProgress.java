package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

	@Override
	public void run() {
		var process = new char[] {'-', '\\',  '|', '/'};
		int i = 0;
		while (!Thread.currentThread().isInterrupted()) {
			if (i >= process.length) {
				i = 0;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;				
			}			
			System.out.print("\r load: " + process[i++]);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
	}	

}
