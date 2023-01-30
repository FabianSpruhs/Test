  <template>
    <v-sheet class="pa-12" rounded>
      <v-card class="mx-auto px-6 py-8" max-width="350" title="Krankmeldung einreichen">
        <v-alert density="comfortable" type="success" v-if="success">Krankmeldung Erfolgreich eingereicht</v-alert>

        <v-overlay v-model="overlay" contained class="align-center justify-center">
          <v-alert type="error"> {{responseError}} </v-alert>
        </v-overlay>

        <v-form
            v-model="form"
            @submit.prevent="onSubmit"
            ref="inputForm"
        >
          <DoubleDateInputField
              firstLable="Krank geschrieben von"
              secondLable="Krank geschrieben bis"
              @doubleDateInput="doubleDateInput"/>
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
            Einreichen
          </v-btn>
        </v-form>
      </v-card>
    </v-sheet>
  </template>

<script lang="ts">
import DoubleDateInputField from "@/components/DoubleDateInputField.vue";
import {defineComponent} from "vue";

export default defineComponent({

  name: "MySickNoteView",
  components: {DoubleDateInputField},

})
</script>

<script setup lang="ts">

import {HTTP} from "@/services/httpCommon";
import {useUserStore} from "@/store/UserStore";
import {ref} from "vue";

const userStore = useUserStore()
const overlay = ref(false)
const form = ref(false)
const loading = ref(false)
const success = ref(false)
const beginDate = ref('')
const endDate = ref('')
const responseError = ref('')

function doubleDateInput (doubleDates: {beginDate: string, endDate: string}): void {
  beginDate.value = doubleDates.beginDate
  endDate.value = doubleDates.endDate
}

function onSubmit (): void {
  if (!form.value) return

  loading.value = true

  HTTP.post("/api/sick_note/submit", {
    employeeId: userStore.getEmployeeID,
    beginDate: beginDate.value,
    endDate: endDate.value
  })
      .then(() => success.value = true)
      .catch((error) => {
        responseError.value = error.response.data
        overlay.value = true
      })

  loading.value = false
}
</script>