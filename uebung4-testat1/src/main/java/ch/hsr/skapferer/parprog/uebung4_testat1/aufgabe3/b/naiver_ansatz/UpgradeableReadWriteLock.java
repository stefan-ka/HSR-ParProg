package ch.hsr.skapferer.parprog.uebung4_testat1.aufgabe3.b.naiver_ansatz;

import java.util.concurrent.Semaphore;

public class UpgradeableReadWriteLock {

	private Semaphore upgradeSemaphore;
	private Thread writeThread = null;
	private Thread upgradeableThread = null;
	private int readerCounter = 0;

	public UpgradeableReadWriteLock() {
		upgradeSemaphore = new Semaphore(1);
	}

	public synchronized void readLock() throws InterruptedException {
		wait4Writer();
		readerCounter++;
	}

	public synchronized void readUnlock() {
		readerCounter--;
	}

	public synchronized void upgradeableReadLock() throws InterruptedException {
		wait4Writer();
		while (!upgradeSemaphore.tryAcquire()) {
			wait();
		}
		upgradeableThread = Thread.currentThread();
	}

	public synchronized void upgradeableReadUnlock() {
		upgradeableThread = null;
		upgradeSemaphore.release();
	}

	public synchronized void writeLock() throws InterruptedException {
		while (upgradeableThread != Thread.currentThread() && upgradeableThread != null) {
			wait();
		}
		wait4Readers();
		wait4Writer();
		writeThread = Thread.currentThread();
	}

	public synchronized void writeUnlock() {
		writeThread = null;
	}

	private void wait4Readers() throws InterruptedException {
		while (readerCounter > 0) {
			wait();
		}
	}

	private void wait4Writer() throws InterruptedException {
		while (writeThread != null) {
			wait();
		}
	}
}
