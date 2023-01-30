<template>
  <v-sheet class="pa-12" rounded>
  <v-card
      class="mx-auto"
      max-width="600"
  >
    <v-toolbar color="primary">
      <v-toolbar-title>{{employee.firstName}} {{employee.lastName}}</v-toolbar-title>
    </v-toolbar>
    <v-list>
      <v-list-subheader >Person</v-list-subheader>

      <v-list-item :title=this.employee.firstName subtitle="Vorname" class="text-left" >
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-account</v-icon>
          </v-avatar>
        </template>
        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editFirstNameDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-list-item :title=this.employee.lastName subtitle="Nachname" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-account</v-icon>
          </v-avatar>
        </template>
        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editLastNameDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-list-item :title=this.employee.mail subtitle="Mail" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-at</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editMailDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-list-item :title=this.employee.telephoneNumber subtitle="Telefonnummer" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-phone-outline</v-icon>
          </v-avatar>
        </template>
        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="editTelephoneNumberDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-divider inset></v-divider>
      <AddressList :address="this.employee.address" @newAddress="newAddress"/>
      <v-divider inset></v-divider>
      <v-list-subheader >Sonstiges</v-list-subheader>
      <v-list-item :title=this.employee.status subtitle="Status" class="text-left" >
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-clipboard-account-outline</v-icon>
          </v-avatar>
        </template>
      </v-list-item>
      <v-list-item :title=this.employee.role subtitle="Rolle" class="text-left" >
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-account-group-outline</v-icon>
          </v-avatar>
        </template>
      </v-list-item>
    </v-list>
    <v-dialog v-model="editFirstNameDialog" persistent max-width="800">
      <v-card title="Vorname ändern">
        <v-card-text>
          <v-text-field v-model="newFirstName" label="Neuer Vorname" persistent-placeholder :placeholder="this.employee.firstName"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="editFirstNameDialog = false">Abbrechen</v-btn>
          <v-btn @click="editFirstName">Ändern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editLastNameDialog" persistent max-width="800">
      <v-card title="Nachname ändern">
        <v-card-text>
          <v-text-field v-model="newLastName" label="Neuer Nachname" persistent-placeholder :placeholder="this.employee.lastName"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="editLastNameDialog = false; newLastName = ''">Abbrechen</v-btn>
          <v-btn @click="editLastName">Ändern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editMailDialog" persistent max-width="800">
      <v-card title="Mail ändern">
        <v-card-text>
          <v-text-field v-model="newMail" label="Neue Mail" persistent-placeholder :placeholder="this.employee.mail"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="editMailDialog = false">Abbrechen</v-btn>
          <v-btn @click="editMail">Ändern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editTelephoneNumberDialog" persistent max-width="800">
      <v-card title="Telefonnummer ändern">
        <v-card-text>
          <v-text-field v-model="newTelephoneNumber" label="Neue Telefonnummer" persistent-placeholder :placeholder="this.employee.telephoneNumber"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="editTelephoneNumberDialog = false">Abbrechen</v-btn>
          <v-btn @click="editTelephoneNumber">Ändern</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
  </v-sheet>
</template>

<script lang="ts">
import AddressList from "@/components/AddressList.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "MyDataView",
  components: {AddressList},
})
</script>

<script setup lang="ts">
import {Address} from "@/interfaces/Address";
import {HTTP} from "@/services/httpCommon";
import {onBeforeMount, Ref, ref} from "vue";
import {Employee} from "@/interfaces/Employee";
import {useRoute} from "vue-router";

const employee: Ref<Employee> = ref({
  id: 0,
  firstName: '',
  lastName: '',
  address: {
    street: '',
    city: '',
    postcode: 0,
    houseNumber: '',
  },
  mail: '',
  telephoneNumber: '',
  role: '',
  status: '',
  scheduledVacationDays: 0
})
const editFirstNameDialog = ref(false)
const editLastNameDialog = ref(false)
const editMailDialog = ref(false)
const editTelephoneNumberDialog = ref(false)
const newFirstName = ref('')
const newLastName = ref('')
const newMail = ref('')
const newTelephoneNumber = ref('')
const route = useRoute()

onBeforeMount(() => {
   HTTP.get(`/api/employee/${route.params.id}`)
      .then(res => { employee.value = res.data as Employee });
})

function editFirstName (): void {
  employee.value.firstName = newFirstName.value
  newFirstName.value = ''
  editFirstNameDialog.value = false
  sendEdit()
}

function editLastName (): void {
  employee.value.lastName = newLastName.value
  newLastName.value = ''
  editLastNameDialog.value = false
  sendEdit()
}

function editMail (): void {
  employee.value.mail = newMail.value
  newMail.value = ''
  editMailDialog.value = false
  sendEdit()
}

function editTelephoneNumber (): void {
  employee.value.telephoneNumber = newTelephoneNumber.value
  newTelephoneNumber.value = ''
  editTelephoneNumberDialog.value = false
  sendEdit()
}

function newAddress(address:Address): void {
  employee.value.address.street = address.street
  employee.value.address.postcode = address.postcode
  employee.value.address.houseNumber = address.houseNumber
  employee.value.address.city = address.city
  sendEdit()
}

function sendEdit(): void {
  HTTP.put(`/api/employee/accounting/edit/${employee.value.id}`, {
    id: employee.value.id,
    firstName: employee.value.firstName,
    lastName: employee.value.lastName,
    address: {
      street: employee.value.address.street,
      houseNumber: employee.value.address.houseNumber,
      postcode: employee.value.address.postcode,
      city: employee.value.address.city
    },
    mail: employee.value.mail,
    telephoneNumber: employee.value.telephoneNumber,
    scheduledVacationDays: employee.value.scheduledVacationDays
  })
}

</script>
