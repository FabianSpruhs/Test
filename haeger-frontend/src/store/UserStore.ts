import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        userName: '',
        token: '',
        employeeID: 0,
        roles: '',
        loggedIn: false,
        fullName: '',
        mail: ''
    }),
    getters: {
        getUserName: state => {
            return state.userName
        },
        getToken: state => {
            return state.token
        },
        getEmployeeID: state => {
            return state.employeeID
        },
        getRoles: state => {
            return state.roles
        },
        isLoggedIn: state => {
            return state.loggedIn
        },
        getFullName: state => {
            return state.fullName
        },
        getMail: state => {
            return state.mail
        },
    },
    actions: {
        setUserName(userName:string) {
            this.userName = userName
        },
        setToken(token:string) {
            this.token = token
        },
        setRoles(roles:string) {
            this.roles = roles
        },
        setEmployeeID(employeeID:number) {
            this.employeeID = employeeID
        },
        logIn() {
            this.loggedIn = true
        },
        logOut() {
            this.loggedIn = false
        },
    },
    persist: {
        storage: sessionStorage,
    }
})