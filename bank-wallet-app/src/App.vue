<script setup lang="ts">
import WalletView from './views/WalletView.vue'
</script>

<template>
  <div class="app-container">
    <div class="content-wrapper">
      <WalletView />
    </div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  overflow: hidden;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  background-color: #f5f5f5;
  color: #333;
}

.app-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.content-wrapper {
  width: 100%;
  max-width: 420px;
  height: 100vh;
  max-height: 100vh;
  background: white;
  overflow: hidden;
}

@media (min-width: 421px) {
  .content-wrapper {
    height: 100vh;
    border-radius: 0;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  }
}
</style>
