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
      <th v-if="props.booked" scope="col">
        Bearbeiten
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
        class="text-left"
        v-for="(workingHour, index) in props.myWorkingHours"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{workingHour.workingDay}}</td>
      <td>{{workingHour.workingHours}}</td>
      <td>{{workingHour.workingHourStatus}}</td>
      <td v-if="booked">
        <v-btn icon="mdi-pencil-outline" @click="openEditDialog(workingHour.id, workingHour.workingDay, workingHour.workingHours)"></v-btn>
      </td>
    </tr>
    </tbody>
    <v-dialog v-model="editDialog" max-width="500" persistent>
      <BookedWorkingHourDialog title="Arbeitsstunde bearbeiten" :hoursToEdit="hoursToEdit" :dateToEdit="dateToEdit">
        <template #cancelButton>
          <v-btn @click="editDialog = false" color="primary">Abbrechen</v-btn>
        </template>
        <template #confirmButton="confirmProps">
          <v-btn @click="editWorkingHour(confirmProps.date, confirmProps.hours)" color="primary">Ändern</v-btn>
        </template>
      </BookedWorkingHourDialog>
    </v-dialog>
  </v-table>
</template>

<script lang="ts">
import BookedWorkingHourDialog from "@/components/BookedWorkingHourDialog.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "WorkingHourTable",
  components: {BookedWorkingHourDialog},
})
</script>

<script setup lang="ts">

import {defineProps, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {WorkingHour} from "@/interfaces/WorkingHour";

const props = defineProps<{
  myWorkingHours: WorkingHour[],
  booked?: boolean
}>()

const tableHeads = ['Arbeitstag', 'Arbeitsstunde', 'Status']
const editDialog = ref(false)
const idToEdit = ref(0)
const dateToEdit = ref('')
const hoursToEdit = ref(0)
const errorMessage = ref('')

function openEditDialog (id:number, date:string, hours:number) {
  editDialog.value = true
  idToEdit.value = id
  dateToEdit.value = date
  hoursToEdit.value = hours
}

function editWorkingHour (date:string, hours:number): void {
  HTTP.put(`/api/working_hour/edit/${idToEdit.value}`, {
    workingDay: date,
    workingHours: hours
  }).then((response) => {
    editDialog.value = false
    const workingHour = props.myWorkingHours.find(workingHour => workingHour.id === response.data.id)
    if (workingHour) {
      workingHour.workingDay = response.data.workingDay
      workingHour.workingHours = response.data.workingHours
    }
  }).catch(error => {
    errorMessage.value = error.data
  })
}
</script>
