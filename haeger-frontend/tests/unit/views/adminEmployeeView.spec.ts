import {mount} from "@vue/test-utils";
import AdminEmployeeView from "@/views/myView/AdminEmployeeView.vue";
import {HTTP} from "@/services/httpCommon";
import {createTestingPinia} from "@pinia/testing";

describe('AdminEmployeeView.vue', () => {
    const employeesList = [
        {
            id: 1,
            firstName: 'first',
            lastName: 'last',
            status: 'ACTIVE',
            role: 'ADMIN',
        },
        {
            id: 2,
            firstName: 'firstName',
            lastName: 'lastName',
            status: 'SICK',
            role: 'STANDARD',
        }
    ]
    test('get employee', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(AdminEmployeeView, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        expect(spy).toBeCalled()
        expect(spy).toBeCalledWith('/api/employee/accounting/all?page=0&size=10')
    })
    test('trigger getPage', () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const pagination = wrapper.find('v-pagination')
        const spy = jest.spyOn(wrapper.vm, 'getPage')
        pagination.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('getPage', () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/employee/accounting/all?page=2&size=10")
    })
    test('trigger edit role', async () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        await wrapper.setData({
            employees: employeesList
        })
        const button = wrapper.findAll('v-btn')[4]
        const spy = jest.spyOn(wrapper.vm, 'editRole')
        await button.trigger('click')
        expect(button.text()).toContain('STANDARD')
        expect(spy).toBeCalled()

    })
    test('edit role', () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            employees: employeesList
        })
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.vm.editRole(1, 'STANDARD')
        expect(spy).toBeCalledWith('/api/employee/admin/edit/role?employee_id=1&role=STANDARD')
    })
    test('trigger edit status', async () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        await wrapper.setData({
            employees: employeesList
        })
        const button = wrapper.findAll('v-btn')[3]
        const spy = jest.spyOn(wrapper.vm, 'editStatus')
        await button.trigger('click')
        expect(button.text()).toContain('SICK')
        expect(spy).toBeCalled()

    })
    test('edit status', () => {
        const wrapper = mount(AdminEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            employees: employeesList
        })
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.vm.editStatus(1, 'SICK')
        expect(spy).toBeCalledWith('/api/employee/admin/edit/status?employee_id=1&status=SICK')
    })
})