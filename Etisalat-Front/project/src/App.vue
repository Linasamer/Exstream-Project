<template>
  <div class="min-h-screen bg-gray-100">
    <div class="container mx-auto grid grid-cols-12">
      <div class="col-span-1"></div>
      <div class="col-span-10 bg-white min-h-screen shadow-lg">
        <!-- Header -->
        <header class="bg-white border-b">
          <div class="px-6 py-3 flex justify-between items-center">
            <div class="flex-1"></div>
            <h1 class="text-xl font-bold text-center flex-1">موبايل انترنت</h1>
            <div class="flex-1 flex justify-end">
              <img src="./assets/logo.png" alt="Etisalat" class="h-16" />
            </div>
          </div>
        </header>

        <!-- Navigation -->
        <nav class="bg-white border-b">
          <div class="flex justify-between">
            <a href="#" class="nav-link">
              <span class="nav-icon">🏷️</span>
              العروض
            </a>
            <a href="#" class="nav-link">
              <span class="nav-icon">👥</span>
              خدمة العملاء
            </a>
            <a href="#" class="nav-link active">
              <span class="nav-icon">🏠</span>
              الرئيسية
            </a>
          </div>
        </nav>

        <!-- Phone Number -->
        <div class="px-6 py-2 text-left">
          <span class="text-gray-700">{{ phoneNumber }}</span>
        </div>

        <!-- Main Content -->
        <main class="px-6 py-4">
          <!-- Offer Banner -->
          <div class="bg-white rounded-lg shadow p-4 mb-4">
            <div class="flex justify-between items-center">
              <div class="text-right">
                <p class="text-lg">عرض ال 4G LTE باقات الإنترنت</p>
              </div>
            </div>
          </div>

          <!-- Balance Section -->
          <div class="bg-white rounded-lg shadow mb-4">
            <div class="flex justify-between items-center p-4 border-b">
              <span class="text-etisalat-red">دعاية ثابتة</span>
              <h2 class="text-lg">خدمة نفاد الرصيد</h2>
            </div>

            <div class="p-4">
              <div class="bg-gray-50 p-4 rounded mb-4">
                <div class="flex justify-between">
                  <span>{{ currentBalance }} جنية</span>
                  <span>الرصيد الحالي</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Usage Statistics -->
          <div class="bg-white rounded-lg shadow mb-4">
            <div class="p-4 border-b">
              <div class="flex justify-between mb-1">
                <span>دعاية ثابتة</span>
                <span>صالحة حتى: {{ expiryDate }}</span>
              </div>
              <p class="text-sm text-gray-600 text-left">متبقي {{ remainingDays }} يوم</p>
            </div>

            <div class="p-4">
              <!-- Mobile Data Usage -->
              <div class="mb-6">
                <div class="flex justify-between mb-2">
                  <span>{{ mobileData.total }}</span>
                  <span>موبايل انترنت</span>
                </div>
                <div class="progress-bar">
                  <div class="progress-fill" :style="{ width: mobileData.percentage + '%' }"></div>
                </div>
                <div class="bg-[#047857] text-white text-sm px-3 py-1 rounded inline-block">
                  المتبقي {{ mobileData.used }}
                </div>
              </div>

              <!-- Units Usage -->
              <div class="mb-6">
                <div class="flex justify-between mb-2">
                  <span>{{ units.total }}</span>
                  <span>وحدة</span>
                </div>
                <div class="progress-bar">
                  <div class="progress-fill" :style="{ width: units.percentage + '%' }"></div>
                </div>
                <div class="bg-[#047857] text-white text-sm px-3 py-1 rounded inline-block">
                  المتبقي {{ units.used }}
                </div>
              </div>
            </div>
          </div>

          <!-- Send Invoice Button -->
          <div class="bg-white rounded-lg shadow p-6">
            <div class="text-center mb-6 flex justify-center gap-4">
              <button @click="callApi" class="bg-etisalat-red hover:bg-red-700 text-white px-8 py-3 rounded-lg text-base font-medium shadow-md transition duration-300 ease-in-out transform hover:scale-105 hover:shadow-lg">ارسال الفاتورة</button>
              <button 
                v-if="pdfUrl" 
                @click="openPdfInNewWindow" 
                class="bg-blue-600 hover:bg-blue-700 text-white px-8 py-3 rounded-lg text-base font-medium shadow-md transition duration-300 ease-in-out transform hover:scale-105 hover:shadow-lg"
              >
                فتح الفاتورة
              </button>
            </div>
            
            <!-- <div v-if="pdfUrl" class="mt-6">
              <iframe :src="pdfUrl" width="100%" height="600px" class="rounded-lg shadow-lg"></iframe>
            </div>

            <div v-if="responseData" class="mt-6 p-4 bg-gray-50 rounded-lg">
              <h3 class="text-lg font-medium mb-2">Response:</h3>
              <pre class="whitespace-pre-wrap break-words text-sm">{{ responseData }}</pre>
            </div> -->
          </div>
        </main>
      </div>
      <div class="col-span-1"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';

const phoneNumber = '01101473905';
const currentBalance = 550.00;
const mobileData = {
  used: '8.5 جيجا',
  total: '10 جيجا',
  percentage: 85
};
const units = {
  used: '350',
  total: '1000',
  percentage: 35
};
const expiryDate = '21/02/2025';
const remainingDays = 7;

interface ResponseDataItem {
  mimeType: string;
  content: string;
}

// API call related refs
const responseData = ref<string | null>(null);
const pdfUrl = ref<string | null>(null);

const openPdfInNewWindow = () => {
  if (pdfUrl.value) {
    window.open(pdfUrl.value, '_blank');
  }
};

const callApi = async () => {
  const url = 'http://localhost:8082/api/input';
  const headers = {
    'Content-Type': 'application/xml',
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
    <NameOfCustomer>Customer</NameOfCustomer>
    <PhoneNumber>01101473905</PhoneNumber>
    <ResellerCoCustCode>5.65041.00.00.103588</ResellerCoCustCode>
    <ResellerCoID>CONTR005164631</ResellerCoID>
    <ResellerADSLUnlimitedLines>250.00</ResellerADSLUnlimitedLines>
    <ResellerADSLLimitedLines>300.00</ResellerADSLLimitedLines>
    <ResellerTotalCharge>550.00</ResellerTotalCharge>
    <ResellerPreviousBalance>1000.00</ResellerPreviousBalance>
    <ResellerTotalAmountDue>1550.00</ResellerTotalAmountDue>
  </ContractSummaryReseller>
</Invoice>`;

  try {
    const response = await axios.post(url, xmlData, { headers });
    responseData.value = response.data;

    if (response.data && response.data.data && response.data.data[0].content) {
      const base64Pdf = response.data.data
        .filter((item: ResponseDataItem) => item.mimeType === "application/pdf")
        .map((item: ResponseDataItem) => item.content);

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
      pdfUrl.value = URL.createObjectURL(blob);
    }
  } catch (error: any) {
    console.error(error);
    responseData.value = error?.message || 'An unexpected error occurred';
  }
};
</script>

<style>
.nav-link {
  @apply flex-1 text-center py-3 text-sm text-gray-600 border-r last:border-r-0;
}

.nav-link.active {
  @apply text-etisalat-red border-b-2 border-b-etisalat-red;
}

.nav-icon {
  @apply block text-lg mb-1;
}

.progress-bar {
  @apply w-full h-2.5 bg-gray-200 rounded-full overflow-hidden mb-2;
}

.progress-fill {
  @apply h-full bg-blue-600 rounded-full transition-all duration-300;
}
</style>