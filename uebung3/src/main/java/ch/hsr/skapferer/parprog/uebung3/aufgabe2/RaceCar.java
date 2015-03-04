package aufgabe2;

public class RaceCar extends Thread {
	private AbstractRaceControl theRace;
	private int lapsToGo;

	public RaceCar(AbstractRaceControl myRace, String name, int nofLaps) {
		super(name);
		theRace = myRace;
		lapsToGo = nofLaps;
	}

	public void run() {
		try {
			printState("starting engine, driving slowly");
			theRace.readyToStart();
			theRace.waitForStartSignal();
			printState("racing...");
			while (lapsToGo > 0 && !theRace.isOver()) {
				printState("remaining laps: " + lapsToGo);
				simulateRaceLap();
				lapsToGo--;
			}
			printState("pass finish line");
			theRace.passFinishLine();
			theRace.waitForLapOfHonor();
			printState("waves to audience");
			simulateRaceLap();
		} catch (InterruptedException e) {
			throw new AssertionError();
		}
	}

	private void simulateRaceLap() throws InterruptedException {
		Thread.sleep((int) (1000 * Math.random()));
	}

	private void printState(String message) {
		System.out.println(getName() + " " + message);
	}
}
