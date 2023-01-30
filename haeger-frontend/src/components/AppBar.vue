<template>
  <v-app-bar dense>
    <v-app-bar-title class="text-h4">Haeger Management</v-app-bar-title>
    <v-btn icon @click="logOut">
    <v-icon>mdi-logout</v-icon>
  </v-btn></v-app-bar>
</template>

<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: "AppBar",
})
</script>

<script setup lang="ts">
import {useUserStore} from "@/store/UserStore";
import router from "@/router";

const userStore = useUserStore()

function logOut(): void {
  userStore.logOut()
  userStore.setToken('')
  userStore.setUserName('')
  userStore.setRoles('')
  userStore.setEmployeeID(0)
  router.push({path: '/login'})
}
</script>