import MyDataView from "@/views/myView/MyDataView.vue";
import {mount} from "@vue/test-utils";
import {HTTP} from "@/services/httpCommon";
import {createTestingPinia} from "@pinia/testing";

describe('MyDataView.vue', () => {
    const mockRoute = {
        params: {
            id: 1
        }
    }
    const mockRouter = {
        push: jest.fn()
    }
    const employee = {
        id: 1,
        firstName: 'first',
        lastName: 'last',
        address: {
            street: 'street',
            houseNumber: '1a',
            city: 'Bonn',
            postcode: 20000,
        },
    }
    test('created', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(MyDataView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        expect(spy).toBeCalledWith("/api/employee/1")
    })
    test('editFirstNameDialog', () => {
        const wrapper = mount(MyDataView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.setData({
            employee: employee
        })
        const spy = jest.spyOn(wrapper.vm, 'sendEdit')
        wrapper.vm.editFirstName()
        expect(spy).toBeCalled()
        expect(wrapper.vm.editFirstNameDialog).toBeFalsy()
        expect(wrapper.vm.newFirstName).toEqual('')
    })
    test('editLastNameDialog', () => {
        const wrapper = mount(MyDataView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.setData({
            employee: employee
        })
        const spy = jest.spyOn(wrapper.vm, 'sendEdit')
        wrapper.vm.editLastName()
        expect(spy).toBeCalled()
        expect(wrapper.vm.editLastNameDialog).toBeFalsy()
        expect(wrapper.vm.newLastName).toEqual('')
    })
    test('editMailDialog', () => {
        const wrapper = mount(MyDataView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.setData({
            employee: employee
        })
        const spy = jest.spyOn(wrapper.vm, 'sendEdit')
        wrapper.vm.editMail()
        expect(spy).toBeCalled()
        expect(wrapper.vm.editMailDialog).toBeFalsy()
        expect(wrapper.vm.newMail).toEqual('')
    })
    test('editTelephoneNumberDialog', () => {
        const wrapper = mount(MyDataView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.setData({
            employee: employee
        })
        const spy = jest.spyOn(wrapper.vm, 'sendEdit')
        wrapper.vm.editTelephoneNumber()
        expect(spy).toBeCalled()
        expect(wrapper.vm.editTelephoneNumberDialog).toBeFalsy()
        expect(wrapper.vm.newTelephoneNumber).toEqual('')
    })
    test('sendEdit', () => {
        const wrapper = mount(MyDataView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.setData({
            employee: employee
        })
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.vm.sendEdit()
        expect(spy).toBeCalledWith('/api/employee/accounting/edit/1', employee)
    })
})