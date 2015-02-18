package ch.hsr.skapferer.parprog.uebung1.aufgabe1.java8lambda;

public class ConsoleTicker {

	public void periodTicker(String sign, int interval) {
		while (true) {
			System.out.print(sign);
			try {
				Thread.sleep(interval);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new Thread(() -> {
			new ConsoleTicker().periodTicker(".", 10);
		}).start();
		new Thread(() -> {
			new ConsoleTicker().periodTicker("*", 20);
		}).start();
	}
}
