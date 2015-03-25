using System;

namespace ParallelQuickSort
{
    public class ParallelQuickSort
    {
        private int _parallelThreshold;

        public ParallelQuickSort(int parallelThreshold)
        {
            if (_parallelThreshold <= 0)
            {
                new ArgumentException("parallelThreshold must be greater than 0");
            }
            _parallelThreshold = parallelThreshold;
        }

        public void Sort(long[] numberArray)
        {
            if (numberArray == null || numberArray.Length == 0)
            {
                new ArgumentException("number array must be at least of length 1");
            }
            _QuickSort(numberArray, 0, numberArray.Length - 1);
        }

        private void _QuickSort(long[] array, int left, int right)
        {
            int i = left, j = right;
            long m = array[(left + right) / 2];
            do
            {
                while (array[i] < m) { i++; }
                while (array[j] > m) { j--; }
                if (i <= j)
                {
                    long t = array[i]; array[i] = array[j]; array[j] = t;
                    i++; j--;
                }
            } while (i <= j);
            // TODO: Parallelize if (j - left > _parallelThreshold && right - i > _parallelThreshold)    
            if (j > left) { _QuickSort(array, left, j); }
            if (i < right) { _QuickSort(array, i, right); }
        }
    }
}
