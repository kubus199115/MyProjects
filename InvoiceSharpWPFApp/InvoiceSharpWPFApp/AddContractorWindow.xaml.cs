using InvoiceSharpWPFApp.Dto;
using InvoiceSharpWPFApp.Service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace InvoiceSharpWPFApp
{
    /// <summary>
    /// Logika interakcji dla klasy AddContractorWindow.xaml
    /// </summary>
    public partial class AddContractorWindow : Window
    {
        private ContractorDTO contractorDTO;
        private ContractorService contractorService;

        public AddContractorWindow()
        {
            InitializeComponent();

            contractorDTO = new ContractorDTO();
            contractorService = new ContractorService();
            // dataContext to binding
            DataContext = contractorDTO;

        }

        // adding new Contractor button, reading response from contractor service
        private void AddNewContractor_Click(object sender, RoutedEventArgs e)
        {
            ResponseDTO response = contractorService.AddContractor(contractorDTO);

            switch (response.statusCode)
            {
                case 200:
                    MessageBox.Show("New contractor created", "Contractor added", MessageBoxButton.OK, MessageBoxImage.Information);
                    break;
                case 400:
                    MessageBox.Show(response.content, "Validation error!", MessageBoxButton.OK, MessageBoxImage.Warning);
                    break;
                // if contractor name already exist
                case 409:
                    MessageBox.Show(response.content, "Information", MessageBoxButton.OK, MessageBoxImage.Information);
                    break;
                case 500:
                    MessageBox.Show(response.content, "Internal server error!", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;


            }
        }

        // When closing window, set DialogResult. It is necessarily to inform MainWindow when this dialog is closing
        private void WindowClosing(object sender, CancelEventArgs e)
        {
            DialogResult = true;
        }

        // allowing only numbers in textbox
        private void OnlyNumbers_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }
    }
}
