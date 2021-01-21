/**
 * api 接口同一出口
 */

import singer from './singer.api'
import songs from './songs.api'
import user from './user.api'
import admin from './admin.api'

export default {
  singer,
  songs,
  user,
  admin
}
