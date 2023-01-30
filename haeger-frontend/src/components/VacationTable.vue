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
        </tr>
        </thead>
        <tbody>
        <tr
            class="text-left"
            v-for="(myVacationRequest, index) in props.myVacationRequests"
            :key="index"
        >
          <td>{{ index + 1 }}</td>
          <td>{{ myVacationRequest.beginDate }}</td>
          <td>{{ myVacationRequest.endDate }}</td>
          <td>{{ myVacationRequest.vacationDays }}</td>
          <td>{{ myVacationRequest.status }}</td>
        </tr>
        </tbody>
      </v-table>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "VacationTable",
})
</script>

<script setup lang="ts">

import {defineProps} from "vue";
import {VacationRequest} from "@/interfaces/VacationRequest";



const props = defineProps<{
  myVacationRequests: VacationRequest[]
}>()

const tableHeads = ['Urlaub von', 'Urlaub bis', 'Urlaubstage', 'Status']
</script>
