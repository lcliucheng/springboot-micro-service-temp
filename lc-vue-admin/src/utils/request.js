import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'


// create an axios instancerelativeURL

axios.defaults.baseURL = process.env.VUE_APP_BASE_API

// request interceptor
axios.interceptors.request.use(
  config => {
    // Do something before request is sent
    if (store.getters.token) {
      // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
axios.interceptors.response.use(
  /**
   * If you want to get information such as headers or status
   * Please return  response => response
   */
  /**
   * 下面的注释为通过在response里，自定义code来标示请求状态
   * 当code返回如下情况则说明权限有问题，登出并返回到登录页
   * 如想通过 XMLHttpRequest 来状态码标识 逻辑可写在下面error中
   * 以下代码均为样例，请结合自生需求加以修改，若不需要，则可删除
   */
  response => {
    if(response.data.code != 200){
      redpDeal(response.data);
      return response.data
    }else{
      return response.data
    }
  },
  error => {
    if(error.response){
      redpDeal(error.response.data);
    }else{
      Message({message:'服务器超时！', type: 'error', duration:4000});
    }
    return Promise.reject(error)
  }
)


function redpDeal(res) {
  if (res.code === 401 || res.code === -601 || res.code === -602) {
    MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      store.dispatch('user/resetToken').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    })
  }else if(res.code === 403){
    MessageBox.alert('您的权限不足，请联系管理员开通权限！', '权限不足', {
      confirmButtonText: '确定',
      type: 'warning'
    });
  }
}
