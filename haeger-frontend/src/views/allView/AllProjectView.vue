<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Alle Projekte">
      <ProjectTable :projects="projects"/>
      <v-card-actions>
        <v-container>
          <v-row>
            <v-col md="2" align-self="center">
        <v-dialog
            v-model="newProjectDialog"
            max-width="500"
            persistent
        >
          <v-card>
            <v-card-title>
              <span class="text-h5">Neues Projekt erstellen</span>
            </v-card-title>
            <v-card-text>
              <v-text-field
                  :rules="[required]"
                  v-model="projektName"
                  label="Projekt Name"></v-text-field>
              <ClientAutoComplete v-model="clientData" :label="'Kunde'"/>
            </v-card-text>
            <v-alert type="error" v-if="responseError">{{responseError}}</v-alert>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="primary"
                  variant="text"
                  @click="newProjectDialog = false"
              >
                Abbrechen
              </v-btn>
              <v-btn
                  color="primary"
                  variant="text"
                  @click="createNewProject"
              >
                Projekt erstellen
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-btn color="primary" @click="this.newProjectDialog = true">
          Neues Projekt
        </v-btn>
            </v-col>
            <v-col md="8" align-self="center">
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
import ProjectTable from "@/components/ProjectTable.vue";
import ClientAutoComplete from "@/components/ClientAutoComplete.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllProjectView",
  components: {ClientAutoComplete, ProjectTable},
})
</script>

<script setup lang="ts">

import {AutoCompleteResponse} from "@/interfaces/AutoCompleteResponse";
import {onBeforeMount, reactive, Ref, ref} from "vue";
import {Project} from "@/interfaces/Project";
import {HTTP} from "@/services/httpCommon";

const clientData: AutoCompleteResponse = reactive({id: 0, name: ''})
const projektName = ref('')
const newProjectDialog = ref(false)
const responseError = ref('')
const projects: Ref<Project[]> = ref([])
const page = ref(1)
const totalPages = ref(1)

function getPage(): void {
  HTTP.get(`/api/project/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        projects.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}
function projectSuccess (project:Project): void {
  newProjectDialog.value = false
  projects.value.push({
    id: project.id,
    name: project.name,
    client:
        {
          id: 0,
          address: {
            street: '',
            houseNumber: '',
            city: '',
            postcode: 0
          },
          name: clientData.name
        }
  })
}

function createNewProject (): void {
  HTTP.post('/api/project/accounting/create', {
    clientID: clientData.id,
    name: projektName.value
  })
      .then(response => projectSuccess(response.data))
      .catch((error) => {
        responseError.value = error.response.data
      })
}

function required (v:string) {
  return !!v || 'Feld wird benötigt'
}

onBeforeMount(() => {
  HTTP.get("/api/project/accounting/all?page=0&size=10")
      .then(res => {
        projects.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})

</script>