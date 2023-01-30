import {mount} from "@vue/test-utils";
import MainMenu from "@/components/MainMenu.vue";

describe('MainMenu.vue', () => {
    test('render props.items', () => {
        const itemList = [
            {
                title: "Test Item" , text: "Test Text", icon: "mdi-test", path: `/test`
            },
            {
                title: "Item" , text: "Text", icon: "mdi-tests", path: `/test/test`
            }
        ]
        const wrapper = mount(MainMenu, {
            props: {items: itemList}
        })
        const cards = wrapper.findAll('v-card')
        expect(cards[0].attributes('title')).toContain(itemList[0].title)
        expect(cards[0].attributes('text')).toContain(itemList[0].text)
        expect(cards[0].attributes('prepend-icon')).toContain(itemList[0].icon)
        expect(cards[0].attributes('to')).toContain(itemList[0].path)
        expect(cards[1].attributes('title')).toContain(itemList[1].title)
        expect(cards[1].attributes('text')).toContain(itemList[1].text)
        expect(cards[1].attributes('prepend-icon')).toContain(itemList[1].icon)
        expect(cards[1].attributes('to')).toContain(itemList[1].path)
    })
})