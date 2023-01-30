import {mount} from "@vue/test-utils";
import MyVacationView from "@/views/myView/MyVacationView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('MyVacationView.vue', () => {
    const vacationList = [
        {
            beginDate: "2022-01-01",
            endDate: "2022-01-02",
            vacationDays: "2",
            status: "OPEN"
        },
        {
            beginDate: "2022-01-03",
            endDate: "2022-01-05",
            vacationDays: "3",
            status: "CONFIRMED"
        }
    ]
    const begin = '2022-01-01'
    const end = '2022-01-02'
    const mockRoute = {
        params: {
            id: 1
        }
    }
    const mockRouter = {
        push: jest.fn()
    }
    test('trigger getPage', () => {
        const wrapper = mount(MyVacationView.vue, {
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
    test('getPage', () => {
        const wrapper = mount(MyVacationView.vue, {
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
        expect(spy).toHaveBeenCalledWith("/api/vacation_request/employee/1?page=2&size=10")
    })
    test('double Date input', () => {
        const wrapper = mount(MyVacationView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        wrapper.vm.doubleDateInput({beginDate: begin, endDate: end})
        expect(wrapper.vm.beginDate).toContain(begin)
        expect(wrapper.vm.endDate).toContain(end)
    })
    test('created', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(MyVacationView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })

        expect(spy).toBeCalledWith('/api/vacation_request/employee/1?page=0&size=10')
        expect(spy).toBeCalledWith('/api/employee/remaining_vacation/1')
    })
    test('render Table Heads', () => {
        const wrapper = mount(MyVacationView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        expect(wrapper.text()).toContain('Urlaub von')
        expect(wrapper.text()).toContain('Urlaub bis')
        expect(wrapper.text()).toContain('Urlaubstage')
        expect(wrapper.text()).toContain('Status')

    })
    test('render myVacations', async () => {
        const wrapper = mount(MyVacationView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        await wrapper.setData({
            myVacationRequests: vacationList
        })
        expect(wrapper.text()).toContain(vacationList[0].beginDate)
        expect(wrapper.text()).toContain(vacationList[0].endDate)
        expect(wrapper.text()).toContain(vacationList[0].vacationDays)
        expect(wrapper.text()).toContain(vacationList[0].status)
        expect(wrapper.text()).toContain(vacationList[1].beginDate)
        expect(wrapper.text()).toContain(vacationList[1].endDate)
        expect(wrapper.text()).toContain(vacationList[1].vacationDays)
        expect(wrapper.text()).toContain(vacationList[1].status)

    })
    test('onSubmit', () => {
        const wrapper = mount(MyVacationView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const spy = jest.spyOn(HTTP, 'post')
        wrapper.setData({
            beginDate: begin,
            endDate: end,
            form: true
        })
        wrapper.vm.onSubmit()
        expect(spy).toBeCalledWith('/api/vacation_request/request', {beginDate: begin, endDate: end, employeeID: 0})
    })
})