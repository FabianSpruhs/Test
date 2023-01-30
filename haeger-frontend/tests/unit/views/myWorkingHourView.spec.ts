import {mount} from "@vue/test-utils";
import MyWorkingHourView from "@/views/myView/MyWorkingHourView.vue";
import {HTTP} from "@/services/httpCommon";
import {createTestingPinia} from "@pinia/testing";

describe('MyWorkingHourView.vue', () => {
    const mockRoute = {
        params: {
            id: 1
        }
    }
    const mockRouter = {
        push: jest.fn()
    }
    const workingHourList = [
        {
            id: 1,
            workingHours: 2,
            workingHourStatus: 'BOOKED',
            workingDay: "2022-01-01"
        },
        {
            id: 2,
            workingHours: 3,
            workingHourStatus: 'FINALIZED',
            workingDay: "2022-01-02"
        }
    ]
    test('getPage', () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/working_hour/employee/1")
    })
    test('trigger getPage', () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const pagination = wrapper.find('v-pagination')
        const spy = jest.spyOn(wrapper.vm, 'getPage')
        pagination.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('created', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(MyWorkingHourView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        expect(spy).toBeCalledWith('/api/working_hour/employee/1')
        expect(spy).toBeCalledWith('/api/working_hour/account/1')
    })
    test('book WorkingHour', () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const spy = jest.spyOn(HTTP, 'post')
        const date = '2022-01-01'
        const hours = 1
        wrapper.vm.bookWorkingHour(date, hours)
        expect(spy).toBeCalledWith('/api/working_hour/book', {employeeID: 0, workingDay: date, workingHours: hours})
    })
    test('finalize WorkingHour', () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.vm.finalizeWorkingHours()
        expect(spy).toBeCalledWith('/api/working_hour/finalize/0')
    })
    test('computed bookedWorkingHours', async () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        await wrapper.setData({myWorkingHours: workingHourList})
        expect(wrapper.vm.bookedWorkingHours).toEqual([workingHourList[0]])
    })
    test('computed finalizedWorkingHours', async () => {
        const wrapper = mount(MyWorkingHourView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        await wrapper.setData({myWorkingHours: workingHourList})
        expect(wrapper.vm.finalizedWorkingHours).toEqual([workingHourList[1]])
    })
})