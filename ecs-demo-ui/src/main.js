import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'

import axios from 'axios'

import Router from 'vue-router'
import router from './router'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(Router)

new Vue({
  render: h => h(App),
  router:router
}).$mount('#app')
