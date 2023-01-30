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
        v-for="(sickNote, index) in props.sickNotes"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{ sickNote.employee.firstName }}</td>
      <td>{{ sickNote.employee.lastName }}</td>
      <td>{{ sickNote.beginDate }}</td>
      <td>{{ sickNote.endDate }}</td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllSickNoteTable",
})
</script>

<script setup lang="ts">

import {defineProps} from "vue";
import {SickNote} from "@/interfaces/SickNote";

const props = defineProps<{
  sickNotes: SickNote[]
}>()

const tableHeads = ['Vorname', 'Nachname', 'Krank von', 'Krank bis']
</script>
