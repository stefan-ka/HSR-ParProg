// Parallel Programming Course, L. Bläser
using System;
using System.Collections.Concurrent;
using System.Linq;
using System.Threading.Tasks;

namespace ParallelPrimeCheck {
  public class PrimeCheck {
    public static bool[] SequentialCheckPrimes(int[] numbers) {
      var result = new bool[numbers.Length];
      for (int i = 0; i < numbers.Length; i++) {
        result[i] = _IsPrime(numbers[i]);
      }
      return result;
    }

    public static bool[] ParallelForCheckPrimesUnpartitioned(int[] numbers) {
      // TODO: Implement parallel prime check with parallel for loop (no range partitioner)
    }

    public static bool[] ParallelForCheckPrimesPartitioned(int[] numbers) {
      // TODO: Implement parallel prime check with parallel loop and range partitioner
    }

    public static bool[] ParallelLinqCheckPrimes(int[] numbers) {
      // TODO: Implement parallel prime check as parallel LINQ
    }

    private static bool _IsPrime(int number) {
      for (int i = 2; i * i <= number; i++) {
        if (number % i == 0) {
          return false;
        }
      }
      return true;
    }
  }
}
