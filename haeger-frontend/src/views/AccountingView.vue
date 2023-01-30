<template>
  <MainMenu :items="items"/>
</template>

<script lang="ts">
import MainMenu from "@/components/MainMenu.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: 'AccountingView',
  components: { MainMenu },
})
</script>

<script setup lang="ts">
const items = [
  { title: "Mitarbeiter", text: "Zeige und bearbeite alle Mitarbeiter.", icon: "mdi-account-group", path: "all_employee" },
  { title: "Projekte" , text: "Zeige und bearbeite alle Projekte.", icon: "mdi-alarm", path: "/all_projects" },
  { title: "Urlaubsanträge", text: "Zeige und bearbeite alle Urlaubsanträge.", icon: "mdi-island", path: "/all_vacation_requests" },
  { title: "Krankmeldungen", text: "Zeige alle Krankmeldungen an.", icon: "mdi-needle", path: "/all_sick_notes" },
  { title: "Kunden", text: "Zeige und bearbeite alle Kunden.", icon: "mdi-account-tie", path: "/all_clients" },
  { title: "Gebuchte Arbeitsstunden", text: "Zeige alle finalisierten Arbeitsstunden an.", icon: "mdi-briefcase-outline", path: "/all_working_hours" }
]
</script>