<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';


const walletBalance = ref('54.1 EGP');
const recentTransactions = ref([
  { type: 'New Bills', amount: '11 EGP', date: '01/02/2024', icon: '📄' },
  { type: 'Deposit transaction', amount: '75.00 EGP', date: '01/04/2024', icon: '💰' },
  { type: 'Orange Recharge', amount: '10.00 EGP', date: '01/05/2024', icon: '📱' },
  { type: 'Send Wallet', amount: '20.00 EGP', date: '01/11/2024', icon: '💳' },
  { type: 'New Bills', amount: '200 EGP', date: '11/11/2024', icon: '📄' },
  { type: 'Deposit transaction', amount: '1000.00 EGP', date: '01/12/2024', icon: '💰' },

]);
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
    'Type': 1,
  };

  const xmlData = `<?xml version="1.0" encoding="UTF-8" ?>
<Driver> 
  <Customer>
    <CustomerLanguage>EN</CustomerLanguage>
    <Role>Manager</Role>
    <CustomerName>Moaz Ahmed</CustomerName>
    <CustomerAddress>Cairo, Egypt</CustomerAddress>
    <CustomerPhone>01154985811</CustomerPhone>
    <CustomerID>ABC12345</CustomerID>
    <CustomerEmail>moaz.ahmed@example.com</CustomerEmail>
    <homeownershipt>Home Equity</homeownershipt>
    <Summary>
        <Month>January</Month>
        <OpeningBalance>3000.00</OpeningBalance>
        <TotalDeposits>1750.00</TotalDeposits>
        <TotalWithdrawals>1462.50</TotalWithdrawals>
        <ClosingBalance>3287.50</ClosingBalance>
    </Summary>
    <Transactions>
        <Transaction>
            <Date>2023-01-01</Date>
            <Invoice>INV001</Invoice>
            <PreviousBalance>3000.00</PreviousBalance>
            <Description>Initial Deposit</Description>
            <Type>Deposit</Type>
            <Amount>500.00</Amount>
            <TotalBalance>3500.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-05</Date>
            <Invoice>INV002</Invoice>
            <PreviousBalance>3500.00</PreviousBalance>
            <Description>Purchase of Products</Description>
            <Type>Withdrawal</Type>
            <Amount>200.00</Amount>
            <TotalBalance>3300.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-10</Date>
            <Invoice>INV003</Invoice>
            <PreviousBalance>3300.00</PreviousBalance>
            <Description>Payment from Client</Description>
            <Type>Deposit</Type>
            <Amount>300.00</Amount>
            <TotalBalance>3600.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-15</Date>
            <Invoice>INV004</Invoice>
            <PreviousBalance>3600.00</PreviousBalance>
            <Description>Service Charge</Description>
            <Type>Withdrawal</Type>
            <Amount>50.00</Amount>
            <TotalBalance>3550.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-20</Date>
            <Invoice>INV005</Invoice>
            <PreviousBalance>3550.00</PreviousBalance>
            <Description>Refund</Description>
            <Type>Deposit</Type>
            <Amount>100.00</Amount>
            <TotalBalance>3650.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-25</Date>
            <Invoice>INV006</Invoice>
            <PreviousBalance>3650.00</PreviousBalance>
            <Description>Additional Service Charge</Description>
            <Type>Withdrawal</Type>
            <Amount>150.00</Amount>
            <TotalBalance>3500.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-28</Date>
            <Invoice>INV007</Invoice>
            <PreviousBalance>3500.00</PreviousBalance>
            <Description>Client Payment</Description>
            <Type>Deposit</Type>
            <Amount>250.00</Amount>
            <TotalBalance>3750.00</TotalBalance>
        </Transaction>
        <Transaction>
            <Date>2023-01-31</Date>
            <Invoice>INV008</Invoice>
            <PreviousBalance>3750.00</PreviousBalance>
            <Description>Final Adjustment</Description>
            <Type>Withdrawal</Type>
            <Amount>462.50</Amount>
            <TotalBalance>3287.50</TotalBalance>
        </Transaction>
    </Transactions>
  </Customer>
</Driver>`;

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

