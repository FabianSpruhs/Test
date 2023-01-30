import {mount} from "@vue/test-utils";
import MyProjectView from "@/views/myView/MyProjectView.vue";
import {HTTP} from "@/services/httpCommon";
import {createTestingPinia} from "@pinia/testing";

describe('MyProjectView.vue', () => {
    const mockRoute = {
        params: {
            id: 1
        }
    }
    const mockRouter = {
        push: jest.fn()
    }
    test('get my projects', () => {
        const spy = jest.spyOn(HTTP, 'get')
        mount(MyProjectView, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        expect(spy).toBeCalled()
        expect(spy).toBeCalledWith('/api/project/employee/1?page=0&size=10')
    })
    test('trigger getPage', () => {
        const wrapper = mount(MyProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const pagination = wrapper.find('v-pagination')
        const spy = jest.spyOn(wrapper.vm, 'getPage')
        pagination.trigger('click')
        expect(spy).toHaveBeenCalled()
    })
    test('getPage', () => {
        const wrapper = mount(MyProjectView.vue, {
            global: {
                plugins: [createTestingPinia()],
                mocks: {
                    $route: mockRoute,
                    $router: mockRouter
                }
            },
        })
        const spy = jest.spyOn(HTTP, "get")
        wrapper.setData({page: 3})
        wrapper.vm.getPage()
        expect(spy).toHaveBeenCalledWith("/api/project/employee/1?page=2&size=10")
    })
})