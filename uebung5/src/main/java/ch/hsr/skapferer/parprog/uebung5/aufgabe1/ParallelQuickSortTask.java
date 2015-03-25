package ch.hsr.skapferer.parprog.uebung5.aufgabe1;

import java.util.concurrent.RecursiveAction;

public class ParallelQuickSortTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private final static int T = 1000;

	private int[] array;
	private int left;
	private int right;
	private int i = left, j = right;

	public ParallelQuickSortTask(int[] array, int left, int right) {
		this.array = array;
		this.left = left;
		this.right = right;
	}

	@Override
	protected void compute() {
		quickSort();
		if (j - left > T && right - i > T) {
			ParallelQuickSortTask leftTask = new ParallelQuickSortTask(array, left, j);
			ParallelQuickSortTask rightTask = new ParallelQuickSortTask(array, i, right);
			leftTask.fork();
			rightTask.fork();
			invokeAll(leftTask, rightTask);
		} else {
			quickSort();
		}
	}

	private void quickSort() {
		long m = array[(left + right) / 2];
		do {
			while (array[i] < m) {
				i++;
			}
			while (array[j] > m) {
				j--;
			}
			if (i <= j) {
				int t = array[i];
				array[i] = array[j];
				array[j] = t;
				i++;
				j--;
			}
		} while (i <= j);
	}

}
