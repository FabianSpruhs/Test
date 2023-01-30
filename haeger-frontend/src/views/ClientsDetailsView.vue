<template>
  <v-sheet class="pa-12" rounded>
  <v-card
      class="mx-auto"
      max-width="600"
  >
    <v-toolbar color="primary">
      <v-toolbar-title>{{client.name}}</v-toolbar-title>
    </v-toolbar>
    <v-list>
      <v-list-subheader >Kunde</v-list-subheader>
      <v-list-item :title=this.client.name subtitle="Kundenname" class="text-left" >

        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-account-tie</v-icon>
          </v-avatar>
        </template>
        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editName(this.client.name)"
          ></v-btn>
        </template>
      </v-list-item>
      <v-divider inset></v-divider>
      <AddressList :address="this.client.address" @newAddress="newAddress"/>
      <v-divider inset></v-divider>
      <v-list-subheader >Mitarbeiter</v-list-subheader>
      <v-list-item :title=this.contactEmployee subtitle="Zuständiger Mitarbeiter" class="text-left" >
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-account</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editContactEmployeeDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-dialog v-model="editNameDialog" persistent max-width="800">
        <v-card title="Ändere Kunden Name">
          <v-card-text>
            <v-text-field v-model="newData" :placeholder="oldData" persistent-placeholder label="Kundenname"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="this.editNameDialog= false; this.newData = ''">Abbrechen</v-btn>
            <v-btn color="=primary" @click="commitEditName">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="editContactEmployeeDialog" persistent max-width="800">
        <v-card title="Zuständigen Mitarbeiter ändern">
          <v-card-text>
            <ContactEmployeeAutoComplete :lableText="'Neuer zuständiger Mitarbeiter'" v-model="newContactEmployeeId"/>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="this.editContactEmployeeDialog = false; this.newContactEmployeeId = {id: 0, name: ''}">Abbrechen</v-btn>
            <v-btn color="=primary" @click="commitEditContactEmployee">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-list>
  </v-card>
  </v-sheet>
</template>

<script lang="ts">
import AddressList from "@/components/AddressList.vue";
import ContactEmployeeAutoComplete from "@/components/ContactEmployeeAutoComplete.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "ClientsDetailsView",
  components: {ContactEmployeeAutoComplete, AddressList},
})
</script>

<script setup lang="ts">
import {onBeforeMount, reactive, Ref, ref, watch} from "vue";
import {Client} from "@/interfaces/Client";
import {HTTP} from "@/services/httpCommon";
import {useRoute} from "vue-router";
import {Address} from "@/interfaces/Address";
import {AutoCompleteResponse} from "@/interfaces/AutoCompleteResponse";

const clients: Ref<Client[]> = ref([])
const client: Ref<Client> = ref({
  id: 0,
  name: '',
  address: {
    city: '',
    street: '',
    postcode: 0,
    houseNumber: '',
  },
  contactEmployee: {
    id: 0,
    firstName: '',
    lastName: '',
    address: {
      city: '',
      street: '',
      postcode: 0,
      houseNumber: '',
    },
    mail: '',
    telephoneNumber: '',
    status: '',
    role: '',
    scheduledVacationDays: 0
  },
})
const contactEmployee = ref('')
const newContactEmployeeId: AutoCompleteResponse = reactive({id: 0, name: ''})
const editContactEmployeeDialog = ref(false)
const editNameDialog = ref(false)
const oldData = ref('')
const newData = ref('')
const route = useRoute()

onBeforeMount(() => {
  HTTP.get("/api/client/accounting/all?page=0&size=1000")
      .then(res => { clients.value = res.data.content })
})

function editName(newName:string) {
  editNameDialog.value = true
  oldData.value = newName
}

function commitEditName() {
  client.value.name = newData.value
  newData.value = ''
  sendEdit()
  editNameDialog.value = false
}

function commitEditContactEmployee() {
  if (client.value.contactEmployee) {
    client.value.contactEmployee.id = newContactEmployeeId.id
  }
  newContactEmployeeId.id = 0
  newContactEmployeeId.name = ''
  sendEdit()
  editContactEmployeeDialog.value = false
}

function newAddress(address:Address) {
  client.value.address.street = address.street
  client.value.address.postcode = address.postcode
  client.value.address.houseNumber = address.houseNumber
  client.value.address.city = address.city
  sendEdit()
}

function sendEdit() {
  if (client.value.contactEmployee) {
    HTTP.put(`/api/client/accounting/${client.value.id}`, {
      id: client.value.id,
      address: client.value.address,
      name: client.value.name,
      contactEmployeeID: client.value.contactEmployee.id
    })
  }
}

watch(clients, () => {
  const clientWithId = clients.value.find(client => client.id === parseInt(route.params.id.toString()))
  if (clientWithId)
    client.value = clientWithId
})

watch(client, () => {
  if(client.value.contactEmployee)
  contactEmployee.value = `${client.value.contactEmployee.firstName} ${client.value.contactEmployee.lastName}`
})

</script>
