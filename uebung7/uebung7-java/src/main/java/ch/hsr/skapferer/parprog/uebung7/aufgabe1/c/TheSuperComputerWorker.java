package ch.hsr.skapferer.parprog.uebung7.aufgabe1.c;

import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class TheSuperComputerWorker extends SwingWorker<String, Void> {

	private TheSupercomputer theSuperComputer;
	private JLabel resultLabel;

	public TheSuperComputerWorker(TheSupercomputer theSuperComputer, JLabel resultLabel) {
		this.theSuperComputer = theSuperComputer;
		this.resultLabel = resultLabel;
	}

	@Override
	protected String doInBackground() throws Exception {
		return theSuperComputer.calculateUltimateAnswerToTheUltimateQuestionOfLifeTheUniverseAndEverything();
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

}
