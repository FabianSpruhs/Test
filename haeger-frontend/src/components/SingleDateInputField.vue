<template>
  <v-text-field
      @input="handleDateInput()"
      :rules="[required, maxYear, minYear]"
      v-model="singleDate"
      :label=props.singleLable
      type="date"></v-text-field>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "SingleDateInputField",
})
</script>

<script setup lang="ts">

import {defineEmits, defineProps, ref} from "vue";

const emit = defineEmits<{
  (e: 'singleDateInput', singleDate:string ): void
}>()

const props = defineProps<{
  singleLable: string,
  dateToEdit: string
}>()

const singleDate = ref(props.dateToEdit)
function handleDateInput (): void {
  emit("singleDateInput", singleDate.value)
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