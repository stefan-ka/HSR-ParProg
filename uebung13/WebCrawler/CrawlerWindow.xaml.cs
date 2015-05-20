using System;
using System.Reactive.Linq;
using System.Reactive.Subjects;
using System.Windows;

namespace WebCrawler
{
    public partial class CrawlerWindow : Window
    {
        private WebClient webClient = new WebClient();
        private ReplaySubject<Uri> addresses = new ReplaySubject<Uri>();

        public CrawlerWindow()
        {
            InitializeComponent();
            DefineWorkflow();
        }

        private void DefineWorkflow()
        {
            // TODO: Define Rx workflow
            // TODO: Display each result with resultListView.Items.Add()
        }

        private void startButton_Click(object sender, RoutedEventArgs e)
        {
            Uri address = new Uri(addressTextBox.Text);
            // TODO: Push into Rx workflow
        }
    }
}
