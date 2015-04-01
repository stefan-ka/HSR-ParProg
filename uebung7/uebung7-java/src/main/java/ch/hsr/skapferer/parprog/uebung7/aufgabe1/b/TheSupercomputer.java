package ch.hsr.skapferer.parprog.uebung7.aufgabe1.b;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class TheSupercomputer extends SwingWorker<String, Void> {

	private enum StatusType {
		BORED, STARTING, CONFUSED, ERROR, RECOVERING_FROM_ERROR, OVERHEATED, CALCULATING
	}

	private static final int ITERATIONS = 5;
	StatusType status = StatusType.BORED;
	private int dotsCount = 0;
	private JLabel resultLabel;

	public TheSupercomputer(JLabel resultLabel) {
		this.resultLabel = resultLabel;
	}

	@Override
	protected String doInBackground() throws Exception {
		return calculateUltimateAnswerToTheUltimateQuestionOfLifeTheUniverseAndEverything();
	}

	@Override
	protected void done() {
		try {
			resultLabel.setText("Result: " + get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public String calculateUltimateAnswerToTheUltimateQuestionOfLifeTheUniverseAndEverything() {
		try {
			int firstPart = calculateFirstPart();
			int secondPart = calculateSecondPart();
			int modulo = calculateModulo();
			status = StatusType.BORED;
			return Integer.toString(firstPart * secondPart % modulo);

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private int calculateModulo() throws InterruptedException {
		status = StatusType.OVERHEATED;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 53;
	}

	private int calculateSecondPart() throws InterruptedException {
		status = StatusType.CONFUSED;
		doSleepLoop();
		doSleepLoop();
		status = StatusType.ERROR;
		doSleepLoop();
		doSleepLoop();
		status = StatusType.RECOVERING_FROM_ERROR;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 41;
	}

	private int calculateFirstPart() throws InterruptedException {
		status = StatusType.STARTING;
		doSleepLoop();
		status = StatusType.CALCULATING;
		doSleepLoop();
		return 23;
	}

	private void doSleepLoop() throws InterruptedException {
		for (int i = 0; i < ITERATIONS; i++) {
			Thread.sleep(200);
			dotsCount %= ITERATIONS;
			dotsCount++;
		}
	}

	public String getStatus() {
		return status.name() + makeDotStr();
	}

	private String makeDotStr() {
		char[] chars = new char[dotsCount];
		Arrays.fill(chars, '.');
		return new String(chars);
	}

}
