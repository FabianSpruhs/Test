<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200">
      <v-card-title>Übersicht Urlaub</v-card-title>
  <VacationTable :myVacationRequests="myVacationRequests"
                 :remainingVacationDays="remainingVacationDays"/>
      <div class="text-center">
        <v-pagination class="text-center"
                      v-model="page"
                      :length="totalPages"
                      :total-visible="7"
                      @click="getPage()"
        ></v-pagination>
      </div>
      <v-footer
          class="justify-center"
          color="primary"
      >
        Restliche Urlaubstage: {{ remainingVacationDays }}
      </v-footer>
    </v-card>
  </v-sheet>

  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        max-width="500"
        persistent
    >
      <template v-slot:activator="{ props }">
        <v-btn
            color="primary"
            v-bind="props"
        >
          Urlaub einreichen
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="text-h5">Urlaubsantrag einreichen</span>
        </v-card-title>
        <v-card-text>
          <v-form
              v-model="form"
              @submit.prevent="onSubmit"
              ref="inputForm"
          >
            <DoubleDateInputField
                firstLable="Urlaub von"
                secondLable="Urlaub bis"
                @doubleDateInput="doubleDateInput"/>

          </v-form>
        </v-card-text>
        <v-alert type="error" v-if="errorMessage">{{errorMessage}}</v-alert>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="blue-darken-1"
              variant="text"
              @click="dialog = false"
          >
            Close
          </v-btn>
          <v-btn
              color="blue-darken-1"
              variant="text"
              :disabled="!form"
              @click="onSubmit"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script lang="ts">
import VacationTable from "@/components/VacationTable.vue";
import DoubleDateInputField from "@/components/DoubleDateInputField.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "MyVacationView",
  components: {DoubleDateInputField, VacationTable},
})
</script>

<script setup lang="ts">
import {onBeforeMount, Ref, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {useUserStore} from "@/store/UserStore";
import {useRoute} from "vue-router";
import {VacationRequest} from "@/interfaces/VacationRequest";

const userStore = useUserStore()
const dialog = ref(false)
const form = ref(false)
const beginDate = ref('')
const endDate = ref('')
const remainingVacationDays = ref(0)
const myVacationRequests: Ref<VacationRequest[]> = ref([])
const errorMessage = ref('')
const page = ref(1)
const totalPages = ref(1)
const route = useRoute()

function getPage() {
  HTTP.get(`/api/vacation_request/employee/${route.params.id}?page=${page.value - 1}&size=10`)
      .then(res => {
        myVacationRequests.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

function doubleDateInput (doubleDate: {beginDate: string, endDate: string}) {
  beginDate.value = doubleDate.beginDate
  endDate.value = doubleDate.endDate
}

function onSubmit () {
  if (!form.value) return
  HTTP.post("/api/vacation_request/request",
      {employeeID: userStore.getEmployeeID,
        beginDate: beginDate.value,
        endDate: endDate.value
      })
      .then((response) => {
        myVacationRequests.value.push(response.data)
        dialog.value = false
      })
      .catch((error) => errorMessage.value = error.response.data)
}

onBeforeMount(() => {
  HTTP.get(`/api/vacation_request/employee/${route.params.id}?page=0&size=10`)
      .then(res => {
        myVacationRequests.value = res.data.content
        totalPages.value = res.data.totalPages
      });
  HTTP.get(`/api/employee/remaining_vacation/${route.params.id}`)
      .then(res => { remainingVacationDays.value = res.data});
})
</script>
