package ch.hsr.skapferer.parprog.uebung1.aufgabe4;

public class NeverEndingThread extends Thread {
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int i = 0;
		while (true) {
			i++;
			Thread thread = new NeverEndingThread();
			thread.start();
			System.out.println("Started Thread: " + i);
		}
	}
}
