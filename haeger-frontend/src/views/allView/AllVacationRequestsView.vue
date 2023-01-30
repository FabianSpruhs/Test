<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Urlaubsanträge">
      <v-card-text>
      <VacationTableAll :myVacationRequests="vacationRequests" @approveVacation="approveVacation" @declineVacation="declineVacation"/>
      </v-card-text>
      <v-dialog
          v-model="approve"
          max-width="500"
          persistent
      >
        <v-card>
          <v-card-title>
            <span class="text-h5">Urlaubsantrag Bestätigen</span>
          </v-card-title>
          <v-card-text>
            <v-alert type="info" v-if="!errorMessage">
              Wollen Sie den Urlaubsantrag wirklich bestätigen?
            </v-alert>
          </v-card-text>
          <v-alert type="error" v-if="errorMessage">{{errorMessage}}</v-alert>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="primary"
                variant="text"
                @click="approve = false"
            >
              Abbrechen
            </v-btn>
            <v-btn
                color="primary"
                variant="text"
                @click="submitApproveVacation()"
            >
              Bestätigen
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog
          v-model="reject"
          max-width="500"
          persistent
      >
        <v-card>
          <v-card-title>
            <span class="text-h5">Urlaubsantrag Ablehnen</span>
          </v-card-title>
          <v-card-text>
            <v-alert type="warning" v-if="!errorMessage">
              Wollen Sie den Urlaubsantrag wirklich ablehnen?
            </v-alert>
          </v-card-text>
          <v-alert type="error" v-if="errorMessage">{{errorMessage}}</v-alert>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="primary"
                variant="text"
                @click="reject = false"
            >
              Abbrechen
            </v-btn>
            <v-btn
                color="primary"
                variant="text"
                @click="submitDeclineVacation()"
            >
              Bestätigen
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <div class="text-center">
        <v-pagination class="text-center"
                      v-model="page"
                      :length="totalPages"
                      :total-visible="7"
                      @click="getPage()"
        ></v-pagination>
      </div>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">
import VacationTableAll from "@/components/VacationTableAll.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "AllVacationRequestsView",
  components: {VacationTableAll},
})
</script>

<script setup lang="ts">
import {onBeforeMount, Ref, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {VacationRequest} from "@/interfaces/VacationRequest";

const vacationRequests: Ref<VacationRequest[]> = ref([])
const reject = ref(false)
const approve = ref(false)
const errorMessage = ref('')
const id = ref(0)
const page = ref(1)
const totalPages = ref(1)

function getPage(): void {
  HTTP.get(`/api/vacation_request/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        vacationRequests.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

function approveVacation(idToEdit:number): void {
  id.value = idToEdit
  approve.value = true
}

function submitApproveVacation(): void {
  HTTP.put(`/api/vacation_request/accounting/edit?id=${id.value}&status=APPROVED`).then(() => {
    approve.value = false
    const vacationRequest = vacationRequests.value.find(vacationRequest => vacationRequest.id === id.value)
    if (vacationRequest) {
      vacationRequest.status = "APPROVED"
    }
  }).catch(error => {
    errorMessage.value = error.data
  })
}

function declineVacation(idToEdit:number): void {
  id.value = idToEdit
  reject.value = true
}

function submitDeclineVacation(): void {
  HTTP.put(`/api/vacation_request/accounting/edit?id=${id.value}&status=REJECTED`).then(() => {
    reject.value = false
    const vacationRequest = vacationRequests.value.find(vacationRequest => vacationRequest.id === id.value)
    if (vacationRequest) {
      vacationRequest.status = "REJECTED"
    }
  }).catch(error => {
    errorMessage.value = error.data
  })
}

onBeforeMount(() => {
  HTTP.get(`/api/vacation_request/accounting/all?page=0&size=10`)
      .then(res => {
        vacationRequests.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})
</script>
