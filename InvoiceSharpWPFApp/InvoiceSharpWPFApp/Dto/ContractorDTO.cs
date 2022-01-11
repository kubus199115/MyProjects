using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Dto
{
    public class ContractorDTO
    {
        public ContractorDTO()
        {

        }

        public string name { get; set; }
        public string address { get; set; }
        public string taxId { get; set; }
        public string regon { get; set; }
        public string phone { get; set; }
        public string email { get; set; }

        public override string ToString()
        {
            return name;
        }
    }
}
