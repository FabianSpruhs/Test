import {mount} from "@vue/test-utils";
import AllProjectView from "@/views/allView/AllProjectView.vue";
import {createTestingPinia} from "@pinia/testing";
import {HTTP} from "@/services/httpCommon";

describe('AllProjectView.vue', () => {
    test('trigger getPage', () => {
        const wrapper = mount(AllProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const pagination = wrapper.find('v-pagination')
        const spy = jest.spyOn(wrapper.vm, 'getPage')
        pagination.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('getPage', () => {
        const wrapper = mount(AllProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/project/accounting/all?page=2&size=10")
    })
    test('projectSuccess', () => {
        const wrapper = mount(AllProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        wrapper.setData({
            newProjectDialog: true,
            clientData: {clientName:'Client Name'}
        })
        const responseData = ({
            id: 1,
            name: 'Test Name',
            client:
                {
                    name: 'Client Name'
                }
        })
        wrapper.vm.projectSuccess(responseData)
        expect(wrapper.vm.newProjectDialog).toBeFalsy()
        expect(wrapper.vm.projects[0]).toEqual(responseData)
    })
    test('createNewProject', () => {
        const wrapper = mount(AllProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
            },
        })
        const spy = jest.spyOn(HTTP, 'post')
        wrapper.setData({
            projectName: 'TestProject',
            clientData: {id: 1}
        })
        wrapper.vm.createNewProject()
        expect(spy).toHaveBeenCalledWith('/api/project/accounting/create', {
            // @ts-ignore
            clientID: wrapper.vm.clientData.id,
            name: wrapper.vm.projektName
        })
    })
})
