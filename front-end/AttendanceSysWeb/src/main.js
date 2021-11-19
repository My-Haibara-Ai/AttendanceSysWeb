import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
// 导入字体图标
import './assets/fonts/iconfont.css'
//导入axios
import axios from 'axios'
// 导入 NProgress 包对应的JS和CSS
import NProgress from 'nprogress'


// 配置请求的跟路径
axios.defaults.baseURL = "http://localhost:8080"
// 在 request 拦截器中，展示进度条 NProgress.start()
axios.interceptors.request.use(config => {
  // console.log(config)
  NProgress.start()
  config.headers.Authorization = window.sessionStorage.getItem('token')
  // 在最后必须 return config
  return config
})
// 在 response 拦截器中，隐藏进度条 NProgress.done()
axios.interceptors.response.use(config => {
  NProgress.done()
  return config
})
Vue.prototype.$http = axios
Vue.config.productionTip = false



new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
