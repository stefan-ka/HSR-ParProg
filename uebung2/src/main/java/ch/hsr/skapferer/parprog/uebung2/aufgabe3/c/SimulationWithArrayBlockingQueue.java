package ch.hsr.skapferer.parprog.uebung2.aufgabe3.c;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

class Producer extends Thread {
	private final ArrayBlockingQueue<Long> buffer;
	private final int nofItems;

	public Producer(ArrayBlockingQueue<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		Random random = new Random();
		for (int i = 0; i < nofItems; i++) {
			try {
				buffer.put(random.nextLong());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Producer finished " + getName());
	}
}

class Consumer extends Thread {
	private final ArrayBlockingQueue<Long> buffer;
	private final int nofItems;

	public Consumer(ArrayBlockingQueue<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		for (int i = 0; i < nofItems; i++) {
			try {
				buffer.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Consumer finished " + getName());
	}
}

public class SimulationWithArrayBlockingQueue {

	public static void test(int noProducers, int noConsumers, int bufferCapacity, int elementsPerProducer, int elementsPerConsumer) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		ArrayBlockingQueue<Long> buffer = new ArrayBlockingQueue<Long>(bufferCapacity);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < noProducers; i++) {
			threads.add(new Producer(buffer, elementsPerProducer));
		}
		for (int i = 0; i < noConsumers; i++) {
			threads.add(new Consumer(buffer, elementsPerConsumer));
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Producer-consumer simulation finished");
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
	}

	public static void main(String[] args) throws InterruptedException {
		test(1, 1, 1, 1000000, 1000000);
		test(1, 10, 1, 1000000, 100000);
	}
}
