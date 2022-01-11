using InvoiceSharpWPFApp.Dto;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.Json;

namespace InvoiceSharpWPFApp.Service
{
    class InvoiceService
    {
        private readonly RestClient client;

        public InvoiceService()
        {
            client = new RestClient("http://localhost:8080");
        }

        // add new invoice
        public ResponseDTO addInvoice(InvoiceDetailsDTO invoiceDetailsDTO)
        {
            var request = new RestRequest("addInvoice", Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddJsonBody(invoiceDetailsDTO);

            IRestResponse restResponse = client.Execute(request);
            ResponseDTO response = new ResponseDTO();
            response.statusCode = (int)restResponse.StatusCode;
            response.content = restResponse.Content;

            return response;
        }

        // get all invoices
        public List<InvoiceDTO> getInvoices()
        {
            var request = new RestRequest("getInvoices", Method.GET);
            IRestResponse<List<InvoiceDTO>> response = client.Execute<List<InvoiceDTO>>(request);
            List<InvoiceDTO> invoices = response.Data;

            return invoices;
        }

        // remove invoice from db
        public ResponseDTO removeInvoice(string invoiceNumber)
        {
            var request = new RestRequest("removeInvoice/{invoiceNumber}", Method.DELETE);
            request.AddUrlSegment("invoiceNumber", invoiceNumber);

            IRestResponse response = client.Execute(request);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.statusCode = (int)response.StatusCode;
            responseDTO.content = response.Content;

            return responseDTO;
        }

        // get invoice details
        public InvoiceDetailsDTO getInvoiceDetails(string invoiceNumber)
        {
            var request = new RestRequest("/getInvoiceDetails/{invoiceNumber}", Method.GET);
            request.AddUrlSegment("invoiceNumber", invoiceNumber);

            IRestResponse restResponse = client.Execute(request);
            string resCon = restResponse.Content;
            
            // using other deserialzer couse restsharp dont handle such complex dto
            InvoiceDetailsDTO invoiceDetails = JsonSerializer.Deserialize<InvoiceDetailsDTO>(resCon);
            return invoiceDetails;
        }

        // get next invoice number
        public ResponseDTO getNextInvoiceNumber()
        {
            var request = new RestRequest("getNextInvoiceNumber", Method.GET);
            IRestResponse<string> response = client.Execute<string>(request);
            string nextNumber = response.Data;

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.statusCode = (int) response.StatusCode;
            responseDTO.content = response.Content;

            return responseDTO;
        }
        
    }
}
