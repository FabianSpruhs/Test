<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto px-6 py-8" max-width="344" title="Anmelden">
      <v-card-text>
        <v-alert type="error" v-if="error">Benutzername oder Password falsch.</v-alert>
      <v-form
          v-model="form"
          @submit.prevent="signIn"
      >
        <v-text-field
            v-model="userName"
            :readonly="loading"
            :rules="[required]"
            class="mb-2"
            clearable
            label="Benutzername"
        ></v-text-field>

        <v-text-field type="password"
            v-model="password"
            :readonly="loading"
            :rules="[required]"
            clearable
            label="Password"
            placeholder="Password eingeben"
        ></v-text-field>
        <br>
        <v-btn
            :disabled="!form"
            :loading="loading"
            block
            color="primary"
            size="large"
            type="submit"
            variant="elevated"
        >
          Anmelden
        </v-btn>
      </v-form>
      </v-card-text>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "LoginPageView"
})
</script>

<script setup lang="ts">
import {useUserStore} from "@/store/UserStore";
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const userStore = useUserStore()
const form = ref(false)
const userName = ref('')
const password = ref('')
const loading = ref(false)
const responseText = ref('')
const errorText = ref('')

function required (v:string) {
  return !!v || 'Feld wird benötigt'
}

function signIn () {
  axios.post(`${process.env.VUE_APP_BASEURL}/api/auth`, {
    userName: userName.value,
    password: password.value
  }).then(response => {
    responseText.value = response.data
    userStore.setUserName(response.data.userName)
    userStore.setToken(response.data.token)
    userStore.setEmployeeID(response.data.employeeID)
    userStore.setRoles(response.data.roles)
    userStore.setRoles(response.data.roles)
    userStore.logIn()
    router.push({path: '/'})
  }).catch(error => errorText.value = error)
}
</script>
