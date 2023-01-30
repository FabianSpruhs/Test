import {mount} from "@vue/test-utils";
import AllSickNoteTable from "@/components/AllSickNoteTable.vue";

describe('AllSickNoteTable.vue', () => {
    it('render table Heads', () => {
        const wrapper = mount(AllSickNoteTable.vue)
        expect(wrapper.text()).toContain('Vorname')
        expect(wrapper.text()).toContain('Nachname')
        expect(wrapper.text()).toContain('Krank von')
        expect(wrapper.text()).toContain('Krank bis')
    })
    it('render props.sickNotes', () => {
        const sickNotes = [
            {
                id: 1,
                beginDate: '2022-01-11',
                endDate: '2022-01-12',
                employee: {
                    firstName: 'Test 1',
                    lastName: 'Test 2'
                }
            },
            {
                id: 1,
                beginDate: '2022-01-11',
                endDate: '2022-01-12',
                employee: {
                    firstName: 'Test 1',
                    lastName: 'Test 2'
                }
            }
        ]
        const wrapper = mount(AllSickNoteTable, {
            props: {sickNotes}
        })
        expect(wrapper.text()).toContain(sickNotes[0].endDate)
        expect(wrapper.text()).toContain(sickNotes[0].beginDate)
        expect(wrapper.text()).toContain(sickNotes[0].employee.lastName)
        expect(wrapper.text()).toContain(sickNotes[0].employee.firstName)
        expect(wrapper.text()).toContain(sickNotes[1].endDate)
        expect(wrapper.text()).toContain(sickNotes[1].beginDate)
        expect(wrapper.text()).toContain(sickNotes[1].employee.lastName)
        expect(wrapper.text()).toContain(sickNotes[1].employee.firstName)
    })
})
