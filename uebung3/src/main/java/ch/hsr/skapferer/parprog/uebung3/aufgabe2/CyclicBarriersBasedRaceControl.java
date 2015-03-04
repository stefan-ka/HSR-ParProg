package ch.hsr.skapferer.parprog.uebung3.aufgabe2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarriersBasedRaceControl extends AbstractRaceControl {

	private CyclicBarrier barrier;
	private boolean isOver = false;

	public CyclicBarriersBasedRaceControl() {
		barrier = new CyclicBarrier(NOF_RACE_CARS + 1);
	}

	@Override
	protected void waitForAllToBeReady() throws InterruptedException {
		await();
	}

	@Override
	public void readyToStart() {
		await();
	}

	@Override
	protected void giveStartSignal() {
		await();
	}

	@Override
	public void waitForStartSignal() throws InterruptedException {
		await();
	}

	@Override
	protected void waitForFinishing() throws InterruptedException {
		await();
	}

	@Override
	public boolean isOver() {
		return isOver;
	}

	@Override
	public void passFinishLine() {
		isOver = true;
		await();
	}

	@Override
	public void waitForLapOfHonor() throws InterruptedException {
		await();
	}

	private void await() {
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

}
