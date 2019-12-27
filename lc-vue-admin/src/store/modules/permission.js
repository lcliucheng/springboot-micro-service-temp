import { asyncRoutes, constantRoutes } from '@/router'
import { getFunction,getDicts } from '@/api/role'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: [],
  asyncRoutes:[],
  sourceRoute:[],
  dictAll:[],
  apkMap:{},
  channelList:{}
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  },
  SET_SOURCES_ROUTES: (state, sourceRoute) => {
    state.sourceRoute = sourceRoute
  },
  SET_DICTS: (state,dicts) => {
    state.dictAll = dicts
  },
  SET_APKS: (state,apks) => {
    state.apkMap = apks
  },
  SET_CHANNEL: (state,channel) => {
    state.channelList = channel
  }

}

const actions = {
   generateRoutes({ commit }, roles) {
    return new Promise( async resolve => {
      let backRoutes = await asyncRoutes();
      commit('SET_ROUTES', backRoutes)
      resolve(backRoutes)
    })
  },
  setRoutes({ commit }, roles) {
    return new Promise( async resolve => {
      getFunction().then(response => {
        commit('SET_SOURCES_ROUTES', response.data);
        resolve(response.data)
      })
    })
  },
  setDicts({ commit }) {
    return new Promise( async resolve => {
      getDicts().then(response => {

        commit('SET_DICTS', response.data);
        resolve()
      })
    })
  },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
