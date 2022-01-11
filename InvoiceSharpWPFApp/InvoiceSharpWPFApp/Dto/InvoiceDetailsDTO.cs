using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Dto
{
    public class InvoiceDetailsDTO
    {
        public InvoiceDetailsDTO()
        {
            invoice = new InvoiceDTO();
            contractor = new ContractorDTO();
            items = new ObservableCollection<ItemDTO>();
        }

        public InvoiceDTO invoice { get; set; }
        public ContractorDTO contractor { get; set; }
        public ObservableCollection<ItemDTO> items { get; set; }
    }
}
