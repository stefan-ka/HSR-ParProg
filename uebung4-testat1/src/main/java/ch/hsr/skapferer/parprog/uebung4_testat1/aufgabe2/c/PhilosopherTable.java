package ch.hsr.skapferer.parprog.uebung4_testat1.aufgabe2.c;

import java.util.Observable;
import java.util.concurrent.Semaphore;

class PhiloState {
	public static final int thinking = 0;
	public static final int hungry = 1;
	public static final int eating = 2;
}

class Philosopher extends Thread {
	private PhilosopherTable table;
	private int philoState = PhiloState.thinking;
	private int id;

	public Philosopher(PhilosopherTable table, int id) {
		this.id = id;
		this.table = table;
	}

	public int getPhiloState() {
		return philoState;
	}

	public long getId() {
		return id;
	}

	private void think() throws InterruptedException {
		philoState = PhiloState.thinking;
		table.notifyStateChange(this);
		sleep((int) (Math.random() * 1500));
	}

	private void eat() throws InterruptedException {
		philoState = PhiloState.eating;
		table.notifyStateChange(this);
		sleep((int) (Math.random() * 1000));
	}

	private void takeForks() throws InterruptedException {
		philoState = PhiloState.hungry;
		table.notifyStateChange(this);

		int leftForkNo = table.leftForkNumber(id);
		int rightForkNo = table.rightForkNumber(id);

		/**
		 * *********AENDERUNG***********
		 * 
		 * Nimm immer die Gabel mit der kleineren Id:
		 */
		int firstForkNo = Math.min(leftForkNo, rightForkNo);
		int secondForkNo = Math.max(leftForkNo, rightForkNo);

		table.acquireFork(firstForkNo);
		while (!table.tryAcquireFork(secondForkNo)) {
			System.out.println("Philosophers " + getId() + " retries...");
			table.releaseFork(firstForkNo);
			sleep(500);
			table.acquireFork(firstForkNo);
		}
		/**
		 * *********AENDERUNG***********
		 */
	}

	private void putForks() {
		table.releaseFork(table.leftForkNumber(id));
		table.releaseFork(table.rightForkNumber(id));
	}

	public void run() {
		yield();
		while (true) {
			try {
				think();
				takeForks();
				eat();
				putForks();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class PhilosopherTable extends Observable {
	private int nofPhilosophers;
	private Semaphore[] forks;
	private Philosopher[] philosophers;

	public PhilosopherTable(int nofPhilosophers) {
		System.out.println("creating table ...");
		this.nofPhilosophers = nofPhilosophers;
		forks = new Semaphore[nofPhilosophers];
		for (int i = 0; i < nofPhilosophers; i++) {
			forks[i] = new Semaphore(1);
		}
		philosophers = new Philosopher[nofPhilosophers];
		for (int i = 0; i < nofPhilosophers; i++) {
			philosophers[i] = new Philosopher(this, i);
		}
	}

	public boolean tryAcquireFork(int forkNumber) {
		return forks[forkNumber].tryAcquire();
	}

	public void acquireFork(int forkNumber) throws InterruptedException {
		forks[forkNumber].acquire();
	}

	public void releaseFork(int forkNumber) {
		forks[forkNumber].release();
	}

	public int leftForkNumber(int philosopherId) {
		return philosopherId;
	}

	public int rightForkNumber(int philosopherId) {
		return (philosopherId + 1) % nofPhilosophers;
	}

	public void notifyStateChange(Philosopher sender) {
		setChanged();
		notifyObservers(sender);
	}

	public void start() {
		notifyStateChange(null);
		for (int i = nofPhilosophers - 1; i >= 0; i--) {
			philosophers[i].setPriority(Thread.MIN_PRIORITY);
			philosophers[i].start();
		}
	}

	public Philosopher getPhilo(int i) {
		return philosophers[i];
	}
}
