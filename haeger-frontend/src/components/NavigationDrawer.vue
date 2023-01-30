<template>
      <v-navigation-drawer
          expand-on-hover
          rail
          rail-width="120"
      >
        <v-list>
          <v-list-item
              :to="`/my_data/${userStore.getEmployeeID}`"
              prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg"
              :title="userStore.getUserName"
              subtitle="fabian.spruhs.haeger@consulting.de"
          ></v-list-item>
        </v-list>
        <v-divider></v-divider>
        <v-list density="comfortable" nav>
          <v-list-item prepend-icon="mdi-home" to="/" title="Meine Daten"></v-list-item>
          <v-list-item v-if="isAccounting" prepend-icon="mdi-account-multiple" to="/accounting" title="Buchhaltung"></v-list-item>
          <v-list-item v-if="isAdmin" prepend-icon="mdi-wrench" to="/admin" title="Administrator"></v-list-item>
        </v-list>
      </v-navigation-drawer>
</template>

<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: "NavigationDrawer",
})
</script>

<script setup lang="ts">

import {useUserStore} from "@/store/UserStore";
import {computed, ComputedRef} from "vue";

const userStore = useUserStore()

const isAdmin: ComputedRef<boolean> = computed((): boolean => {
  return userStore.getRoles === 'ADMIN'
})

const isAccounting: ComputedRef<boolean> = computed((): boolean => {
  return userStore.getRoles === 'ADMIN' || userStore.getRoles === 'ACCOUNTING'
})

</script>