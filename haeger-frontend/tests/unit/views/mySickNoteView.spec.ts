import {mount} from "@vue/test-utils";
import MySickNoteView from "@/views/myView/MySickNoteView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('MySickNoteView.vue', () => {
    const begin = '2022-01-01'
    const end = '2022-01-02'
    test('double Date input', () => {
        const wrapper = mount(MySickNoteView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.vm.doubleDateInput({beginDate: begin, endDate: end})
        expect(wrapper.vm.beginDate).toContain(begin)
        expect(wrapper.vm.endDate).toContain(end)
    })
    test('on Submit', () => {
        const wrapper = mount(MySickNoteView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, 'post')
        wrapper.setData({
            beginDate: begin,
            endDate: end,
            form: true
        })
        wrapper.vm.onSubmit()
        expect(spy).toBeCalled()
        expect(spy).toBeCalledWith('/api/sick_note/submit', {beginDate: begin, endDate: end, employeeId: 0})

    })
})
