package ch.hsr.skapferer.parprog.uebung1.aufgabe1.threadsubclass;

import java.util.Scanner;

public class ConsoleTicker extends Thread {

	private String sign;
	private int interval;

	public ConsoleTicker(String sign, int interval) {
		this.sign = sign;
		this.interval = interval;
		setDaemon(true);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("shutdown hook");
				super.run();
			}
		});
	}

	@Override
	public void run() {
		while (true) {
			System.out.print(sign);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new ConsoleTicker(".", 10).start();
		new ConsoleTicker("*", 20).start();

		Scanner scanIn = new Scanner(System.in);
		scanIn.nextLine();
		scanIn.close();
	}
}
