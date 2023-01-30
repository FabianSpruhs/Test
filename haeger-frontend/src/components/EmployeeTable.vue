<template>
  <v-table fixed-header
           height="500px">
    <thead>
    <tr>
      <th class="text-left" scope="col">
        #
      </th>
      <th v-for="tableHead in tableHeads" class="text-left" :key="tableHead" scope="col">
        {{ tableHead }}
      </th>
      <th class="text-left" scope="col">
        Details
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
        class="text-left"
        v-for="(employee, index) in props.employees"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{ employee.firstName }}</td>
      <td>{{ employee.lastName }}</td>
      <td>{{ employee.mail }}</td>
      <td>{{ employee.status }}</td>
      <td>{{ employee.role }}</td>
      <td><router-link :to="{name: 'myData', params: {id: employee.id}}" ><v-icon>mdi-clipboard-outline</v-icon></router-link></td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "EmployeeTable",
})
</script>

<script setup lang="ts">
import {defineProps} from "vue";
import {Employee} from "@/interfaces/Employee";

const tableHeads = ['Vorname', 'Nachname', 'Mail', 'Status', 'Rolle']

const props = defineProps<{
  employees: Employee[]
}>()

</script>
