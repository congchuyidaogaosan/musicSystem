import Vue from 'vue'
import Vuex from 'vuex'
import router from '@/router'
import Axios from 'axios'
import createPersistedState from 'vuex-persistedstate'
import BaseInfoStore from './baseInfo'
Vue.use(Vuex)
const store = new Vuex.Store({
  modules: {
    baseInfoStore: BaseInfoStore
  },
  plugins: [createPersistedState({
      storage: window.sessionStorage
  })]
})

store.registerModule('pageSwitch', {
  state: {
    isLoading: false
  },
  mutations: {
    updateLoadingStatus (state, payload) {
      state.isLoading = payload.isLoading
    }
  }
})

router.beforeEach(function (to, from, next) {
  if (to.meta.title) {
    document.title = to.meta.title
  }
  store.commit('updateLoadingStatus', { isLoading: true })
  next()
})

router.afterEach(function (to) {
  store.commit('updateLoadingStatus', { isLoading: false })
})

store.registerModule('ajaxSwitch', {
  state: {
    ajaxIsLoading: false
  },
  mutations: {
    ajaxStar (state) {
      state.ajaxIsLoading = true
    },
    ajaxEnd (state) {
      state.ajaxIsLoading = false
    }
  },
  getter: {
    ajaxIsLoading: state => state.ajaxIsLoading
  }
})

Axios.interceptors.request.use(config => {
  store.commit('ajaxStar')
  return config;
})

Axios.interceptors.response.use(config => {
  if (config.data.code) {
    if (config.data.code == 200) {
      // let resInfo = {
      //   code: 0,
      //   msg: ''
      // }
    } else {
      let resInfo = {
        code: config.data.code,
        message: config.data.msg
      }
      store.commit('setResponse', resInfo)
    }
  }
  store.commit('ajaxEnd')
  return config
},
error => {
  console.log(error.response);
  if (error.response != undefined) {
    if (error.response.status != 200) {
      let resInfo = {
        code: error.response.status,
        message: '服务器繁忙，请稍后再试！'
      }
      store.commit('setResponse', resInfo)
    }
  }
  store.commit('ajaxEnd')
  return error;
})

export default store;
