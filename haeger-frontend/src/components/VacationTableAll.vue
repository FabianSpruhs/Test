<template>
  <v-table fixed-header>
    <thead>
    <tr>
      <th class="text-left" scope="col">
        #
      </th>
      <th v-for="tableHead in tableHeads" class="text-left"  :key="tableHead" scope="col">
        {{ tableHead }}
      </th>
      <th scope="col">Genehmigen</th>
    </tr>
    </thead>
    <tbody>
    <tr
        class="text-left"
        v-for="(vacationRequest, index) in props.vacationRequests"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{ vacationRequest.employee.firstName }} {{ vacationRequest.employee.lastName }}</td>
      <td>{{ vacationRequest.beginDate }}</td>
      <td>{{ vacationRequest.endDate }}</td>
      <td>{{ vacationRequest.vacationDays }}</td>
      <td>{{ vacationRequest.status }}</td>
      <td v-if="vacationRequest.status === 'OPEN'">
        <v-btn icon="mdi-check" color="success" @click="emit('approveVacation', vacationRequest.id)"></v-btn>
        <v-btn color="error" icon="mdi-close" @click="emit('declineVacation', vacationRequest.id)"></v-btn>
      </td>
      <td v-else></td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "VacationTableAll",
  emits: ['approveVacation', 'declineVacation'],

})
</script>

<script setup lang="ts">

import {defineEmits, defineProps} from "vue";
import {VacationRequest} from "@/interfaces/VacationRequest";


const emit = defineEmits<{
  (e: 'approveVacation', id:number): void
  (e: 'declineVacation', id:number): void
}>()

const props = defineProps<{
  myVacationRequests: VacationRequest[]
}>()

const tableHeads = ['Mitarbeiter', 'Urlaub von', 'Urlaub bis', 'Urlaubstage', 'Status']

</script>
