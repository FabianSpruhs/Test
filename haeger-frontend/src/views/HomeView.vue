<template>
  <MainMenu :items="loadItems"/>
</template>

<script lang="ts">
import MainMenu from "@/components/MainMenu.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: 'HomeView',
  components: { MainMenu },
})
</script>

<script setup lang="ts">
import {useUserStore} from "@/store/UserStore";
import {computed, ComputedRef} from "vue";

const userStore = useUserStore()

const loadItems: ComputedRef<{title: string, text: string, icon: string, path: string}[]> = computed(() => {
  if (userStore) {
    return [
      { title: "Meine Daten", text: "Zeige und bearbeite meine Daten.", icon: "mdi-account", path: `/my_data/${userStore.getEmployeeID}`},
      { title: "Stunden Buchungen" , text: "Buche Stunden und siehe Abrechnungen.", icon: "mdi-alarm", path: `/my_working_hours/${userStore.getEmployeeID}`},
      { title: "Urlaubs Übersicht", text: "Urlaub beantragen restliche Urlaubstage anzeigen.", icon: "mdi-island", path: `/my_vacation/${userStore.getEmployeeID}`},
      { title: "Krankmeldung einreichen", text: "Reiche eine Krankmeldung ein.", icon: "mdi-needle", path: "/my_sick_note" },
      { title: "Meine Projekte", text: "Zeige meine Projekte an.", icon: "mdi-briefcase-outline", path: `/my_projects/${userStore.getEmployeeID}`}]
  }
  return []
})

</script>