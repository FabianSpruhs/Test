import {mount} from "@vue/test-utils";
import SingleDateInputField from "@/components/SingleDateInputField.vue";

describe('SingleDateInputField.vue', () => {
    test('render props.singleLable', () => {
        const testLable = 'Test Lable'
        const wrapper = mount(SingleDateInputField, {
            props: {singleLable: testLable}
        })
        const textField = wrapper.find('v-text-field')
        expect(textField.attributes('label')).toContain(testLable)
    })
    test('trigger handleDateInput', () => {
        const wrapper = mount(SingleDateInputField.vue)
        const textField = wrapper.find('v-text-field')
        const spy = jest.spyOn(wrapper.vm, "handleDateInput")
        textField.trigger('input')
        expect(spy).toHaveBeenCalled()
    })
    test('emit singleDateInput', () => {
        const testDate = "2022-01-01"
        const wrapper = mount(SingleDateInputField.vue)
        wrapper.vm.singleDate = testDate
        wrapper.vm.handleDateInput()
        expect(wrapper.emitted('singleDateInput')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('singleDateInput')[0][0]).toEqual(testDate)
    })
})