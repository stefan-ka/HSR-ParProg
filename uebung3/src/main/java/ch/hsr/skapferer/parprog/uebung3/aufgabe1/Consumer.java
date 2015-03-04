package ch.hsr.skapferer.parprog.uebung3.aufgabe1;

import java.util.Random;

class Consumer extends Thread {
	private final Warehouse warehouse;
	private final int nofItems;
	private final int maxPerCall;

	public Consumer(Warehouse warehouse, int nofItems, int maxPerCall) {
		this.warehouse = warehouse;
		this.nofItems = nofItems;
		this.maxPerCall = maxPerCall;
	}

	public void run() {
		try {
			Random random = new Random(4711);
			int countAllItems = 0;
			while (countAllItems < nofItems) {
				int countPerCall = random.nextInt(maxPerCall) + 1;
				if (countAllItems + countPerCall > nofItems) {
					countPerCall = nofItems - countAllItems;
				}
				warehouse.get(countPerCall);
				countAllItems += countPerCall;
			}
		} catch (InterruptedException e) {
			throw new AssertionError();
		}
	}
}
