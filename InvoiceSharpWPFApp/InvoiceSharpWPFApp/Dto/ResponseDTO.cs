using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Dto
{
    public class ResponseDTO
    {
        public ResponseDTO()
        {

        }

        public int statusCode { get; set; }
        public string content { get; set; }
    }
}
