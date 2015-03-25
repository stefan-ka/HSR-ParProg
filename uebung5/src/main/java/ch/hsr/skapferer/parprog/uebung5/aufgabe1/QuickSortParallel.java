package ch.hsr.skapferer.parprog.uebung5.aufgabe1;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class QuickSortParallel {
	private static final int NOF_ELEMENTS = 50_000_000;

	private ForkJoinPool pool;

	public QuickSortParallel() {
		pool = new ForkJoinPool();
	}

	public void sort(int[] array, int left, int right) {
		ParallelQuickSortTask task = new ParallelQuickSortTask(array, left, right);
		pool.invoke(task);
	}

	private static int[] createRandomArray(int length) {
		Random random = new Random(4711);
		int[] numberArray = new int[length];
		for (int i = 0; i < length; i++) {
			numberArray[i] = random.nextInt();
		}
		return numberArray;
	}

	private static void checkSorted(int[] numberArray) {
		for (int i = 0; i < numberArray.length - 1; i++) {
			if (numberArray[i] > numberArray[i + 1]) {
				throw new RuntimeException("Not sorted");
			}
		}
	}

	public static void main(String[] args) {
		int[] numberArray = createRandomArray(NOF_ELEMENTS);
		long startTime = System.currentTimeMillis();
		QuickSortParallel quickSort = new QuickSortParallel();
		quickSort.sort(numberArray, 0, numberArray.length - 1);
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
		checkSorted(numberArray);
	}
}
