using System;
using System.Threading.Tasks;
using System.Windows;

namespace TestUIThread
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            numberTextBox.Text = "20000000000000003";
        }

        private async void startCalculationButton_Click(object sender, RoutedEventArgs e)
        {
            calculationResultLabel.Content = "(computing)";
            long number;
            if (long.TryParse(numberTextBox.Text, out number))
            {
                if (await _IsPrime(number))
                {
                    calculationResultLabel.Content = "Prime";
                }
                else
                {
                    calculationResultLabel.Content = "No prime";
                }
            }
        }

        private Task<bool> _IsPrime(long number)
        {
            return Task.Run(() =>
            {
                for (long i = 2; i <= Math.Sqrt(number); i++)
                {
                    if (number % i == 0)
                    {
                        return false;
                    }
                }
                return true;
            });
        }
    }
}
