package ch.hsr.skapferer.parprog.uebung1.aufgabe1.threadsubclass;

public class ConsoleTicker extends Thread {

	private String sign;
	private int interval;

	public ConsoleTicker(String sign, int interval) {
		this.sign = sign;
		this.interval = interval;
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
	}
}
