import axios from '../http-instance/index'
import host from '../http-env/index'

const admin = {
  //验证登录
  verifyPassword(params) {
    return axios.post(`${host.apiHost}/admin/login/status`, params)
  }
}

export default admin
