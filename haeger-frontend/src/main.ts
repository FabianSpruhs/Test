import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import router from './router'
import { createPinia } from 'pinia'
import axios from 'axios'
import VueAxios from 'vue-axios'
import piniaPluginPersistedState from "pinia-plugin-persistedstate"

loadFonts()

createApp(App).use(createPinia().use(piniaPluginPersistedState)).use(router).use(router)
  .use(vuetify).use(VueAxios, axios)
  .mount('#app')
