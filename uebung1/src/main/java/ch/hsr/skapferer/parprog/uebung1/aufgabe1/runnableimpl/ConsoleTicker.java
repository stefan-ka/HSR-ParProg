package ch.hsr.skapferer.parprog.uebung1.aufgabe1.runnableimpl;

public class ConsoleTicker implements Runnable {

	private String sign;
	private int interval;

	public ConsoleTicker(String sign, int interval) {
		this.sign = sign;
		this.interval = interval;
	}

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
		new Thread(new ConsoleTicker(".", 10)).start();
		new Thread(new ConsoleTicker("*", 20)).start();
	}
}
