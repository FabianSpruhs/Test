import {mount} from "@vue/test-utils";
import AllClientsView from "@/views/allView/AllClientsView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('AllClientsView.vue', () => {
    const clientList = [
        {
            id: 1,
            name: "client",
            address: {
                street: "test street",
                houseNumber: "1a",
                postcode: 99999,
                city: "bonn"
            },
            contactEmployee: {
                id: 2,
                firstName: "firstName",
                lastName: "lastName",
            }
        },
        {
            id: 3,
            name: "test client",
            address: {
                street: "road",
                houseNumber: "4a",
                postcode: 99999,
                city: "Hamburg"
            },
            contactEmployee: {
                id: 4,
                firstName: "nameFirst",
                lastName: "nameLast",
            }
        }
    ]
    test('close Dialog', () => {
        const wrapper = mount(AllClientsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({newClientDialog: true})
        wrapper.vm.closeDialog()
        expect(wrapper.vm.newClientDialog).toBeFalsy()
    })
    test('getPage', () => {
        const wrapper = mount(AllClientsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/client/accounting/all?page=2&size=10")
    })
    test('clientSuccess', () => {
        const wrapper = mount(AllClientsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            clients: clientList
        })
        const newClient = {
            id: 10,
            name: "new test client",
            address: {
                street: "new road",
                houseNumber: "14a",
                postcode: 55555,
                city: "Berlin"
            },
            contactEmployee: {
                id: 40,
                firstName: "new nameFirst",
                lastName: " new nameLast",
            }
        }
        wrapper.vm.clientSuccess(newClient)
        expect(wrapper.vm.clients[2]).toEqual(newClient)
    })
    test('trigger getPage', () => {
        const wrapper = mount(AllClientsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const pagination = wrapper.find('v-pagination')
        const spy = jest.spyOn(wrapper.vm, 'getPage')
        pagination.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
})