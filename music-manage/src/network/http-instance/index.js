import axios from 'axios'

// 允许跨域
axios.defaults.withCredentials = true

var instance = axios.create({
  // 默认方法设置
  method: 'POST',
  //返回数据的格式
  //其可选项是arraybuffer,blob,document,json,text,stream
  responseType: 'json',
  xsrfCookieName: 'XSRF-TOKEN',
  // 返回数据设置，有data和headers两个参数
  transformRequest: [
    function(data) {
      return data
    }
  ],
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  },
  // 请求超时设置
  timeout: 30000
})

// response拦截器处理
instance.interceptors.response.use(
  response => {
    if (response.status === 200) {
      return response.data
    } else {
      return Promise.reject(response)
    }
  },
  error => {
    return Promise.reject(error)
  }
)

//reuest拦截器处理
instance.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

export default instance
