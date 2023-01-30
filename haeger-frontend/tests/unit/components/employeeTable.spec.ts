import {mount} from "@vue/test-utils";
import EmployeeTable from "@/components/EmployeeTable.vue";

describe('EmployeeTable.vue', () => {
    const employeeList = [
        {
            id: 2,
            firstName: "firstName",
            lastName: "lastName",
            address: {
                street: "street",
                houseNumber: "2a",
                postcode: 99999,
                city: "berlin"
            },
            mail: "test@testen.com",
            telephoneNumber: "+49 221 333222",
            status: "ACTIVE",
            role: "STANDARD",
            scheduledVacationDays: 10
        },
        {
            id: 4,
            firstName: "nameFirst",
            lastName: "nameLast",
            address: {
                street: "Avenue",
                houseNumber: "6a",
                postcode: 99999,
                city: "Koblenz"
            },
            mail: "testen@test",
            telephoneNumber: "+49 2233 555444",
            status: "PASSIVE",
            role: "ADMIN",
            scheduledVacationDays: 20
        }
    ]
    test('render table heads', () => {
        const wrapper = mount(EmployeeTable)
        expect(wrapper.text()).toContain('Vorname')
        expect(wrapper.text()).toContain('Nachname')
        expect(wrapper.text()).toContain('Mail')
        expect(wrapper.text()).toContain('Status')
        expect(wrapper.text()).toContain('Rolle')
    })
    test('renders props.employees', () => {
        const wrapper = mount(EmployeeTable, {
            props: {employees: employeeList}
        })
        expect(wrapper.text()).toContain(employeeList[0].firstName)
        expect(wrapper.text()).toContain(employeeList[0].lastName)
        expect(wrapper.text()).toContain(employeeList[0].mail)
        expect(wrapper.text()).toContain(employeeList[0].status)
        expect(wrapper.text()).toContain(employeeList[0].role)
        expect(wrapper.text()).toContain(employeeList[1].firstName)
        expect(wrapper.text()).toContain(employeeList[1].lastName)
        expect(wrapper.text()).toContain(employeeList[1].mail)
        expect(wrapper.text()).toContain(employeeList[1].status)
        expect(wrapper.text()).toContain(employeeList[1].role)
    })
})