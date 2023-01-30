import {mount} from "@vue/test-utils";
import AllWorkingHoursView from "@/views/allView/AllWorkingHoursView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('AllWorkingHoursView.vue', () => {
    test('trigger getPage', () => {
        const wrapper = mount(AllWorkingHoursView.vue, {
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
        const wrapper = mount(AllWorkingHoursView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/working_hour/accounting/finalize/all?page=2&size=10")
    })
    test('trigger created', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(AllWorkingHoursView, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        expect(spy).toBeCalledWith('/api/working_hour/accounting/finalize/all?page=0&size=10')
        expect(spy).toBeCalledWith('/api/working_hour/accounting/finalize/all?page=0&size=10')
    })
    test('watch workingHoursAccount', () => {
        const wrapper = mount(AllWorkingHoursView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const workingHourAccountingTest = [
            {
                workingHour: 1,
                employeeJPA: {
                    firstName: 'Test First',
                    lastName: 'Test Last'
                }
            },
            {
                workingHour: 2,
                employeeJPA: {
                    firstName: 'First',
                    lastName: 'Last'
                }
            }
        ]
        wrapper.setData({workingHoursAccount: workingHourAccountingTest})
        // @ts-ignore
        wrapper.vm.$options.watch.workingHoursAccount.call(wrapper.vm)
        expect(wrapper.vm.workingHoursAccountings).toEqual([[
            workingHourAccountingTest[0].employeeJPA.firstName,
            workingHourAccountingTest[0].employeeJPA.lastName,
            workingHourAccountingTest[0].workingHour
        ], [
            workingHourAccountingTest[1].employeeJPA.firstName,
            workingHourAccountingTest[1].employeeJPA.lastName,
            workingHourAccountingTest[1].workingHour
        ]])


    })
})