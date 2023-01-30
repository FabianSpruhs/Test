<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200">
      <v-card-text>
      <v-table fixed-header
               height="500px">
        <thead>
        <tr>
          <th class="text-left" scope="col">
            #
          </th>
          <th class="text-left" scope="col">
            Vorname
          </th>
          <th class="text-left" scope="col">
            Nachname
          </th>
          <th class="text-left" scope="col">
            Status
          </th>
          <th class="text-left" scope="col">
            Rolle
          </th>
          <th class="text-left" scope="col">
            Löschen
          </th>
        </tr>
        </thead>
        <tbody>
        <tr
            class="text-left"
            v-for="(employee, index) in employees"
            :key="index"

        >
          <td>{{ index + 1 }}</td>
          <td>{{ employee.firstName }}</td>
          <td>{{ employee.lastName }}</td>
          <td>
            <div class="text-left">
            <v-menu >
              <template v-slot:activator="{ props }">
                <v-btn v-bind="props" variant="text">
                  {{ employee.status }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                    v-for="(item) in status"
                    :key="item"
                >
                  <v-list-item-title><v-btn variant="text" @click="editStatus(employee.id, item)">{{ item }}</v-btn></v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          </td>
          <td>
            <div class="text-left">
              <v-menu >
                <template v-slot:activator="{ props }">
                  <v-btn v-bind="props" variant="text">
                    {{ employee.role }}
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                      v-for="(item) in role"
                      :key="item"
                  >
                    <v-list-item-title><v-btn variant="text" @click="editRole(employee.id, item)">{{ item }}</v-btn></v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </td>
          <td><v-btn icon="mdi-skull-scan-outline" color="red"></v-btn></td>
        </tr>
        </tbody>
      </v-table>
      </v-card-text>
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
import {defineComponent} from "vue";

export default defineComponent({
  name: "AdminEmployeeView",
})
</script>

<script setup lang="ts">
import {onBeforeMount, Ref, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {Employee} from "@/interfaces/Employee";

const employees: Ref<Employee[]> = ref([])
const status = ['ACTIVE', 'PASSIVE', 'VACATION', 'SICK']
const role = ['STANDARD', 'ACCOUNTING', 'ADMIN']
const page = ref(1)
const totalPages = ref(1)

function getPage(): void {
  HTTP.get(`/api/employee/accounting/all?page=${page.value - 1}&size=10`)
      .then(res => {
        employees.value = res.data.content
        totalPages.value = res.data.totalPages
      });
}

function editRole(id:number, role:string): void {
  HTTP.put(`/api/employee/admin/edit/role?employee_id=${id}&role=${role}`).then(() => {
    const employee = employees.value.find(employee => employee.id === id)
    if (employee) {
      employee.role = role
    }
  })
}

function editStatus(id:number, status:string): void {
  HTTP.put(`/api/employee/admin/edit/status?employee_id=${id}&status=${status}`).then(() => {
    const employee = employees.value.find(employee => employee.id === id)
    if (employee) {
      employee.status = status
    }
  })
}

onBeforeMount(() => {
  HTTP.get(`/api/employee/accounting/all?page=0&size=10`)
      .then(res => {
        employees.value = res.data.content
        totalPages.value = res.data.totalPages
      });
})
</script>
