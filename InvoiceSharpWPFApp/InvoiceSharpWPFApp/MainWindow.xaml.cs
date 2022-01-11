using InvoiceSharpWPFApp.Dto;
using InvoiceSharpWPFApp.Generator;
using InvoiceSharpWPFApp.Service;
using System;
using System.Collections.Generic;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace InvoiceSharpWPFApp
{
    /// <summary>
    /// Logika interakcji dla klasy MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        private List<ContractorDTO> contractors;
        private ContractorDTO selectedContractor;

        private List<InvoiceDTO> invoices;
        private InvoiceDTO selectedInvoice;

        private ContractorService contractorService;
        private InvoiceService invoiceService;

        public MainWindow()
        {
            InitializeComponent();

            contractorService = new ContractorService();
            //selectedContractor = new ContractorDTO();
            contractors = contractorService.getContractors();

            invoiceService = new InvoiceService();
            invoices = invoiceService.getInvoices();

            // setting item source
            lvContractors.ItemsSource = contractors;
            lvInvoices.ItemsSource = invoices;

            // data context binding to settings
            DataContext = Properties.Settings.Default;


        }

        // adding new contractor button in main window. This show new dialog window with new contractor form
        private void AddContractor_Click(object sender, RoutedEventArgs e)
        {
            AddContractorWindow addContractorWindow = new AddContractorWindow();
            bool? dialogResult = addContractorWindow.ShowDialog();

            // refreshing list, when dialog window (addContractorWindow) is closed
            if (dialogResult == true)
            {
                contractors = contractorService.getContractors();
                lvContractors.ItemsSource = contractors;
                lvContractors.Items.Refresh();
            }

        }

        // removing button in main window. This removing selected contractor from data base
        private void RemoveContractor_Click(object sender, RoutedEventArgs e)
        {
            // setting selected item
            selectedContractor = lvContractors.SelectedItem as ContractorDTO;
            // checking if any contractor is selected
            if(selectedContractor == null)
            {
                MessageBox.Show("Please choose contractor to remove", "Choose contractor", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            ResponseDTO response = contractorService.removeContractor(selectedContractor.name);

            switch (response.statusCode)
            {
                case 200:
                    MessageBox.Show("Contractor removed", "Contractor removed", MessageBoxButton.OK, MessageBoxImage.Information);
                    contractors = contractorService.getContractors();
                    lvContractors.ItemsSource = contractors;
                    lvContractors.Items.Refresh();
                    break;
                // if contractor is asigned to invoice
                case 409:
                    MessageBox.Show(response.content, "Information", MessageBoxButton.OK, MessageBoxImage.Information);
                    break;
                case 500:
                    MessageBox.Show(response.content, "Internal server error!", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;

            }
        }

        // showing new outgoing invoice window form
        private void AddInvoice_Click(object sender, RoutedEventArgs e)
        {
            AddInvoiceWindow addInvoiceWindow = new AddInvoiceWindow();
            bool? dialogResult = addInvoiceWindow.ShowDialog();

            // refreshing list, when dialog window (addInvoiceWindow) is closed
            if (dialogResult == true)
            {
                invoices = invoiceService.getInvoices();
                lvInvoices.ItemsSource = invoices;
                lvInvoices.Items.Refresh();
            }
        }

        // showing new incoming invoice window form
        private void AddIncomingInvoice_Click(object sender, RoutedEventArgs e)
        {
            AddIncomingInvoiceWindow addIncomingInvoiceWindow = new AddIncomingInvoiceWindow();
            bool? dialogResult = addIncomingInvoiceWindow.ShowDialog();

            // refreshing list, when dialog window (addInvoiceWindow) is closed
            if (dialogResult == true)
            {
                invoices = invoiceService.getInvoices();
                lvInvoices.ItemsSource = invoices;
                lvInvoices.Items.Refresh();
            }
        }

        // removing invoice button. This removing selected invoice from db
        private void RemoveInvoice_Click(object sender, RoutedEventArgs e)
        {
            // setting selected item
            selectedInvoice = lvInvoices.SelectedItem as InvoiceDTO;
            // checking if any contractor is selected
            if (selectedInvoice == null)
            {
                MessageBox.Show("Please choose invoice to remove", "Choose invoice", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }

            ResponseDTO response = invoiceService.removeInvoice(selectedInvoice.invoiceNumber);

            switch (response.statusCode)
            {
                case 200:
                    MessageBox.Show("Invoice removed", "Invoice removed", MessageBoxButton.OK, MessageBoxImage.Information);
                    invoices = invoiceService.getInvoices();
                    lvInvoices.ItemsSource = invoices;
                    lvInvoices.Items.Refresh();
                    break;
                case 500:
                    MessageBox.Show(response.content, "Internal server error!", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;

            }
        }

        // print/details button. Showing pdf file
        private void DetailsPrint_Click(object sender, RoutedEventArgs e)
        {
            // setting selected item
            selectedInvoice = lvInvoices.SelectedItem as InvoiceDTO;
            // checking if any contractor is selected
            if (selectedInvoice == null)
            {
                MessageBox.Show("Please choose invoice to print", "Choose invoice", MessageBoxButton.OK, MessageBoxImage.Warning);
                return;
            }
            InvoiceDetailsDTO invoiceDetails = invoiceService.getInvoiceDetails(selectedInvoice.invoiceNumber);

            PdfGenerator generator = new PdfGenerator(invoiceDetails);
            generator.genPdf();
        }

        // allowing only numbers in textbox
        private void OnlyNumbers_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        // saving user settings
        private void SaveSettings_Click(object sender, RoutedEventArgs e)
        {
            Properties.Settings.Default.Save();
        }
    }
}
