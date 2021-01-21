import Vue from 'vue'
import VueRouter from 'vue-router'

const Index = () => import('@/views/index')

const SingerManage = () => import('@/components/SingerManage')
const UserManage = () => import('@/components/UserManage')

const SongManage = () => import('@/components/SongManage')
const Login = () => import('@/components/Login')
const Home = () => import('@/components/Home')

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login,
    meta: { title: '登录' }
  },
  {
    path: '/dashboard',
    component: Index,
    children: [
      {
        path: 'home',
        component: Home,
        meta: { title: '首页' }
      },
      {
        path: 'singer',
        component: SingerManage,
        meta: { title: '歌手管理' }
      },
      {
        path: 'user',
        component: UserManage,
        meta: { title: '用户管理' }
      },
      {
        path: 'songs',
        component: SongManage,
        meta: { title: '歌曲管理' }
      }
    ]
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
