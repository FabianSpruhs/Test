<template>
  <v-card :title="props.title">
    <v-card-text>
      <SingleDateInputField :singleLable="'Arbeitstag'" @singleDateInput="singleDateInput" :dateToEdit="props.dateToEdit"/>
      <v-text-field type="number" v-model="hours" :rules="[required, maxHours]" label="Arbeitsstunde">
      </v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <slot name="cancelButton" ></slot>
      <slot name="confirmButton" :date="date" :hours="hours"></slot>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts">
import SingleDateInputField from "@/components/SingleDateInputField.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "BookedWorkingHourDialog",
  components: {SingleDateInputField},
})
</script>

<script setup lang="ts">

import {withDefaults, defineProps, toRef} from "vue";


const props = withDefaults(defineProps<{
  title: string,
  dateToEdit: string,
  hoursToEdit: number,
}>(), {
  title: '',
  dateToEdit: '',
  hoursToEdit: 0,
})

const date = toRef(props, 'dateToEdit')
const hours = toRef(props, 'hoursToEdit')


function singleDateInput (dateInput:string): void {
  date.value = dateInput
}

function required (v:number) {
  return !!v || 'Feld wird benötigt'
}

function maxHours (v:number) {
  return v < 16 || 'Es darf nicht mehr als 16 Stunden am Tag gearbeitet werden!'
}
</script>
