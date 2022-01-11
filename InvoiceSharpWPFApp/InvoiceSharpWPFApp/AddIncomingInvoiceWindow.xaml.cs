using InvoiceSharpWPFApp.Dto;
using InvoiceSharpWPFApp.Generator;
using InvoiceSharpWPFApp.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
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
    /// Logika interakcji dla klasy AddIncomingInvoiceWindow.xaml
    /// </summary>
    public partial class AddIncomingInvoiceWindow : Window
    {
        private InvoiceDetailsDTO invoiceDetailsDTO;
        private InvoiceService invoiceService;
        private ContractorService contractorService;
        private List<ContractorDTO> contractors;

        public AddIncomingInvoiceWindow()
        {
            InitializeComponent();

            invoiceDetailsDTO = new InvoiceDetailsDTO();
            invoiceService = new InvoiceService();
            contractorService = new ContractorService();

            // setting incoming invoice
            invoiceDetailsDTO.invoice.type = "Incoming";

            // getting contractors to comboBox
            contractors = contractorService.getContractors();
            ContractorsComboBox.ItemsSource = contractors;

            // data binding
            DataContext = invoiceDetailsDTO;
            Items.ItemsSource = invoiceDetailsDTO.items;
        }

        // adding new Invoice button, reading response from invoice service
        private void AddInvoice_Click(object sender, RoutedEventArgs e)
        {
            // calculating invoice total value
            decimal total = 0;
            foreach (ItemDTO item in invoiceDetailsDTO.items)
            {
                total += item.grossValue;
            }
            invoiceDetailsDTO.invoice.totalValue = total;

            ResponseDTO response = invoiceService.addInvoice(invoiceDetailsDTO);
            switch (response.statusCode)
            {
                case 200:
                    MessageBox.Show("New invoice created", "Invoice added", MessageBoxButton.OK, MessageBoxImage.Information);
                    DialogResult = true;
                    break;
                case 400:
                    MessageBox.Show(response.content, "Validation error!", MessageBoxButton.OK, MessageBoxImage.Warning);
                    break;
                // if invoice number already exist
                case 409:
                    MessageBox.Show(response.content, "Information", MessageBoxButton.OK, MessageBoxImage.Information);
                    break;
                case 500:
                    MessageBox.Show(response.content, "Internal server error!", MessageBoxButton.OK, MessageBoxImage.Error);
                    break;

            }

        }

        // print/details button. Showing pdf file
        private void PrintDetail_Click(object sender, RoutedEventArgs e)
        {
            PdfGenerator generator = new PdfGenerator(invoiceDetailsDTO);
            generator.genPdf();

        }

    }
}
