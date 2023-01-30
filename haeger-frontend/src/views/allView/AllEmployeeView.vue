<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Alle Mitarbeiter">
      <v-card-text>
        <EmployeeTable :employees="employees"/>
      </v-card-text>
      <div class="text-center">
        <v-pagination class="text-center"
                      v-model="page"
                      :length="totalPages"
                      :total-visible="7"
                      @click="getPage()"
        ></v-pagination>
      </div>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">
import EmployeeTable from "@/components/EmployeeTable.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllEmployeeView",
  components: {EmployeeTable},
})
</script>

<script setup lang="ts">

import {onBeforeMount, Ref, ref} from "vue";
import {Employee} from "@/interfaces/Employee";
import {HTTP} from "@/services/httpCommon";

const employees: Ref<Employee[]> = ref([])
const page = ref(1)
const totalPages = ref(1)

function getPage (): void {
  HTTP.get(`/api/employee/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        employees.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

onBeforeMount(() => {
  HTTP.get("/api/employee/accounting/all?page=0&size=10")
      .then(res => {
        employees.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})

</script>