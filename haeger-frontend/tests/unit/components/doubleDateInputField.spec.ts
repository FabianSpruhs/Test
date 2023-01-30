import {mount} from "@vue/test-utils";
import DoubleDateInputField from "@/components/DoubleDateInputField.vue";

describe('DoubleDateInputField.vue', () => {
    test('render props.firstLable and props.secondLable', () => {
        const wrapper = mount(DoubleDateInputField.vue, {
            props: {
                firstLable: 'firstLable',
                secondLable: 'secondLable'
            }
        })
        const textFields = wrapper.findAll('v-text-field')
        expect(textFields[0].attributes('label')).toContain('firstLable')
        expect(textFields[1].attributes('label')).toContain('secondLable')
    })
    test('emit doubleDateInput', () => {
        const emitObject = {
            beginDate: '2022-01-01',
            endDate: '2022-01-02'
        }
        const wrapper = mount(DoubleDateInputField.vue)
        wrapper.setData(emitObject)
        wrapper.vm.handleDateInput()
        expect(wrapper.emitted('doubleDateInput')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('doubleDateInput')[0][0]).toEqual(emitObject)
    })
    test('handleDateInput', () => {
        const wrapper = mount(DoubleDateInputField.vue)
        const textFields = wrapper.findAll('v-text-field')
        const spy = jest.spyOn(wrapper.vm, 'handleDateInput')
        textFields[0].trigger('input')
        textFields[1].trigger('input')
        expect(spy).toHaveBeenCalledTimes(2)
    })
})