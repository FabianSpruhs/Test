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
      <th class="text-left" scope="col">
        Details
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
        class="text-left"
        v-for="(client, index) in props.clients"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{ client.name }}</td>
      <td>{{ client.contactEmployee.firstName }} {{ client.contactEmployee.lastName }}</td>
      <td><router-link :to="{name: 'client', params: {id: client.id}}" ><v-icon>mdi-clipboard-outline</v-icon></router-link></td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({
  name: "ClientTable",
})
</script>

<script setup lang="ts">

import {defineProps} from "vue";
import {Client} from "@/interfaces/Client";

const props = defineProps<{
  clients: Client[]
}>()

const tableHeads = ['Kundenname', 'Zuständiger Mitarbeiter']
</script>
