import {mount} from "@vue/test-utils";
import FinalizedWorkingHourTable from "@/components/FinalizedWorkingHourTable.vue";

describe('FinalizedWorkingHourTable.vue', () => {
    const workingHourList = [
        {
            workingDay: '2022-01-01',
            workingHours: '3',
            employee: {
                firstName: 'Test Firstname',
                lastName: 'Test Lastname'
            }
        },
        {
            workingDay: '2022-01-02',
            workingHours: '4',
            employee: {
                firstName: 'Firstname',
                lastName: 'Lastname'
            }
        }
    ]
    test('render table heads', () => {
        const wrapper = mount(FinalizedWorkingHourTable)
        expect(wrapper.text()).toContain('Vorname')
        expect(wrapper.text()).toContain('Nachname')
        expect(wrapper.text()).toContain('Arbeitstag')
        expect(wrapper.text()).toContain('Arbeitsstunde')
    })
    test('render props.workingHours', () => {
        const wrapper = mount(FinalizedWorkingHourTable, {
            props: {workingHours: workingHourList}
        })
        expect(wrapper.text()).toContain(workingHourList[0].workingDay)
        expect(wrapper.text()).toContain(workingHourList[0].workingHours)
        expect(wrapper.text()).toContain(workingHourList[0].employee.firstName)
        expect(wrapper.text()).toContain(workingHourList[0].employee.lastName)
        expect(wrapper.text()).toContain(workingHourList[1].workingDay)
        expect(wrapper.text()).toContain(workingHourList[1].workingHours)
        expect(wrapper.text()).toContain(workingHourList[1].employee.firstName)
        expect(wrapper.text()).toContain(workingHourList[1].employee.lastName)
    })
})