<template>
    <v-card>
      <v-card-title>
        <span class="text-h5">Neuen Kunden anlegen</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col md="12">
              <v-text-field v-model="this.clientData.name" label="Kundenname*" :rules="[required]" type="text" ></v-text-field>
            </v-col>
            <v-divider></v-divider>
            <v-col md="10">
              <v-text-field v-model="clientData.address.street" :rules="[required]" label="Strasse*" type="text"></v-text-field>
            </v-col>
            <v-col md="2">
              <v-text-field v-model="clientData.address.houseNumber" :rules="[required]" label="Nr.*" type="text"></v-text-field>
            </v-col>
            <v-col md="6">
              <v-text-field v-model="clientData.address.city" :rules="[required]" label="Stadt*" type="text"></v-text-field>
            </v-col>
            <v-col md="6">
              <v-text-field v-model="clientData.address.postcode" :rules="[required]" label="Postleitzahl*" type="number"></v-text-field>
            </v-col>
            <v-divider></v-divider>
            <v-col md="12">
              <ContactEmployeeAutoComplete v-model="employeeData" :lableText="'Zuständiger Mitarbeiter*'"/>
            </v-col>
          </v-row>
        </v-container>
        <small>*Pflichtfeld</small>
      </v-card-text>
      <v-alert type="error" v-if="responseError">{{responseError}}</v-alert>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            color="primary"
            variant="text"
            @click="emit('closeDialog')"
        >
          Abbrechen
        </v-btn>
        <v-btn
            color="primary"
            variant="text"
            @click="createNewClient()"
        >
          Kunden anlegen
        </v-btn>
      </v-card-actions>
    </v-card>
</template>

<script lang="ts">
import ContactEmployeeAutoComplete from "@/components/ContactEmployeeAutoComplete.vue";
import {defineComponent} from "vue";


export default defineComponent({
  name: "ClientDialog",
  components: {ContactEmployeeAutoComplete},
})
</script>

<script setup lang="ts">
import {Client} from "@/interfaces/Client";
import {HTTP} from "@/services/httpCommon";
import {defineEmits, reactive, ref} from "vue";


const emit = defineEmits<{
  (e: 'closeDialog'): void
}>()

const responseError = ref('')
const clientData = reactive({
  name: '',
  address: {
    street: '',
    houseNumber: '',
    city: '',
    postcode: 0,
  }
} as Client )
const employeeData = reactive({
  name: '',
  id: 0
})


function required (v:string | number) {
  return !!v || 'Feld wird benötigt'
}

function createNewClient (): void {
  HTTP.post('/api/client/accounting', {
    contactEmployeeID: employeeData.id,
    name: clientData.name,
    address: {
      street: clientData.address.street,
      houseNumber: clientData.address.houseNumber,
      postcode: clientData.address.postcode,
      city: clientData.address.city,
    }})
      .then(() => emit('closeDialog'))
      .catch((error) => {
        responseError.value = error.response.data
      })
}
</script>