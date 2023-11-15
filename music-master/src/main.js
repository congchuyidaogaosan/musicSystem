import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/scss/element-variables.scss'
import axios from 'axios'
import UrlConfig from '@/assets/js/UrlConfig.js'
Vue.config.productionTip = false
axios.defaults.withCredentials = true
Vue.use(ElementUI)
Vue.prototype.$http = axios
Vue.use(UrlConfig)
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
