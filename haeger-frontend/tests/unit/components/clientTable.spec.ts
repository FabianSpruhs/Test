import {shallowMount} from "@vue/test-utils";
import ClientTable from "@/components/ClientTable.vue";

describe('ClientTable.vue', () => {
    it('renders props.clients when past', () => {
        const clients = [
            {
                id: 1,
                name: "client",
                address: {
                    street: "test street",
                    houseNumber: "1a",
                    postcode: 99999,
                    city: "bonn"
                },
                contactEmployee: {
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
            },
            {
                id: 3,
                name: "test client",
                address: {
                    street: "road",
                    houseNumber: "4a",
                    postcode: 99999,
                    city: "Hamburg"
                },
                contactEmployee: {
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
            }
        ]
        const wrapper = shallowMount(ClientTable, {
            props: { clients }
        })
        expect(wrapper.text()).toContain(clients[0].name)
        expect(wrapper.text()).toContain(clients[1].name)
        expect(wrapper.text()).toContain(clients[0].contactEmployee.firstName + " " + clients[0].contactEmployee.lastName)
        expect(wrapper.text()).toContain(clients[1].contactEmployee.firstName + " " + clients[1].contactEmployee.lastName)
    })
    it('renders tableHeads', () => {
        const wrapper = shallowMount(ClientTable)
        expect(wrapper.text()).toContain('Kundenname')
        expect(wrapper.text()).toContain('Zuständiger Mitarbeiter')
    })
})