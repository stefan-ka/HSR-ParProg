package ch.hsr.skapferer.parprog.uebung3.aufgabe2;

public class FormulaOne {
	public static void main(String[] args) throws InterruptedException {
//		new MonitorBasedRaceControl().runRace();
//		System.out.println("----------------------------------------------------------------------------------------");
//		new CountDownLatchesBasedRaceControl().runRace();
//		System.out.println("----------------------------------------------------------------------------------------");
		new CyclicBarriersBasedRaceControl().runRace();
	}
}
