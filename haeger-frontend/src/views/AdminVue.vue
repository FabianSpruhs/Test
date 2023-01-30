<template>
  <MainMenu :items="items"/>
</template>

<script lang="ts">
import MainMenu from "@/components/MainMenu.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: 'AdminView',
  components: { MainMenu },
})
</script>

<script setup lang="ts">
const items = [
  { title: "Mitarbeiter einstellen", text: "Stellt einen neue Mitarbeiter ein.", icon: "mdi-account-plus-outline", path: "/new_employee" },
  { title: "Mitarbeiter administration", text: "Ändere Status und Rolle von Mitarbeitern und entlasse Mitarbeiter.", icon: "mdi-badge-account-horizontal-outline", path: "/admin_employee" },
]
</script>