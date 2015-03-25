using System;
using System.Diagnostics;
using System.Net.Http;

namespace DownloadPerformance {
  class Program {
    public static void Main(string[] args) {
      new Program()._MeasurePerformance();
    }

    private void _MeasurePerformance() {
      Stopwatch watch = Stopwatch.StartNew();
      _DownloadWebsite("http://www.google.com");
      _DownloadWebsite("http://www.bing.com");
      _DownloadWebsite("http://www.yahoo.com");
      _DownloadWebsite("http://msdn.microsoft.com");
      _DownloadWebsite("http://www.facebook.com");
      _DownloadWebsite("http://www.xing.com");
      Console.WriteLine("Elapsed {0} ms", watch.ElapsedMilliseconds);
    }

    private static void _DownloadWebsite(string url) {
      Stopwatch watch = Stopwatch.StartNew();
      HttpClient client = new HttpClient();
      string html = client.GetStringAsync(url).Result;
      Console.WriteLine("{0} downloaded (length {1}): {2} ms", url, html.Length, watch.ElapsedMilliseconds); 
    }
  }
}
