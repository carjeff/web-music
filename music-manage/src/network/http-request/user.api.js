import axios from '../http-instance/index'
import host from '../http-env/index'

const user = {
  //添加用户
  addUser(data) {
    return axios.post(`${host.apiHost}/admin/user`, data)
  },
  //查询所有用户
  getAllUsers() {
    return axios.get(`${host.apiHost}/admin/users`)
  },
  //删除对应id用户
  deleteUser(params) {
    return axios.delete(`${host.apiHost}/admin/user`, params)
  },
  //更新用户
  updateUser(data) {
    return axios.put(`${host.apiHost}/admin/user`, data)
  },
  //根据username查用户
  getUserByName(params) {
    return axios.get(`${host.apiHost}/admin/users/username`, params)
  }
}

export default user
