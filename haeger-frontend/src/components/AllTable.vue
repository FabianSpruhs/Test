<template>
  <v-sheet class="pa-12" rounded>
  <v-card class="mx-auto" max-width="1200" :title="props.titel">
    <v-table fixed-header
             height="500px">
      <thead>
      <tr>
        <th class="text-left" scope="col">
          #
        </th>
        <th v-for="tableHead in props.tableHeads" class="text-left"  :key="tableHead" scope="col">
          {{ tableHead }}
        </th>
      </tr>
      </thead>
      <tbody>
      <tr
          class="text-left"
          v-for="(content, index) in props.allContent"
          :key="index"

      >
        <td>{{ index + 1 }}</td>
        <td v-for="data in content" :key="data">{{ data }}</td>
      </tr>
      </tbody>

    </v-table>
  </v-card>
  </v-sheet>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllTable",
})
</script>

<script setup lang="ts">

import {defineProps} from "vue";

const props = defineProps<{
  allContent: string[],
  titel: string,
  tableHeads: string[]
}>()

</script>
