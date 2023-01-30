<template>
  <v-sheet class="pa-12" rounded>
  <v-card>
    <v-card-title>
      <span class="text-h5">Neuen Mitarbeiter einstellen</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-row>
          <v-col  md="6"><v-text-field prepend-inner-icon="mdi-account-outline" v-model="newFirstName" label="Vorname*" :rules="[required]" type="text" ></v-text-field></v-col>
          <v-col  md="6"><v-text-field prepend-inner-icon="mdi-account-outline" v-model="newLastName" label="Nachname*" :rules="[required]" type="text" ></v-text-field></v-col>
          <v-divider></v-divider>
          <v-col  md="10"><v-text-field prepend-inner-icon="mdi-home-outline" v-model="newStreet" :rules="[required]" label="Strasse*" type="text"></v-text-field></v-col>
          <v-col  md="2"><v-text-field prepend-inner-icon="mdi-home-outline" v-model="newHouseNumber" :rules="[required]" label="Nr.*" type="text"></v-text-field></v-col>
          <v-col  md="8"><v-text-field prepend-inner-icon="mdi-home-outline" v-model="newCity" :rules="[required]" label="Stadt*" type="text"></v-text-field></v-col>
          <v-col  md="4"><v-text-field prepend-inner-icon="mdi-home-outline" v-model="newPostcode" :rules="[required]" label="Postleitzahl*" type="number"></v-text-field></v-col>
          <v-divider></v-divider>
          <v-col  md="6"><v-text-field prepend-inner-icon="mdi-at" v-model="newMail" label="Mailadresse" type="mail"></v-text-field></v-col>
          <v-col  md="6"><v-text-field prepend-inner-icon="mdi-phone-outline" v-model="newTelephoneNumber" label="Telefonnummer" type="text"></v-text-field></v-col>
          <v-col  md="12"><v-slider append-icon="mdi-island" thumb-label="always" :step="0.5" v-model="vacationDays" min="0" max="40" label="Urlaubstage"></v-slider></v-col>
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
          @click="$router.push('/admin')"
      >
        Abbrechen
      </v-btn>
      <v-btn
          color="primary"
          variant="text"
          @click="hireNewEmployee()"
      >
        Mitarbeiter einstellen
      </v-btn>
    </v-card-actions>
    <v-dialog v-model="dialogSuccess">
      <v-card>
        <v-card-text>
          <v-alert type="success">
            Mitarbeiter {{newFirstName}} {{newLastName}} erfolgreich angelegt.
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" block @click="dialogSuccess = false">Ok</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
  </v-sheet>
</template>

<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({
  name: "AddNewEmployeeView",
})
</script>

<script setup lang="ts">
import {ref} from "vue";
import {HTTP} from "@/services/httpCommon";

const dialogSuccess = ref(false)
const vacationDays = ref(20)
const newFirstName = ref('')
const newLastName = ref('')
const newStreet = ref('')
const newHouseNumber = ref('')
const newCity = ref('')
const newMail = ref('')
const newTelephoneNumber = ref('')
const newPostcode = ref(0)
const responseError = ref('')

function hireNewEmployee () {
  HTTP.post('/api/employee/admin/hire', {
    firstName: newFirstName.value,
    lastName: newLastName.value,
    address: {
      street: newStreet.value,
      houseNumber: newHouseNumber.value,
      postcode: newPostcode.value,
      city: newCity.value,
    },
    mail: newMail.value,
    telephoneNumber: newTelephoneNumber.value,
    scheduledVacationDays: vacationDays.value
  })
      .then(() => dialogSuccess.value = true)
      .catch((error) => responseError.value = error.response.data)
}

function required (v:string) {
  return !!v || 'Feld wird benötigt'
}

</script>
