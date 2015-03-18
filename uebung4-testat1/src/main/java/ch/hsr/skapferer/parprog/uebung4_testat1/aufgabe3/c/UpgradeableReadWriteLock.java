package ch.hsr.skapferer.parprog.uebung4_testat1.aufgabe3.c;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UpgradeableReadWriteLock {

	private ReentrantReadWriteLock readWriteLock;
	private ReentrantLock upgradeLock;

	public UpgradeableReadWriteLock() {
		readWriteLock = new ReentrantReadWriteLock(true);
		upgradeLock = new ReentrantLock(true);
	}

	public void readLock() throws InterruptedException {
		readWriteLock.readLock().lock();
	}

	public void readUnlock() {
		readWriteLock.readLock().unlock();
	}

	public void upgradeableReadLock() throws InterruptedException {
		upgradeLock.lock();
	}

	public void upgradeableReadUnlock() {
		upgradeLock.unlock();
	}

	public void writeLock() throws InterruptedException {
		upgradeLock.lock();
		readWriteLock.writeLock().lock();
	}

	public void writeUnlock() {
		readWriteLock.writeLock().unlock();
		upgradeLock.unlock();
	}
}
