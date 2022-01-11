using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using InvoiceSharpWPFApp.Dto;
using RestSharp;

namespace InvoiceSharpWPFApp.Service
{
    class ContractorService
    {
        private readonly RestClient client;

        public ContractorService()
        {
            client = new RestClient("http://localhost:8080");
        }

        // get Contractor by name from api
        public ContractorDTO GetContractorByName(string name)
        {
            var request = new RestRequest("getContractor/{name}", Method.GET);
            request.AddUrlSegment("name", name);

            IRestResponse<ContractorDTO> response = client.Execute<ContractorDTO>(request);
            ContractorDTO contractorDTO = response.Data;
           
            return contractorDTO;
        }

        // add new Contractor
        public ResponseDTO AddContractor(ContractorDTO contractorDTO)
        {
            var request = new RestRequest("addContractor", Method.POST);
            request.RequestFormat = DataFormat.Json;
            request.AddJsonBody(contractorDTO);
            // request.OnBeforeDeserialization = resp => { resp.ContentType = "application/json"; };

            IRestResponse response = client.Execute(request);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.statusCode = (int)response.StatusCode;
            responseDTO.content = response.Content;

            return responseDTO;
        }

        // get Contractor list
        public List<ContractorDTO> getContractors()
        {
            var request = new RestRequest("getContractors", Method.GET);
            IRestResponse<List<ContractorDTO>> response = client.Execute<List<ContractorDTO>>(request);
            List<ContractorDTO> contractors = response.Data;

            return contractors;
        }

        // remove contractor
        public ResponseDTO removeContractor(string name)
        {
            var request = new RestRequest("removeContractor/{name}", Method.DELETE);
            request.AddUrlSegment("name", name);

            IRestResponse response = client.Execute(request);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.statusCode = (int)response.StatusCode;
            responseDTO.content = response.Content;

            return responseDTO;
        }
    }
}
