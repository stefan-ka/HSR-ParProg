
using System;
using System.Diagnostics;
using System.Numerics;

namespace JuliaSet
{
    public class JuliaSet
    {
        private const string OutputFile = "juliaset.jpg";
        private const int ImageWidth = 4 * 1024;
        private const int ImageHeight = 4 * 1024;
        private const double ImageScale = 1.5;

        public static void Main(string[] args)
        {
            // TODO: Parallelize with MPI
            // Perform a row-wise partitioning of the image where each MPI process computes an independent partition.
            // Processes send their computed partitions to a common process that combines the partitions to the final image.

            int[,] pixels = ComputeJuliaSet();
            BitmapHelper.WriteBitmap(pixels, OutputFile);
        }

        private static int[,] ComputeJuliaSet()
        {
            Stopwatch computing = Stopwatch.StartNew();
            int[,] pixels = new int[ImageHeight, ImageWidth];
            for (int y = 0; y < ImageHeight; y++)
            {
                for (int x = 0; x < ImageWidth; x++)
                {
                    pixels[y, x] = JuliaValue(x, y);
                }
            }
            Console.WriteLine("Computed {0} ms", computing.ElapsedMilliseconds);
            return pixels;
        }

        private static int JuliaValue(int x, int y)
        {
            double jx = ImageScale * (double)(ImageWidth / 2 - x) / (ImageWidth / 2);
            double jy = ImageScale * (double)(ImageHeight / 2 - y) / (ImageHeight / 2);

            Complex c = new Complex(-.8f, .156f);
            Complex a = new Complex(jx, jy);

            int i;
            for (i = 0; i < 200 && a.Magnitude * a.Magnitude <= 1000; i++)
            {
                a = a * a + c;
            }
            return i;
        }
    }
}
