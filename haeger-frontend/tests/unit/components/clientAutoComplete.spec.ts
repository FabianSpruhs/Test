import {mount} from "@vue/test-utils";
import ClientAutoComplete from "@/components/ClientAutoComplete.vue";
import {createTestingPinia} from "@pinia/testing";

describe('ClientAutoComplete.vue', () => {
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
    test('render props.lable', () => {
        const testLabel = 'test Label'
        const wrapper = mount(ClientAutoComplete.vue, {
            props: {label: testLabel},
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const autoComplete = wrapper.find('v-autocomplete')
        expect(autoComplete.attributes('label')).toContain(testLabel)
    })
    test('computed returnID', () => {
        const wrapper = mount(ClientAutoComplete.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            clients: clientList,
            clientName: clientList[0].name
        })
        expect(wrapper.vm.returnID).toEqual({"clientName": clientList[0].name, "id": clientList[0].id})
    })
    test('watcher clients', () => {
        const wrapper = mount(ClientAutoComplete.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            clients: clientList,
            clientName: clientList[0].name,
        })
        // @ts-ignore
        wrapper.vm.$options.watch.clients.call(wrapper.vm)
        expect(wrapper.vm.clientsView).toEqual([clientList[0].name, clientList[1].name])
    })
    test('watcher clientName', () => {
        const wrapper = mount(ClientAutoComplete, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            clients: clientList,
            clientName: clientList[0].name,
        })
        // @ts-ignore
        wrapper.vm.$options.watch.clientName.call(wrapper.vm)
        expect(wrapper.emitted('update:modelValue')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('update:modelValue')[0][0]).toEqual({"clientName": clientList[0].name, "id": clientList[0].id})
    })
})