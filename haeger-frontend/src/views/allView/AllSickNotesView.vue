<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Krankmeldungen">
      <v-card-text>
        <AllSickNoteTable :sickNotes="sickNotes"/>
      </v-card-text>

      <div class="text-center">
        <v-pagination class="text-center"
                      v-model="page"
                      :length="totalPages"
                      :total-visible="7"
                      @click="getPage()"
        ></v-pagination>
      </div>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">

import AllSickNoteTable from "@/components/AllSickNoteTable.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllSickNotesView",
  components: {AllSickNoteTable},

})
</script>

<script setup lang="ts">

import {onBeforeMount, Ref, ref} from "vue";
import {SickNote} from "@/interfaces/SickNote";
import {HTTP} from "@/services/httpCommon";

const sickNotes: Ref<SickNote[]> = ref([])
const page = ref(1)
const totalPages = ref(1)

function getPage(): void {
  HTTP.get(`/api/sick_note/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        sickNotes.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

onBeforeMount(() => {
  HTTP.get(`/api/sick_note/accounting/all?page=0&size=10`)
      .then(res => {
        sickNotes.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})

</script>