<template>
    <!-- <div class="wallet-content"> -->
      <!-- Balance Section -->
      <div class="balance-section">
        <h2>Wallet Balance</h2>
        <div class="balance-amount">{{ walletBalance }}</div>
      </div>

      <!-- Quick Actions -->
      <div class="quick-actions">
        <div class="action-item">
          <div class="action-icon">💳</div>
          <span>Payny service</span>
        </div>
        <div class="action-item">
          <div class="action-icon">➡️</div>
          <span>Send Money</span>
        </div>
        <div class="action-item">
          <div class="action-icon">📱</div>
          <span>ATM/Cash/PIN</span>
        </div>
      </div>

      <!-- Recent Transactions -->
      <div class="transactions-section">
        <div class="section-header">
          <h3>Recent transactions</h3>
          <span class="view-all">View All</span>
        </div>
        <div class="transactions-list">
          <div
            v-for="(transaction, index) in recentTransactions"
            :key="index"
            class="transaction-item"
          >
            <div class="transaction-icon">{{ transaction.icon }}</div>
            <div class="transaction-details">
              <div class="transaction-type">{{ transaction.type }}</div>
              <div class="transaction-date">{{ transaction.date }}</div>
            </div>
            <div class="transaction-amount">{{ transaction.amount }}</div>
          </div>
        </div>
      </div>
       <!-- New Button for Monthly Statement -->
       <div class="bg-white rounded-lg shadow p-6">
            <div class="text-center mb-6 flex justify-center gap-4">
              <button @click="callApi" class="send-button">Send Monthly Statement</button>
              <button 
                v-if="pdfUrl" 
                @click="openPdfInNewWindow" 
                class="my-button"
              >
              Open Statement        
                  </button>
            </div>
    </div>
    <!-- </div> -->
</template>


<style scoped>
.wallet-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100vh;
  padding: 20px;
  background-color: #f5f5f5;
}

.wallet-content {
  width: 100%;
  max-width: 1200px;
  background: white;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.balance-section {
  background: linear-gradient(135deg, #8e1537 0%, #5c0c23 100%);
  color: white;
  padding: 30px;
  text-align: center;
}

.balance-section h2 {
  font-size: 1.5em;
  margin-bottom: 10px;
}

.balance-amount {
  font-size: 3em;
  font-weight: bold;
}

.quick-actions {
  display: flex;
  justify-content: space-around;
  padding: 20px;
  gap: 15px;
  background-color: #f9f9f9;
}

.action-item {
  flex: 1;
  max-width: 180px;
  background: white;
  padding: 15px;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.action-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.15);
}

.action-icon {
  font-size: 2em;
  margin-bottom: 10px;
}

.transactions-section {
  padding: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h3 {
  font-size: 1.5em;
}

.view-all {
  color: #8e1537;
  font-weight: bold;
  cursor: pointer;
}

.transactions-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.transaction-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 10px;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
}

.transaction-item:hover {
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.transaction-icon {
  width: 50px;
  height: 50px;
  background: #f5f5f5;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 1.5em;
}

.transaction-details {
  flex: 1;
}

.transaction-type {
  font-weight: bold;
  margin-bottom: 5px;
}

.transaction-date {
  font-size: 0.85em;
  color: #666;
}

.transaction-amount {
  font-weight: bold;
  color: #8e1537;
}

@media (max-width: 768px) {
  .quick-actions {
    flex-wrap: wrap;
    gap: 10px;
  }

  .action-item {
    flex: 1 1 calc(50% - 10px);
  }
}
.my-button {
  background-color: blue;
  color: white;
    padding: 15px 18px;
    border: none;
    border-radius: 22px;
    margin-left: 18%;
    font-size: larger;
    width: 272px;
    margin-top: 2%;
    cursor: pointer;
}
.send-button {
  background-color: #8e1537;
    color: white;
    padding: 15px 18px;
    border: none;
    border-radius: 22px;
    margin-left: 18%;
    font-size: larger;
    width: 272px;
    margin-top: 2%;
    cursor: pointer;
}

</style>
