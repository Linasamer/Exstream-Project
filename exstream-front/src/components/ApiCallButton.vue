<template>
  <div class="api-call-container">
    <button class="api-call-button" @click="callApi">Call API</button>
    <div v-if="pdfUrl" class="pdf-preview-container">
      <!-- Embed the PDF using iframe -->
      <iframe :src="pdfUrl" width="100%" height="600px"></iframe>
      <!-- Alternatively, you can use the embed tag to display the PDF -->
      <!-- <embed :src="pdfUrl" width="100%" height="600px" type="application/pdf" /> -->
    </div>
    <div v-if="responseData" class="response-container">
      <h3>Response:</h3>
      <pre class="response-data">{{ responseData }}</pre>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      responseData: null,
      pdfUrl: null,  
    };
  },
  methods: {
    async callApi() {
      const url = 'http://localhost:8081/api/input';
      const headers = {
        'X-Xsrf-Token': '12f2e33a-0dde-4bc0-8086-4c86cea2a24c',
        'Content-Type': 'application/xml',
        'Authorization': 'eyJraWQiOiI0NzIwZWVlOWY5OWViOTZhNjM2OGY2MDNiZWU2OWZjNmNlZWY1MTdjIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiI3MTBlZGE4MS1lMDE5LTQwM2YtYmI0Ny1hYzExOTQwMDFlZDgiLCJzY3AiOlsib3Rkczpncm91cHMiLCJvdGRzOnJvbGVzIl0sInJvbGUiOltdLCJncnAiOlsiZW1wb3dlcmFkbWluc0BleHN0cmVhbS5yb2xlIiwidGVuYW50YWRtaW5zQGV4c3RyZWFtLnJvbGUiXSwiZG1wIjp7Ik9URFNfQ1JFRFNfQVVUSCI6InRydWUiLCJPVERTX0hBU19QQVNTV09SRCI6ImZhbHNlIn0sInJ0aSI6IjFmZGRiZjkwLTg0YWItNDZkMC04NDkyLWIwOTc2NmFiMGUzMiIsInNhdCI6MTczMjE4OTI4NywiaXNzIjoiaHR0cHM6Ly90ZC1leHN0cmVhbWNuLmVpbWRlbW8uY29tOjQ0My9vdGRzd3MiLCJncnQiOiJhdXRob3JpemF0aW9uX2NvZGUiLCJzdWJfdHlwIjowLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJwaWQiOiJleHN0cmVhbS5yb2xlIiwicmlkIjp7fSwidGlkIjoiaW5ub3ZhdGUiLCJzaWQiOiJlOWUxODVhZi00ZDJmLTQzYmUtOTI1My1kODZlMTgwYWVlMTIiLCJ1aWQiOiJ0ZW5hbnRhZG1pbkBleHN0cmVhbS5yb2xlIiwidW5tIjoidGVuYW50YWRtaW4iLCJuYW1lIjoidGVuYW50YWRtaW4iLCJleHAiOjE3MzIxOTAxODcsImlhdCI6MTczMjE4OTI4NywianRpIjoiNjAzNTQyZWEtODVkMy00YzBlLTgyOWItYTU5Yzk1OWZiMTdiIiwiY2lkIjoiRXhzdHJlYW1TeXN0ZW0ifQ.G683f4H4VEYHjk0GOHOUGWaT8gpX1u38uaFdpkvPJ19ZmcNYNfcpYVNw4HOOWxdZpC5pdNqGflTr3U5EODFBWwzFCZwXt2SxJh8Cn6ZajVPFgV4P7Fu0TwOJAVjQU7wYeppVWmZ1_ljk0lyoCAIkIEP0I8ON9YQfQdKKzoNbINSx9P-wPdA7S_00upjnxTrcnECNu_U1If0ZUP_NPcBNkY14Kosk86SchHW91QQDyH7MRudye5e3u_wdnMBTJhoHZGSNn38lYkGycRNcx8DUAKMOiSh0-5DeG3kArFOYh2cuH_NZeLAmnqsStAFq3rsaKN3tLPaF78pYr_xFZBCmMg',
      };

      const xmlData = `<Invoice>
<Language>EN</Language>
<Address>
<Line1>Etisalat Customer</Line1>
<Line2>Cairo</Line2>
<Line3>Egypt</Line3>
</Address>
<InvoiceNumber>2011010048941848</InvoiceNumber>
<InvoiceStart>01/10/2024</InvoiceStart>
<InvoiceEnd>31/10/2024</InvoiceEnd>
<InvoiceDate>01/11/2024</InvoiceDate>
<PaymentDueDate>22/11/2024</PaymentDueDate>
 
  <ContractSummaryReseller>
<NameOfCustomer>testtttttttttttttttttttttt1111111111</NameOfCustomer>
<PhoneNumber>01154985811</PhoneNumber>
<email>moaz.nageb@asset.com.eg</email>
<ResellerCoCustCode>5.65041.00.00.103588</ResellerCoCustCode>
<ResellerCoID>CONTR005164631</ResellerCoID>
<ResellerADSLUnlimitedLines>250.00</ResellerADSLUnlimitedLines>
<ResellerADSLLimitedLines>300.00</ResellerADSLLimitedLines>
<ResellerTotalCharge>550.00</ResellerTotalCharge>
<ResellerPreviousBalance>1000.00</ResellerPreviousBalance>
<ResellerTotalAmountDue>1550.00</ResellerTotalAmountDue>
</ContractSummaryReseller>
 
  <ContractSummaryReseller>
<NameOfCustomer>Lina1111111111111111111111111111111111111111111</NameOfCustomer>
<PhoneNumber>01020444909</PhoneNumber>
<email>lina.samer@asset.com.eg</email>
<ResellerCoCustCode>5.65041.00.00.103590</ResellerCoCustCode>
<ResellerCoID>CONTR005164633</ResellerCoID>
<ResellerADSLUnlimitedLines>360.00</ResellerADSLUnlimitedLines>
<ResellerADSLLimitedLines>265.00</ResellerADSLLimitedLines>
<ResellerTotalCharge>625.00</ResellerTotalCharge>
<ResellerPreviousBalance>2000.00</ResellerPreviousBalance>
<ResellerTotalAmountDue>2625.00</ResellerTotalAmountDue>
</ContractSummaryReseller>
 
  <ContractSummaryReseller>
<NameOfCustomer>Moaz11111111111111111111111111111111111</NameOfCustomer>
<PhoneNumber>01097302351</PhoneNumber>
<email>mahmoud.ezzelarab@asset.com.eg</email>
<ResellerCoCustCode>5.65041.00.00.103592</ResellerCoCustCode>
<ResellerCoID>CONTR005164635</ResellerCoID>
<ResellerADSLUnlimitedLines>400.00</ResellerADSLUnlimitedLines>
<ResellerADSLLimitedLines>160.00</ResellerADSLLimitedLines>
<ResellerTotalCharge>560.00</ResellerTotalCharge>
<ResellerPreviousBalance>3000.00</ResellerPreviousBalance>
<ResellerTotalAmountDue>3560.00</ResellerTotalAmountDue>
</ContractSummaryReseller>
</Invoice>`;  // Your XML data as before

      try {
        const response = await axios.post(url, xmlData, { headers });
        this.responseData = response.data;

        // Check if the response contains a base64-encoded PDF string
        if (response.data && response.data.data && response.data.data[0].content) {
          //const base64Pdf = response.data.data[0].content;
          const base64Pdf = response.data.data
    .filter(item => item.mimeType === "application/pdf")
    .map(item => item.content);

          // Convert base64 string to Blob
          const byteCharacters = atob(base64Pdf);
          const byteArrays = [];
          for (let offset = 0; offset < byteCharacters.length; offset += 1024) {
            const slice = byteCharacters.slice(offset, offset + 1024);
            const byteNumbers = new Array(slice.length);
            for (let i = 0; i < slice.length; i++) {
              byteNumbers[i] = slice.charCodeAt(i);
            }
            byteArrays.push(new Uint8Array(byteNumbers));
          }

          const blob = new Blob(byteArrays, { type: "application/pdf" });
          const pdfUrl = URL.createObjectURL(blob);

          // Set the PDF URL to be used in iframe or embed
          this.pdfUrl = pdfUrl;
        }
      } catch (error) {
        console.error(error);
        this.responseData = error.message;
      }
    },
  },
};
</script>

<style scoped>
.api-call-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.api-call-button {
  padding: 12px 20px;
  font-size: 16px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.api-call-button:hover {
  background-color: #367a52;
}

.pdf-preview-container {
  margin-top: 20px;
  width: 100%;
  max-width: 800px;
  height: 600px;
}

.response-container {
  margin-top: 20px;
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  border: 1px solid #ccc;
  width: 80%;
  max-width: 800px;
  overflow-x: auto;
}

.response-data {
  font-family: monospace;
  white-space: pre-wrap;
  word-wrap: break-word;
  background-color: #eef2f7;
  padding: 10px;
  border-radius: 4px;
  font-size: 14px;
  margin-top: 10px;
  color: #333;
}
</style>
