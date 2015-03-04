package ch.hsr.skapferer.parprog.uebung3.aufgabe1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseWithLockCondition implements Warehouse {

	private int capacity;
	private int currentAmount = 0;
	private boolean fair;

	private Lock monitor;
	private Condition nonFull;
	private Condition nonEmpty;

	public WarehouseWithLockCondition(int capacity, boolean fair) {
		this.capacity = capacity;
		this.fair = fair;
		this.monitor = new ReentrantLock(this.fair);
		this.nonFull = this.monitor.newCondition();
		this.nonEmpty = this.monitor.newCondition();
	}

	@Override
	public void put(int amount) throws InterruptedException {
		monitor.lock();
		try {
			while ((currentAmount + amount) > capacity) {
				nonFull.await();
			}
			currentAmount = currentAmount + amount;
			nonEmpty.signalAll();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void get(int amount) throws InterruptedException {
		monitor.lock();
		try {
			while (currentAmount < amount) {
				nonEmpty.await();
			}
			currentAmount = currentAmount - amount;
			nonFull.signalAll();
		} finally {
			monitor.unlock();
		}
	}
}
