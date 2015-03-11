package ch.hsr.skapferer.parprog.uebung4_testat1.aufgabe1;

import java.util.concurrent.CountDownLatch;

public class BrokenCyclicLatch {
	private static final int NOF_ROUNDS = 10;
	private static final int NOF_THREADS = 10;

	private static CountDownLatch latch = new CountDownLatch(NOF_THREADS);

	private static void multiRounds(int threadId) throws InterruptedException {
		for (int round = 0; round < NOF_ROUNDS; round++) {
			latch.countDown();
			latch.await();

			/**
			 * Problematik: Sobald eine Runde beendet ist (CountDownLatch steht
			 * auf 0) muesste eine neue CountDownLatch erzeugt werden. Dies wird
			 * hier zwar vom Thread mit der ID 0 gemacht, aber man kann nicht
			 * sicher sein dass dieser der "erste" Thread ist welcher aus dem
			 * await() kommt und wieder loslaeuft. Wenn ein anderer Thread
			 * zuerst startet, geht dieser in die naechste Runde obwohl die
			 * Latch noch gar nicht neu erzeugt wurde.
			 * 
			 */

			if (threadId == 0) {
				latch = new CountDownLatch(NOF_THREADS); // new latch for new
															// round
			}
			System.out.println("Round " + round + " thread " + threadId);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < NOF_THREADS; i++) {
			final int threadId = i;
			new Thread() {
				@Override
				public void run() {
					try {
						multiRounds(threadId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
