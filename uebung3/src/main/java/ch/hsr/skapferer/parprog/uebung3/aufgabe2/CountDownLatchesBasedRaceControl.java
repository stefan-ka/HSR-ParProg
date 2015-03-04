package ch.hsr.skapferer.parprog.uebung3.aufgabe2;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchesBasedRaceControl extends AbstractRaceControl {

	private CountDownLatch allCarsReady;
	private CountDownLatch startSignal;
	private CountDownLatch finishSignal;
	private CountDownLatch lapOfHonor;
	private boolean isOver = false;

	public CountDownLatchesBasedRaceControl() {
		allCarsReady = new CountDownLatch(NOF_RACE_CARS);
		startSignal = new CountDownLatch(1);
		finishSignal = new CountDownLatch(1);
		lapOfHonor = new CountDownLatch(NOF_RACE_CARS);
	}

	@Override
	protected void waitForAllToBeReady() throws InterruptedException {
		allCarsReady.await();
	}

	@Override
	public void readyToStart() {
		allCarsReady.countDown();
	}

	@Override
	public void waitForStartSignal() throws InterruptedException {
		startSignal.await();
	}

	@Override
	protected void giveStartSignal() {
		startSignal.countDown();
	}

	@Override
	protected void waitForFinishing() throws InterruptedException {
		finishSignal.await();
	}

	@Override
	public boolean isOver() {
		return isOver;
	}

	@Override
	public void passFinishLine() {
		finishSignal.countDown();
		lapOfHonor.countDown();
		isOver = true;
	}

	@Override
	public void waitForLapOfHonor() throws InterruptedException {
		lapOfHonor.await();
	}

}
