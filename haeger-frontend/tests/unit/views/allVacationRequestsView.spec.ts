import {mount} from "@vue/test-utils";
import AllVacationRequestsView from "@/views/allView/AllVacationRequestsView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('AllVacationRequestsView.vue', () => {
    test('trigger getPage', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
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
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/vacation_request/accounting/all?page=2&size=10")
    })
    test('approveVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({approve: false})
        const testId = 1
        wrapper.vm.approveVacation(testId)
        expect(wrapper.vm.id).toEqual(testId)
        expect(wrapper.vm.approve).toBeTruthy()
    })
    test('declineVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({reject: false})
        const testId = 1
        wrapper.vm.declineVacation(testId)
        expect(wrapper.vm.id).toEqual(testId)
        expect(wrapper.vm.reject).toBeTruthy()
    })
    test('trigger submitApproveVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(wrapper.vm, 'submitApproveVacation')
        const button = wrapper.findAll('v-btn')[1]
        button.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('trigger submitDeclineVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(wrapper.vm, 'submitDeclineVacation')
        const button = wrapper.findAll('v-btn')[3]
        button.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('submitApproveVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const testId = 1
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.setData({
            id: testId
        })
        wrapper.vm.submitApproveVacation()
        expect(spy).toHaveBeenCalledWith(`/api/vacation_request/accounting/edit?id=${testId}&status=APPROVED`)
    })
    test('submitDeclineVacation', () => {
        const wrapper = mount(AllVacationRequestsView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const testId = 1
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.setData({
            id: testId
        })
        wrapper.vm.submitDeclineVacation()
        expect(spy).toHaveBeenCalledWith(`/api/vacation_request/accounting/edit?id=${testId}&status=REJECTED`)
    })
})