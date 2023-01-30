import {mount} from "@vue/test-utils";
import WorkingHourTable from "@/components/WorkingHourTable.vue";
import {HTTP} from "@/services/httpCommon";
import {createTestingPinia} from "@pinia/testing";

describe('WorkingHourTable.vue', () => {
    const workingHourList = [
        {
            id: 1,
            workingHours: 2,
            workingHourStatus: 'OPEN',
            workingDay: "2022-01-01"
        },
        {
            id: 2,
            workingHours: 3,
            workingHourStatus: 'OPEN',
            workingDay: "2022-01-02"
        }
    ]
    test('render table heads', () => {
        const mapper = mount(WorkingHourTable)
        expect(mapper.text()).toContain('Arbeitstag')
        expect(mapper.text()).toContain('Arbeitsstunde')
        expect(mapper.text()).toContain('Status')
    })
    test('render props.myWorkingHours', () => {
        const mapper = mount(WorkingHourTable, {
            props: {myWorkingHours: workingHourList}
        })
        expect(mapper.text()).toContain(workingHourList[0].workingHours.toString())
        expect(mapper.text()).toContain(workingHourList[0].workingHourStatus)
        expect(mapper.text()).toContain(workingHourList[0].workingDay)
        expect(mapper.text()).toContain(workingHourList[1].workingHours.toString())
        expect(mapper.text()).toContain(workingHourList[1].workingHourStatus)
        expect(mapper.text()).toContain(workingHourList[1].workingDay)
    })
    test('render props.booked true' , () => {
        const mapper = mount(WorkingHourTable, {
            props: {myWorkingHours: workingHourList, booked: true}
        })
        const button = mapper.find('v-btn')
        expect(button.attributes('icon')).toContain('mdi-pencil-outline')
    })
    test('render props.booked false', () => {
        const mapper = mount(WorkingHourTable, {
            props: {myWorkingHours: workingHourList}
        })
        const button = mapper.find('v-btn')
        expect(button.text()).toContain('Abbrechen')
    })
    test('trigger openEditDialog', () => {
        const wrapper = mount(WorkingHourTable.vue, {
            props: {myWorkingHours: workingHourList, booked: true}
        })
        const button = wrapper.find('v-btn')
        const spy = jest.spyOn(wrapper.vm, 'openEditDialog')
        button.trigger('click')
        expect(spy).toHaveBeenCalledWith(workingHourList[0].id, workingHourList[0].workingDay, workingHourList[0].workingHours)
    })
    test('openEditDialog', () => {
        const wrapper = mount(WorkingHourTable.vue)
        wrapper.setData({
            editDialog: false
        })
        wrapper.vm.openEditDialog(workingHourList[0].id.toString(), workingHourList[0].workingDay, workingHourList[0].workingHours.toString())
        expect(wrapper.vm.editDialog).toBeTruthy()
        expect(wrapper.vm.idToEdit).toContain(workingHourList[0].id.toString())
        expect(wrapper.vm.dateToEdit).toContain(workingHourList[0].workingDay)
        expect(wrapper.vm.hoursToEdit).toContain(workingHourList[0].workingHours.toString())
    })
    test('trigger editWorkingHour', () => {
        const wrapper = mount(WorkingHourTable.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const button = wrapper.findAll('v-btn')[1]
        const spy = jest.spyOn(wrapper.vm, 'editWorkingHour')
        button.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('editWorkingHour', () => {
        const wrapper = mount(WorkingHourTable.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, 'put')
        wrapper.setData({
            dateToEdit: workingHourList[0].workingDay,
            hoursToEdit: workingHourList[0].workingHours,
            idToEdit: workingHourList[0].id,
            editDialog: true
        })
        wrapper.vm.editWorkingHour("2022-01-01", 2)
        expect(spy).toHaveBeenCalledWith(`/api/working_hour/edit/${workingHourList[0].id}`, {
            workingDay: workingHourList[0].workingDay,
            workingHours:workingHourList[0].workingHours
        })

    })
})