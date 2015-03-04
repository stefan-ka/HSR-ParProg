package ch.hsr.skapferer.parprog.uebung3.aufgabe1;

public class WarehouseWithMonitor implements Warehouse {

	private int capacity;
	private int currentAmount = 0;

	public WarehouseWithMonitor(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public synchronized void put(int amount) throws InterruptedException {
		while ((currentAmount + amount) > capacity)
			wait();

		currentAmount = currentAmount + amount;
		notifyAll();
	}

	@Override
	public synchronized void get(int amount) throws InterruptedException {
		while (currentAmount < amount)
			wait();

		currentAmount = currentAmount - amount;
		notifyAll();
	}
}
