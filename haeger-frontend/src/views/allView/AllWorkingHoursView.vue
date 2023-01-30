<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Finalisierte Arbeitsstunden">
      <v-card-text>
        <FinalizedWorkingHourTable :workingHours="workingHours"/>
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
  <AllTable :allContent="workingHoursAccountings" :tableHeads="tableHeadAccount" :titel="'Arbeitsstunden Abrechnung'"/>
</template>

<script lang="ts">
import AllTable from "@/components/AllTable.vue";
import FinalizedWorkingHourTable from "@/components/FinalizedWorkingHourTable.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllWorkingHoursView",
  components: {FinalizedWorkingHourTable, AllTable },
})
</script>

<script setup lang="ts">
import {onBeforeMount, Ref, ref, watch} from "vue";
import {HTTP} from "@/services/httpCommon";
import {WorkingHourAccounting} from "@/interfaces/WorkingHourAccounting";
import {WorkingHour} from "@/interfaces/WorkingHour";

const workingHours: Ref<WorkingHour[]> = ref([])
const tableHeadAccount = ['Vorname', 'Nachname', 'Restliche Arbeitsstunden']
const workingHoursAccount: Ref<WorkingHourAccounting[]> = ref([])
const page = ref(1)
const totalPages = ref(1)
const workingHoursAccountings: Ref<string[][]> = ref([])

watch(workingHoursAccount, () => {
  workingHoursAccount.value.forEach((workingHour) => {
    let workingHourAccountingStringList: string[] = []
    workingHourAccountingStringList.push(workingHour.employeeJPA.firstName.toString())
    workingHourAccountingStringList.push(workingHour.employeeJPA.lastName.toString())
    workingHourAccountingStringList.push(workingHour.workingHour.toString())
    workingHoursAccountings.value.push(workingHourAccountingStringList)
  })
})

function getPage(): void {
  HTTP.get(`/api/working_hour/accounting/finalize/all?page=${page.value - 1}&size=10`)
      .then(res => {
        workingHours.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

onBeforeMount(() => {
  HTTP.get(`/api/working_hour/accounting/finalize/all?page=0&size=10`)
      .then(res => {
        workingHours.value = res.data.content
        totalPages.value = res.data.totalPages
      });
  HTTP.get("/api/working_hour/accounting/account/all")
      .then(res => { workingHoursAccount.value = res.data});
})
</script>
