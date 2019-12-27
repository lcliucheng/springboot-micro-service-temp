import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import Element from 'element-ui'
import './styles/element-variables.scss'

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import i18n from './lang' // Internationalization
import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log
import { getToken } from '@/utils/auth'

import * as filters from './filters' // global filters
import axios from 'axios'
import './utils/request'
import Qs from 'qs';
import checkPermission from './utils/permission'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import {PAGE_SIZE} from './utils/constant'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.snow.css'

//Vue.use(Viewer) 默认配置写法
Vue.use(Viewer, {
  defaultOptions: {
    zIndex: 9999
  }
})



Vue.prototype.checkPermission = checkPermission
Vue.prototype.$axios = axios
Vue.prototype.Qs = Qs
Vue.prototype.PAGE_SIZES = PAGE_SIZE

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})

Vue.use(VueQuillEditor)
