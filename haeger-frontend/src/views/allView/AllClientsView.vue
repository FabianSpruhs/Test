<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" >
      <v-card-title class="text-h5">
        Kundenübersicht
      </v-card-title>
      <ClientTable :clients="clients"/>
      <v-card-actions>
        <v-container>
          <v-row>
            <v-col md="2" align-self="center">
        <v-dialog
            v-model="newClientDialog"
            persistent
            max-width="800"
        >
          <ClientDialog @closeDialog="closeDialog"/>
        </v-dialog>
        <v-btn color="primary" @click="this.newClientDialog = true" class="align-center">
          Neuer Kunde
        </v-btn>
            </v-col>
            <v-col md="8">
        <div class="text-center">
          <v-pagination class="text-center"
                        v-model="page"
                        :length="totalPages"
                        :total-visible="7"
                        @click="getPage()"
          ></v-pagination>
        </div>
            </v-col>
          </v-row>
        </v-container>
      </v-card-actions>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">

import ClientTable from "@/components/ClientTable.vue";
import ClientDialog from "@/components/ClientDialog.vue";
import {defineComponent} from "vue";
export default defineComponent({
  name: "AllClientsView",
  components: {ClientDialog, ClientTable},
})
</script>

<script setup lang="ts">

import {onBeforeMount, Ref, ref} from "vue";
import {Client} from "@/interfaces/Client";
import {HTTP} from "@/services/httpCommon";

const newClientDialog = ref(false)
const clients: Ref<Client[]>= ref([])
const totalPages = ref(1)
const page = ref(1)

function closeDialog (): void {
  newClientDialog.value = false
}

function getPage(): void {
  HTTP.get(`/api/client/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        clients.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

onBeforeMount(() => {
  HTTP.get("/api/client/accounting/all?page=0&size=10")
      .then(res => {
        clients.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})
</script>
