<template>
  <div v-if="props.address">
    <v-list>
      <v-list-subheader>Adresse</v-list-subheader>

      <v-list-item :title=props.address.street subtitle="Strasse" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-home-outline</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="streetDialog = true"
          ></v-btn>
        </template>
      </v-list-item>

      <v-list-item :title=props.address.houseNumber subtitle="Hausnummer" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-home-outline</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="houseNumberDialog = true"
          ></v-btn>
        </template>
      </v-list-item>

      <v-list-item :title=props.address.postcode subtitle="Postleitzahl" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-home-outline</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="postCodeDialog = true"
          ></v-btn>
        </template>
      </v-list-item>

      <v-list-item :title=props.address.city subtitle="Stadt" class="text-left">
        <template v-slot:prepend>
          <v-avatar color="grey-lighten-1">
            <v-icon color="white">mdi-home-outline</v-icon>
          </v-avatar>
        </template>

        <template v-slot:append>
          <v-btn
              icon="mdi-pencil"
              variant="text"
              @click="cityDialog = true"
          ></v-btn>
        </template>
      </v-list-item>
      <v-dialog v-model="streetDialog" persistent max-width="800">
        <v-card title="Ändere Strasse">
          <v-card-text>
            <v-text-field v-model="newAddress.street" :placeholder="props.address.street" persistent-placeholder label="Strasse"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="streetDialog= false; newAddress.street = ''">Abbrechen</v-btn>
            <v-btn color="=primary" @click="editStreet">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="houseNumberDialog" persistent max-width="800">
        <v-card title="Ändere Hausnummer">
          <v-card-text>
            <v-text-field v-model="newAddress.houseNumber" :placeholder="props.address.houseNumber" persistent-placeholder label="Hausnummer"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="houseNumberDialog = false; newAddress.houseNumber = ''">Abbrechen</v-btn>
            <v-btn color="=primary" @click="editHouseNumber">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="cityDialog" persistent max-width="800">
        <v-card title="Ändere Stadt">
          <v-card-text>
            <v-text-field v-model="newAddress.city" :placeholder="props.address.city" persistent-placeholder label="Stadt"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="cityDialog = false; newAddress.city = ''">Abbrechen</v-btn>
            <v-btn color="=primary" @click="editCity">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="postCodeDialog" persistent max-width="800">
        <v-card title="Ändere Postleitzahl">
          <v-card-text>
            <v-text-field type="number"  v-model="newAddress.postcode" persistent-placeholder label="Postleitzahl"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="=primary" @click="postCodeDialog = false; newAddress.postcode = 0">Abbrechen</v-btn>
            <v-btn color="=primary" @click="editPostcode">Ändern</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-list>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: "AddressList",
  emits: ['newAddress'],
})
</script>

<script setup lang="ts">

import {ref, defineProps, reactive, defineEmits} from "vue";
import {Address} from "@/interfaces/Address";

const props = defineProps<{
  address: Address
}>()

const emit = defineEmits<{
  (e: 'newAddress', newAddress:Address): void
}>()

const streetDialog = ref(false)
const cityDialog = ref(false)
const houseNumberDialog = ref(false)
const postCodeDialog = ref(false)
const newAddress:Address = reactive({
         street: '',
         houseNumber: '',
         city: '',
         postcode: 0
})

function resetNewAddress(): void {
  newAddress.city = ''
  newAddress.houseNumber = ''
  newAddress.postcode = 0
  newAddress.street = ''
}

function editStreet(): void {
  newAddress.city = props.address.city
  newAddress.houseNumber = props.address.houseNumber
  newAddress.postcode = props.address.postcode
  emit('newAddress', newAddress)
  resetNewAddress()
  streetDialog.value = false
}

function editHouseNumber(): void {
  newAddress.city = props.address.city
  newAddress.postcode = props.address.postcode
  newAddress.street = props.address.street
  emit('newAddress', newAddress)
  resetNewAddress()
  houseNumberDialog.value = false
}

function editCity(): void {
  newAddress.houseNumber = props.address.houseNumber
  newAddress.postcode = props.address.postcode
  newAddress.street = props.address.street
  emit('newAddress', newAddress)
  resetNewAddress()
  cityDialog.value = false
    }

function editPostcode(): void {
  newAddress.city = props.address.city
  newAddress.houseNumber = props.address.houseNumber
  newAddress.street = props.address.street
  emit('newAddress', newAddress)
  resetNewAddress()
  postCodeDialog.value = false
}
</script>