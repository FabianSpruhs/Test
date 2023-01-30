import { createRouter, createWebHistory } from 'vue-router'
import AdminVue from "@/views/AdminVue.vue";
import HomeView from "@/views/HomeView.vue";
import AccountingView from "@/views/AccountingView.vue";
import MyDataView from "@/views/myView/MyDataView.vue";
import MyVacationView from "@/views/myView/MyVacationView.vue";
import MySickNoteView from "@/views/myView/MySickNoteView.vue";
import MyProjectView from "@/views/myView/MyProjectView.vue";
import MyWorkingHourView from "@/views/myView/MyWorkingHourView.vue";
import AllEmployeeView from "@/views/allView/AllEmployeeView.vue";
import AllClientsView from "@/views/allView/AllClientsView.vue";
import AllProjectView from "@/views/allView/AllProjectView.vue";
import AllSickNotesView from "@/views/allView/AllSickNotesView.vue";
import AllVacationRequestsView from "@/views/allView/AllVacationRequestsView.vue";
import AllWorkingHoursView from "@/views/allView/AllWorkingHoursView.vue";
import AddNewEmployeeView from "@/views/AddNewEmployeeView.vue";
import ClientsDetailsView from "@/views/ClientsDetailsView.vue";
import AdminEmployeeView from "@/views/myView/AdminEmployeeView.vue";
import LoginPageView from "@/views/LoginPageView.vue";
import {useUserStore} from "@/store/UserStore";


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminVue
  },
  {
    path: '/my_data/:id',
    name: 'myData',
    component: MyDataView
  },
  {
    path: '/my_vacation/:id',
    name: 'myVacation',
    component: MyVacationView
  },
  {
    path: '/my_sick_note',
    name: 'mySickNote',
    component: MySickNoteView
  },
  {
    path: '/my_projects/:id',
    name: 'myProjects',
    component: MyProjectView
  },
  {
    path: '/my_working_hours/:id',
    name: 'myWorkingHours',
    component: MyWorkingHourView
  },
  {
    path: '/all_clients',
    name: 'allClients',
    component: AllClientsView
  },
  {
    path: '/all_employee',
    name: 'allEmployee',
    component: AllEmployeeView
  },
  {
    path: '/all_projects',
    name: 'allProjects',
    component: AllProjectView
  },
  {
    path: '/all_sick_notes',
    name: 'allSickNotes',
    component: AllSickNotesView
  },
  {
    path: '/all_vacation_requests',
    name: 'allVacationRequests',
    component: AllVacationRequestsView
  },
  {
    path: '/all_working_hours',
    name: 'allWorkingHours',
    component: AllWorkingHoursView
  },
  {
    path: '/new_employee',
    name: 'newEmployee',
    component: AddNewEmployeeView
  },
  {
    path: '/accounting',
    name: 'accounting',
    component: AccountingView,
  },
  {
    path: '/client/:id',
    name: 'client',
    component: ClientsDetailsView,
  },
  {
    path: '/admin_employee',
    name: 'adminEmployee',
    component: AdminEmployeeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginPageView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.name !== 'login' && !userStore.isLoggedIn) next({ name: 'login' })
  else next()
})

export default router
