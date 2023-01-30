<template>
  <div style="height: 100px"></div>
  <v-container>
    <v-row justify="center">
      <v-col
          v-for="(item, n) in props.items"
          :key="n"
          cols="auto"
      >
        <v-card height="150"
                width="400"
                :title="item.title"
                :text="item.text"
                :prepend-icon="item.icon"
                :key="item.title"
                :to="item.path"
        >
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "MainMenu",
})
</script>

<script setup lang="ts">
import {defineProps} from "vue";
import {MenuItems} from "@/interfaces/MenuItems";

const props = defineProps<{
  items: MenuItems[]
}>()
</script>