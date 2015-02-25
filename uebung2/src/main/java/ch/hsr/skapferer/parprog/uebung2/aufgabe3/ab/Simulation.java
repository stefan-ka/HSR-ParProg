package ch.hsr.skapferer.parprog.uebung2.aufgabe3.ab;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class BoundedBuffer<T> {

	private int capacity;
	private Queue<T> queue = new LinkedList<>();

	public BoundedBuffer(int capacity) {
		this.capacity = capacity;
	}

	public synchronized void put(T item) {
		while (queue.size() == capacity) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		queue.add(item);
		notifyAll();
	}

	public synchronized T get() {
		while (queue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		return queue.remove();
	}
}

class Producer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Producer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		Random random = new Random();
		for (int i = 0; i < nofItems; i++) {
			buffer.put(random.nextLong());
		}
		System.out.println("Producer finished " + getName());
	}
}

class Consumer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Consumer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		for (int i = 0; i < nofItems; i++) {
			buffer.get();
		}
		System.out.println("Consumer finished " + getName());
	}
}

public class Simulation {

	public static void test(int noProducers, int noConsumers, int bufferCapacity, int elementsPerProducer, int elementsPerConsumer) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		BoundedBuffer<Long> buffer = new BoundedBuffer<Long>(bufferCapacity);
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
