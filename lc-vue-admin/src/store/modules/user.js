import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import {MessageBox, Message} from 'element-ui'

const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  userId:'',
  roles: [],
  dashboard:''
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar =  avatar ? avatar : "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_USER_ID: (state, userId) => {
    state.userId = userId
  },
  SET_DASHBOARD: (state, dashboard) => {
    state.dashboard = dashboard
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password,scope:'PC'}).then(response => {
        if(response.code !== 200){
          Message.error(response.msg);
          reject(error)
        }else{
          const { data } = response;
          commit('SET_TOKEN', data.access_token)
          setToken(data.access_token)
          resolve()

        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        const {name, avatar,createdAt} = data
        commit('SET_INTRODUCTION',createdAt);
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_USER_ID', data.id)

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(resp =>{
        commit('SET_TOKEN', '');
        commit('SET_ROLES', []);
        commit('SET_INTRODUCTION', '');
        commit('SET_NAME', '');
        commit('SET_USER_ID', '');
        commit('SET_DASHBOARD', '');
        removeToken()
        resetRouter()
        resolve()

      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      commit('SET_INTRODUCTION', '')
      commit('SET_USER_ID', '')

      removeToken()
      resolve()
    })
  },

  // Dynamically modify permissions
  changeRoles({ commit, dispatch }, role) {
    return new Promise(async resolve => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const { roles } = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
