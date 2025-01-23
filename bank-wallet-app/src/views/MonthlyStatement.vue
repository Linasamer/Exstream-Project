<template>
  <div class="monthly-statement">
    <header class="header">
      <h1>Monthly Statement</h1>
    </header>

    <div class="wallet-balance">
      <div class="balance-amount">34.21 EGP</div>
      <button class="load-button" @click="sendStatement">SEND STATEMENT</button>
    </div>

    <div class="transactions-list">
      <h2>Recent transactions</h2>
      <div class="transaction" v-for="transaction in transactions" :key="transaction.id">
        <div class="transaction-icon">
          <span class="material-icons">{{ transaction.icon }}</span>
        </div>
        <div class="transaction-details">
          <div class="transaction-name">{{ transaction.name }}</div>
          <div class="transaction-id">{{ transaction.id }}</div>
        </div>
        <div class="transaction-amount">
          {{ transaction.amount }} EGP
          <div class="transaction-date">{{ transaction.date }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const transactions = ref([
  {
    id: '012',
    name: 'Orange Recharge',
    amount: '10.00',
    date: '04/01/2023',
    icon: 'sync'
  },
  {
    id: '012',
    name: 'Load Wallet',
    amount: '50.00',
    date: '04/01/2023',
    icon: 'account_balance_wallet'
  }
])

const sendStatement = () => {
  // Add functionality to send statement
  console.log('Sending monthly statement...')
}
</script>

<style scoped>
.monthly-statement {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.header {
  background-color: #8B1F41;
  color: white;
  padding: 15px;
  text-align: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}

.header h1 {
  margin: 0;
  font-size: 1.2rem;
}

.wallet-balance {
  background: white;
  padding: 20px;
  margin-top: 60px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  text-align: center;
}

.balance-amount {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 15px;
}

.load-button {
  background-color: #8B1F41;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
}

.transactions-list {
  margin-top: 20px;
}

.transactions-list h2 {
  font-size: 1.1rem;
  color: #333;
  margin-bottom: 15px;
}

.transaction {
  display: flex;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #eee;
  background: white;
}

.transaction-icon {
  margin-right: 15px;
}

.transaction-details {
  flex: 1;
}

.transaction-name {
  font-weight: bold;
}

.transaction-id {
  color: #666;
  font-size: 0.9rem;
}

.transaction-amount {
  text-align: right;
  font-weight: bold;
}

.transaction-date {
  color: #666;
  font-size: 0.8rem;
  margin-top: 4px;
}
</style>
