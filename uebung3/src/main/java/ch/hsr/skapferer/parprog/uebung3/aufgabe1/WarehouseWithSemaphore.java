package ch.hsr.skapferer.parprog.uebung3.aufgabe1;

import java.util.concurrent.Semaphore;

public class WarehouseWithSemaphore implements Warehouse {

	private int capacity;
	private boolean fair;
	private int currentAmount = 0;
	private Semaphore upperLimit;
	private Semaphore lowerLimit;

	public WarehouseWithSemaphore(int capacity, boolean fair) {
		this.capacity = capacity;
		this.fair = fair;
		this.upperLimit = new Semaphore(this.capacity, this.fair);
		this.lowerLimit = new Semaphore(0, this.fair);
	}

	@Override
	public void put(int amount) throws InterruptedException {
		upperLimit.acquire(amount);
		synchronized (this) {
			currentAmount = currentAmount + amount;
		}
		lowerLimit.release(amount);
	}

	@Override
	public void get(int amount) throws InterruptedException {
		lowerLimit.acquire(amount);
		synchronized (this) {
			currentAmount = currentAmount - amount;
		}
		upperLimit.release(amount);
	}
}
