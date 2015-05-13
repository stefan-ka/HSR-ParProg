
using MPI;
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
            using (new MPI.Environment(ref args))
            {
                int rank = Communicator.world.Rank;
                int size = Communicator.world.Size;

                int height = ImageHeight / size;
                int[,] pixels = ComputeJuliaSet(rank * height, height);

                if (rank > 0)
                {
                    Communicator.world.Send(pixels, 0, 0);
                }
                else
                {
                    int[,] resultPixels = new int[ImageHeight, ImageWidth];
                    for (int y = 0; y < pixels.GetLength(0); y++)
                    {
                        for (int x = 0; x < pixels.GetLength(1); x++)
                        {
                            resultPixels[y, x] = pixels[y, x];
                        }
                    }

                    for (int r = 1; r < size; r++)
                    {
                        int[,] tmpPixels;
                        Communicator.world.Receive(r, 0, out tmpPixels);
                        for (int y = 0; y < tmpPixels.GetLength(0); y++)
                        {
                            for (int x = 0; x < tmpPixels.GetLength(1); x++)
                            {
                                if ((y + (r * height)) < ImageHeight)
                                {
                                    resultPixels[y + (r * height), x] = tmpPixels[y, x];
                                }
                            }
                        }
                    }

                    BitmapHelper.WriteBitmap(resultPixels, OutputFile);
                }
            }
        }

        private static int[,] ComputeJuliaSet(int rowStart, int height)
        {
            Stopwatch computing = Stopwatch.StartNew();
            int[,] pixels = new int[height, ImageWidth];
            for (int y = rowStart; y < height; y++)
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
