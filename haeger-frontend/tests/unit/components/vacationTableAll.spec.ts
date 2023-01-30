import {mount} from "@vue/test-utils";
import VacationTableAll from "@/components/VacationTableAll.vue";

describe('VacationTableAll.vue', () => {
    const vacationList = [
        {
            id: 10,
            beginDate: "2022-01-01",
            endDate: "2022-01-02",
            vacationDays: "2",
            status: "OPEN",
            employee: {
                id: 1,
                firstName: 'FirstName',
                lastName: 'LastName'
            }
        },
        {
            id: 20,
            beginDate: "2022-01-03",
            endDate: "2022-01-05",
            vacationDays: "3",
            status: "CONFIRMED",
            employee: {
                id: 2,
                firstName: 'Test First',
                lastName: 'Test Last'
            }
        }
    ]
    test('render table heads', () => {
        const wrapper = mount(VacationTableAll)
        expect(wrapper.text()).toContain('Mitarbeiter')
        expect(wrapper.text()).toContain('Urlaub von')
        expect(wrapper.text()).toContain('Urlaub bis')
        expect(wrapper.text()).toContain('Urlaubstage')
        expect(wrapper.text()).toContain('Status')
    })
    test('render props.vacationRequests', () => {
        const wrapper = mount(VacationTableAll, {
            props: {vacationRequests: vacationList}
        })
        expect(wrapper.text()).toContain(vacationList[0].beginDate)
        expect(wrapper.text()).toContain(vacationList[0].endDate)
        expect(wrapper.text()).toContain(vacationList[0].status)
        expect(wrapper.text()).toContain(`${vacationList[0].employee.firstName} ${vacationList[0].employee.lastName}`)
        expect(wrapper.text()).toContain(vacationList[0].vacationDays)
        expect(wrapper.text()).toContain(vacationList[1].beginDate)
        expect(wrapper.text()).toContain(vacationList[1].vacationDays)
        expect(wrapper.text()).toContain(vacationList[1].endDate)
        expect(wrapper.text()).toContain(`${vacationList[1].employee.firstName} ${vacationList[1].employee.lastName}`)
        expect(wrapper.text()).toContain(vacationList[1].status)
    })
    test('emits approveVacation', () => {
        const wrapper = mount(VacationTableAll, {
            props: {vacationRequests: vacationList}
        })
        const buttons = wrapper.findAll('v-btn')
        buttons[0].trigger('click')
        expect(wrapper.emitted('approveVacation')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('approveVacation')[0][0]).toEqual(vacationList[0].id)
    })
    test('emits declineVacation', () => {
        const wrapper = mount(VacationTableAll, {
            props: {vacationRequests: vacationList}
        })
        const buttons = wrapper.findAll('v-btn')
        buttons[1].trigger('click')
        expect(wrapper.emitted('declineVacation')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('declineVacation')[0][0]).toEqual(vacationList[0].id)
    })
})