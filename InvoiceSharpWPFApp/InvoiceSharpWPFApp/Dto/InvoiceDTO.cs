using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Dto
{
    public class InvoiceDTO
    {
        public InvoiceDTO()
        {
            // setting default values
            type = "Outgoing";
            dateOfIssue = DateTime.Now;
            dateOfSale = DateTime.Now;
            methodOfPayment = "Cash";
            totalValue = 0.00M;
        }

        public string invoiceNumber { get; set; }

        public string type { get; set; }

        public string placeOfIssue { get; set; }

        public DateTime dateOfIssue { get; set; }

        public DateTime dateOfSale { get; set; }

        public string methodOfPayment { get; set; }

        public string dateOfPayment { get; set; }

        public string accountNumber { get; set; }

        public decimal totalValue { get; set; }

        public string remarks { get; set; }

    }
}
