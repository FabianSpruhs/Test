import {mount} from "@vue/test-utils";
import ContactEmployeeAutoComplete from "@/components/ContactEmployeeAutoComplete.vue";
import {createTestingPinia} from "@pinia/testing";

describe('ContactEmployeeAutoComplete.vue', () => {
    const employeeList = [
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
        },
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
        }
    ]
    test('render props.lableText', () => {
        const testString = 'Test Lable'
        const wrapper = mount(ContactEmployeeAutoComplete, {
            props: {lableText: testString},
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const autoComplete = wrapper.find('v-autocomplete')
        expect(autoComplete.attributes('label')).toContain(testString)
    })
    test('computed returnID', () => {
        const wrapper = mount(ContactEmployeeAutoComplete.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({employees: employeeList, name: `${employeeList[0].firstName} ${employeeList[0].lastName}`})
        expect(wrapper.vm.returnID).toEqual({
            name: `${employeeList[0].firstName} ${employeeList[0].lastName}`,
            id: employeeList[0].id
        })
    })
    test('watch employees', () => {
        const wrapper = mount(ContactEmployeeAutoComplete.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({employees: employeeList, name: `${employeeList[0].firstName} ${employeeList[0].lastName}`})
        // @ts-ignore
        wrapper.vm.$options.watch.employees.call(wrapper.vm)
        expect(wrapper.vm.employeesView).toEqual([
            `${employeeList[0].firstName} ${employeeList[0].lastName}`,
            `${employeeList[1].firstName} ${employeeList[1].lastName}`
        ])
    })
    test('watch name', () => {
        const wrapper = mount(ContactEmployeeAutoComplete, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({employees: employeeList, name: `${employeeList[0].firstName} ${employeeList[0].lastName}`})
        // @ts-ignore
        wrapper.vm.$options.watch.name.call(wrapper.vm)
        expect(wrapper.emitted('update:modelValue')).toBeTruthy()
        // @ts-ignore
        expect(wrapper.emitted('update:modelValue')[0][0]).toEqual({
            name: `${employeeList[0].firstName} ${employeeList[0].lastName}`,
            id: employeeList[0].id
        })
    })
})