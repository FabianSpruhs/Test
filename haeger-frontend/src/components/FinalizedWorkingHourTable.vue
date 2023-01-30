<template>
  <v-table fixed-header
           height="500px">
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
        v-for="(workingHour, index) in props.workingHours"
        :key="index"

    >
      <td>{{ index + 1 }}</td>
      <td>{{ workingHour.employee.firstName }}</td>
      <td>{{ workingHour.employee.lastName }}</td>
      <td>{{ workingHour.workingDay }}</td>
      <td>{{ workingHour.workingHours }}</td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "FinalizedWorkingHourTable",
})
</script>

<script setup lang="ts">
import {WorkingHour} from "@/interfaces/WorkingHour";
import {defineProps} from "vue";

const props = defineProps<{
  workingHours: WorkingHour[]
}>()
const tableHeads = ['Vorname', 'Nachname', 'Arbeitstag', 'Arbeitsstunde']
</script>
