import {mount} from "@vue/test-utils";
import AllEmployeeView from "@/views/allView/AllEmployeeView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('AllEmployeeView.vue', () => {
    test('trigger getPage', () => {
        const wrapper = mount(AllEmployeeView.vue, {
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
        const wrapper = mount(AllEmployeeView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/employee/accounting/all?page=2&size=10")
    })
})