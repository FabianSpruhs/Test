<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Gebuchte Arbeitsstunden">
      <WorkingHourTable :myWorkingHours="bookedWorkingHours" :booked="true"/>
      <v-card-actions>
        <v-container>
          <v-row>
            <v-col md="2" align-self="center">
        <v-dialog
            v-model="finalizeDialog"
            max-width="500"
            persistent
        >
          <v-card>
            <v-card-title>
              <span class="text-h5">Stunden Finalisieren</span>
            </v-card-title>
            <v-card-text>
              Bestätigen Sie, dass Sie alle Stunden finalisieren wollen.
            </v-card-text>
            <v-alert type="error" v-if="errorMessage">{{errorMessage}}</v-alert>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="primary"
                  variant="text"
                  @click="finalizeDialog = false"
              >
                Abbrechen
              </v-btn>
              <v-btn
                  color="primary"
                  variant="text"
                  @click="finalizeWorkingHours"
              >
                Bestätigen
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-btn color="primary" @click="this.finalizeDialog = true">
          Finalisieren
        </v-btn>
            </v-col>
            <v-col md="2" align-self="center">
        <v-dialog
            v-model="bookDialog"
            max-width="500"
            persistent
        >
          <BookedWorkingHourDialog title="Arbeitsstunde buchen">
            <template #cancelButton>
              <v-btn @click="bookDialog = false" color="primary">Abbrechen</v-btn>
            </template>
            <template #confirmButton="confirmProps">
              <v-btn @click="bookWorkingHour(confirmProps.date, confirmProps.hours)" color="primary">Buchen</v-btn>
            </template>
          </BookedWorkingHourDialog>
        </v-dialog>
        <v-btn color="primary" @click="this.bookDialog = true">
          Buchen
        </v-btn>
            </v-col>
            <v-col md="4">
        <div class="text-center">
          <v-pagination class="text-center"
                        v-model="page"
                        :length="totalPages"
                        :total-visible="7"
                        @click="getPage()"
          ></v-pagination>
        </div>
            </v-col>
          </v-row>
        </v-container>
      </v-card-actions>
    </v-card>
  </v-sheet>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Finalisierte Arbeitsstunden">
      <WorkingHourTable :myWorkingHours="finalizedWorkingHours"/>
      <v-footer
          class="justify-center"
          color="primary"
      >
        Noch zu arbeitende Stunden: {{ workingHourAccounting }}
      </v-footer>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">
import WorkingHourTable from "@/components/WorkingHourTable.vue";
import BookedWorkingHourDialog from "@/components/BookedWorkingHourDialog.vue";
import {defineComponent} from "vue";


export default defineComponent({
  name: "MyWorkingHourView",
  components: {BookedWorkingHourDialog, WorkingHourTable},
})
</script>

<script setup lang="ts">
import {computed, ComputedRef, onBeforeMount, Ref, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {useUserStore} from "@/store/UserStore";
import {WorkingHour} from "@/interfaces/WorkingHour";
import {useRoute} from "vue-router";

const userStore = useUserStore()
const myWorkingHours: Ref<WorkingHour[]> = ref([])
const workingHourAccounting = ref(0)
const finalizeDialog = ref(false)
const bookDialog = ref(false)
const errorMessage = ref('')
const page = ref(1)
const totalPages = ref(1)
const route = useRoute()

const bookedWorkingHours: ComputedRef<WorkingHour[]> = computed((): WorkingHour[] => {
  if (myWorkingHours.value) {
    return myWorkingHours.value.filter(workingHour => workingHour.workingHourStatus === 'BOOKED')
  }
  return []
});

const finalizedWorkingHours: ComputedRef<WorkingHour[]> = computed((): WorkingHour[] => {
  if (myWorkingHours.value) {
    return myWorkingHours.value.filter(workingHour => workingHour.workingHourStatus === 'FINALIZED')
  }
  return []
});

function getPage(): void {
  HTTP.get(`/api/working_hour/employee/${route.params.id}`)
      .then(res => {
        myWorkingHours.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

function finalizeWorkingHours (): void {
  HTTP.put(`/api/working_hour/finalize/${userStore.getEmployeeID}`)
      .then(() => {
        finalizeDialog.value = false
        myWorkingHours.value.forEach(workingHour => workingHour.workingHourStatus = 'FINALIZED')
      })
      .catch((error) => errorMessage.value = error)
}

function bookWorkingHour (date:string, hours:number): void {
  HTTP.post("/api/working_hour/book", {
    employeeID: userStore.getEmployeeID,
    workingDay: date,
    workingHours: hours
  })
      .then(response => {
        bookDialog.value = false
        myWorkingHours.value.push(response.data)
      })
      .catch((error) => {
        errorMessage.value = error.response.data
      })
}

onBeforeMount(() => {
  HTTP.get(`/api/working_hour/employee/${route.params.id}`)
      .then(res => {
        myWorkingHours.value = res.data.content
        totalPages.value = res.data.totalPages
      });
  HTTP.get(`/api/working_hour/account/${route.params.id}`)
      .then(res => { workingHourAccounting.value = res.data.workingHour});
})
</script>