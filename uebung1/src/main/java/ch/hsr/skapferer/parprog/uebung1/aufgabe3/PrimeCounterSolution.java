package ch.hsr.skapferer.parprog.uebung1.aufgabe3;

import java.util.ArrayList;
import java.util.List;

public class PrimeCounterSolution {
	private static boolean isPrime(long number) {
		for (long factor = 2; factor * factor <= number; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private long countPrimes(long start, long end, int amountOfThreads) {
		List<PrimeCounterThread> threads = new ArrayList<>();
		long localStart = start;
		long rangePerThread = (end - start) / amountOfThreads;
		for (int i = 0; i < amountOfThreads; i++) {
			PrimeCounterThread thread = new PrimeCounterThread(localStart, localStart + rangePerThread);
			localStart = localStart + rangePerThread + 1;
			thread.start();
			threads.add(thread);
		}

		long count = 0;
		for (PrimeCounterThread thread : threads) {
			try {
				thread.join();
				count = count + thread.getCount();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	private class PrimeCounterThread extends Thread {

		private long start;
		private long end;
		private long count = 0;

		public PrimeCounterThread(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			for (long number = start; number < end; number++) {
				if (isPrime(number)) {
					count++;
				}
			}
			super.run();
		}

		public long getCount() {
			return count;
		}
	}

	private static final long START = 1_000_000L;
	private static final long END = 10_000_000L;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		PrimeCounterSolution counter = new PrimeCounterSolution();
		long count = counter.countPrimes(START, END, 8);
		long endTime = System.currentTimeMillis();
		System.out.println("#Primes: " + count + " Time: " + (endTime - startTime) + " ms");
	}
}
