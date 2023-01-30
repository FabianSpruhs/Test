import {mount} from "@vue/test-utils";
import ClientDialog from "@/components/ClientDialog.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('ClientDialog.vue', () => {
    test('emit close dialog', () => {
        const wrapper = mount(ClientDialog.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const button = wrapper.find('v-btn')
        button.trigger('click')
        expect(wrapper.emitted('closeDialog')).toBeTruthy()
    })
    test('trigger createNewClient', () => {
        const wrapper = mount(ClientDialog.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const button = wrapper.findAll('v-btn')[1]
        const spy = jest.spyOn(wrapper.vm, 'createNewClient')
        button.trigger('click')
        expect(button.text()).toContain('Kunden anlegen')
        expect(spy).toHaveBeenCalled()
    })
    test('create with http', () => {
        const wrapper = mount(ClientDialog.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            clientData: {
                name: "Test Name",
                street: "Test Street",
                houseNumber: "1a",
                city: "Bonn",
                postCode: 50674,
            },
            employeeData: {id: 1}
        })
        const spy = jest.spyOn(HTTP, 'post')
        wrapper.vm.createNewClient()
        expect(spy).toHaveBeenCalled()
        expect(spy).toHaveBeenCalledWith('/api/client/accounting', {address: {
            city: wrapper.vm.clientData.address.city,
            houseNumber: wrapper.vm.clientData.address.houseNumber,
            postcode: wrapper.vm.clientData.address.postcode,
            street: wrapper.vm.clientData.address.street},
            // @ts-ignore
            contactEmployeeID: wrapper.vm.employeeData.id,
            name: wrapper.vm.clientData.name})
    })
})