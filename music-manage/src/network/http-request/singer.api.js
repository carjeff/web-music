import axios from '../http-instance/index'
import host from '../http-env/index'

const singer = {
  // 添加歌手
  addSinger(data) {
    return axios.post(`${host.apiHost}/admin/singer`, data)
  },
  // 更新歌手
  updateSinger(data) {
    return axios.put(`${host.apiHost}/admin/singer`, data)
  },
  // 删除歌手
  deleteSinger(data) {
    return axios.delete(`${host.apiHost}/admin/singer`, { data })
  },
  // 根据主键查询歌手
  getSingerById(params) {
    return axios.get(`${host.apiHost}/admin/singer`, params)
  },
  // 获取所有歌手
  getAllSinger() {
    return axios.get(`${host.apiHost}/admin/singers`)
  },
  // 根据歌手姓名查询歌手
  getSingerByName(params) {
    return axios.get(`${host.apiHost}/admin/singers/name`, params)
  },
  // 根据歌手性别查询歌手
  getSingerBySex(params) {
    return axios.get(`${host.apiHost}/admin/singers/sex`, params)
  }
}

export default singer
