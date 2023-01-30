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
      <th scope="col">
        Ändern
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
        class="text-left"
        v-for="(project, index) in props.projects"
        :key="index"
    >
      <td>{{ index + 1 }}</td>
      <td>{{project.name}}</td>
      <td>{{project.client.name}}</td>
      <td><v-btn icon="mdi-pencil-outline" @click="editProjectDialog = true; projektID = project.id"></v-btn></td>
    </tr>
    </tbody>
    <v-dialog v-model="editProjectDialog" max-width="500" persistent>
      <v-card title="Projektdaten ändern" >
        <v-card-text>
          <v-text-field
              v-model="projektName"
              label="Neuer Projekt Name"></v-text-field>

        <ClientAutoComplete :label="'Neuer Kunde'" v-model="clientData"/>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="primary"
              variant="text"
              @click="cancelEditProject()"
          >
            Abbrechen
          </v-btn>
          <v-btn
              color="primary"
              variant="text"
              @click="editProject()"
          >
            Ändern
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-table>
</template>

<script lang="ts">
import ClientAutoComplete from "@/components/ClientAutoComplete.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "ProjectTable",
  components: {ClientAutoComplete},

})
</script>

<script setup lang="ts">
import {defineProps, Ref, ref} from "vue";
import {Project} from "@/interfaces/Project";
import {HTTP} from "@/services/httpCommon";
import {AutoCompleteResponse} from "@/interfaces/AutoCompleteResponse";

const props = defineProps<{
  projects: Project[]
}>()

const tableHeads = ['Name', 'Kunde']
const editProjectDialog = ref(false)
const clientData: Ref<AutoCompleteResponse> = ref({
  id: 0,
  name:''
})
const projektName = ref('')
const projektID = ref(0)

function cancelEditProject (): void {
  editProjectDialog.value = false
  clientData.value.name = ''
  clientData.value.id = 0
  projektName.value = ''
  projektID.value = 0
}

function editProject (): void {
  if (projektName.value !== "") {
    HTTP.put(`/api/project/accounting/edit/${projektID.value}`, {
      id: projektID.value,
      clientID: clientData.value.id,
      name: projektName.value
    }).then(() => {
      editProjectDialog.value = false
      clientData.value.name = ''
      clientData.value.id = 0
      projektName.value = ''
      projektID.value = 0
    })
  }
  if (clientData.value.name !== "") {
    HTTP.post(`/api/project/accounting/assign/?project_id=${projektID.value}&client_id=${clientData.value.id}`)
  }
}



</script>
