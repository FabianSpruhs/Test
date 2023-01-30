<template>
<div>
    <v-autocomplete :rules="[required]" v-model="name" :items="employeesView" :label="props.lableText"></v-autocomplete>
</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "ContactEmployeeAutoComplete",
})
</script>

<script setup lang="ts">
import {defineEmits, defineProps, onBeforeMount, Ref, ref, watch} from "vue";
import {HTTP} from "@/services/httpCommon";
import {AutoCompleteResponse} from "@/interfaces/AutoCompleteResponse";
import {Employee} from "@/interfaces/Employee";

const name = ref('')
const employeesView: Ref<string[]> = ref([])
const employees: Ref<Employee[]> = ref([])

const props = defineProps<{
  lableText: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', employeeResponse:AutoCompleteResponse): void
}>()

onBeforeMount(() => {
  HTTP.get("/api/employee/accounting/all")
      .then(res => { employees.value = res.data.content });
})

watch(employees, () => {
  employeesView.value = []
  employees.value.forEach(employee => employeesView.value.push(`${employee.firstName} ${employee.lastName}`))
})

watch(name, () => {
  emit('update:modelValue', createEmployeeResponse())
})

function required (v:string) {
  return !!v || 'Feld wird benötigt'
}

function createEmployeeResponse ():AutoCompleteResponse {
  let employeeID = 0
  employees.value.forEach((employee) => {
    if (`${employee.firstName} ${employee.lastName}` === name.value) {
      employeeID = employee.id;
    }
  });
  return {id: employeeID, name: name.value}
}
</script>

