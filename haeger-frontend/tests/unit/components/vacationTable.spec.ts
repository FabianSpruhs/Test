import {mount} from "@vue/test-utils";
import VacationTable from "@/components/VacationTable.vue";

describe('VacationTable.vue', () => {
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
    test('render table heads', () => {
        const wrapper = mount(VacationTable)
        expect(wrapper.text()).toContain('Urlaub von')
        expect(wrapper.text()).toContain('Urlaub bis')
        expect(wrapper.text()).toContain('Urlaubstage')
        expect(wrapper.text()).toContain('Status')
    })
    test('render props.myVacationRequests', () => {
        const wrapper = mount(VacationTable.vue, {
            props: {myVacationRequests: vacationList}
        })
        expect(wrapper.text()).toContain(vacationList[0].beginDate)
        expect(wrapper.text()).toContain(vacationList[0].vacationDays)
        expect(wrapper.text()).toContain(vacationList[0].endDate)
        expect(wrapper.text()).toContain(vacationList[0].status)
        expect(wrapper.text()).toContain(vacationList[1].beginDate)
        expect(wrapper.text()).toContain(vacationList[1].vacationDays)
        expect(wrapper.text()).toContain(vacationList[1].endDate)
        expect(wrapper.text()).toContain(vacationList[1].status)
    })
})