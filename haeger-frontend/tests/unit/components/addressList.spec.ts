import {shallowMount} from "@vue/test-utils";
import {mount} from "@vue/test-utils";
import AddressList from "@/components/AddressList.vue";

const address = {
    street: "Main Street",
    houseNumber: "2a",
    postcode: 99999,
    city: "Bonn"
}

describe('AddressList.vue', () => {
    it('renders props.address when passed', () => {
        const wrapper = shallowMount(AddressList.vue, {
            props: {address}
        })
        const vListItem = wrapper.findAll('v-list-item')
        expect(vListItem[0].attributes('title')).toBe(address.street)
        expect(vListItem[1].attributes('title')).toBe(address.houseNumber)
        expect(vListItem[2].attributes('title')).toBe(address.postcode.toString())
        expect(vListItem[3].attributes('title')).toBe(address.city)
    })
    it('close streetDialog', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({ streetDialog: true })
        wrapper.setData({ newAddress: address })
        const button = wrapper.findAll('v-btn')[0]
        button.trigger('click')
        expect(button.text()).toBe('Abbrechen')
        expect(wrapper.vm.streetDialog).toBe(false)
        expect(wrapper.vm.newAddress.street).toBe('')


    })
    test('close cityDialog', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({ cityDialog: true })
        wrapper.setData({ newAddress: address })
        const button = wrapper.findAll('v-btn')[4]
        button.trigger('click')
        expect(button.text()).toBe('Abbrechen')
        expect(wrapper.vm.cityDialog).toBe(false)
        expect(wrapper.vm.newAddress.city).toBe('')
    })
    test('close houseNumberDialog', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({ houseNumberDialog: true })
        wrapper.setData({ newAddress: address })
        const button = wrapper.findAll('v-btn')[2]
        button.trigger('click')
        expect(button.text()).toBe('Abbrechen')
        expect(wrapper.vm.houseNumberDialog).toBe(false)
        expect(wrapper.vm.newAddress.houseNumber).toBe('')
    })
    test('close postCodeDialog', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({ postCodeDialog: true })
        wrapper.setData({ newAddress: address })
        const button = wrapper.findAll('v-btn')[6]
        button.trigger('click')
        expect(button.text()).toBe('Abbrechen')
        expect(wrapper.vm.postCodeDialog).toBe(false)
        expect(wrapper.vm.newAddress.postcode).toBe(0)
    })
    test('editStreet', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({streetDialog: true})
        const spy = jest.spyOn(wrapper.vm, 'resetNewAddress')
        wrapper.vm.editStreet()
        expect(wrapper.vm.streetDialog).toBe(false)
        expect(spy).toHaveBeenCalled()
        expect(wrapper.emitted('newAddress')).toBeTruthy()
    })
    test('editHouseNumber', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })

        wrapper.setData({houseNumberDialog: true})
        const spy = jest.spyOn(wrapper.vm, 'resetNewAddress')
        wrapper.vm.editHouseNumber()
        expect(wrapper.vm.houseNumberDialog).toBe(false)
        expect(spy).toHaveBeenCalled()
        expect(wrapper.emitted('newAddress')).toBeTruthy()
    })
    test('editCity', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({cityDialog: true})
        const spy = jest.spyOn(wrapper.vm, 'resetNewAddress')
        wrapper.vm.editCity()
        expect(wrapper.vm.cityDialog).toBe(false)
        expect(spy).toHaveBeenCalled()
        expect(wrapper.emitted('newAddress')).toBeTruthy()
    })
    test('editPostcode', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({postCodeDialog: true})
        const spy = jest.spyOn(wrapper.vm, 'resetNewAddress')
        wrapper.vm.editPostcode()
        expect(wrapper.vm.postCodeDialog).toBe(false)
        expect(spy).toHaveBeenCalled()
        expect(wrapper.emitted('newAddress')).toBeTruthy()
    })
    test('resetNewAddress', () => {
        const wrapper = mount(AddressList.vue, {
            props: {address}
        })
        wrapper.setData({newAddress: address})
        wrapper.vm.resetNewAddress()
        expect(wrapper.vm.newAddress).toStrictEqual({
                street: "",
                houseNumber: "",
                postcode: 0,
                city: ""
            }
        )
    })
})
