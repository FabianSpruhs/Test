<template>
  <v-text-field
      @input="handleDateInput()"
      :rules="[required, maxYear, minYear]"
      v-model="beginDate"
      :label=props.firstLable
      type="date"></v-text-field>
  <v-text-field
      @input="handleDateInput()"
      :rules="[required, maxYear, minYear]"
      v-model="endDate"
      :label=props.secondLable
      type="date"></v-text-field>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "DoubleDateInputField",
})
</script>

<script setup lang="ts">

import {defineEmits, defineProps, ref} from "vue";

const props = defineProps<{
  firstLable: string,
  secondLable: string
}>()

const emit = defineEmits<{
  (e: 'doubleDateInput', {beginDate, endDate} : {beginDate: string, endDate: string}): void
}>()

const beginDate = ref('')
const endDate = ref('')

function handleDateInput (): void {
  emit("doubleDateInput", {beginDate: beginDate.value, endDate: endDate.value})
}

function required (v:string) {
  return !!v || 'Feld wird benötigt'
}

function minYear (v:string) {
  return parseInt(v.slice(0,4)) > 2000 || 'Ungültiges Jahr'
}

function maxYear (v:string) {
  return parseInt(v.slice(0,4)) < 3000 || 'Ungültiges Jahr'
}

</script>
