import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import store from "../store";

/* Router Modules */

export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/icon',
    component: () => import('@/views/svg-icons/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {title: 'dashboard', icon: 'dashboard', noCache: true, affix: true}
      }
    ]
  }

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */

export async function  asyncRoutes() {

  const asyncRoutes = [
    // systemRouter,
    {path: '*', redirect: '/404', hidden: true}
  ]
  await axios.get('/users/getUserMenus').then(response => {
    let list = response.data;
    for (let i = 0; i < list.length; i++) {
      let route = createRoute(list[i]);
      asyncRoutes.push(route)
    }


  });

  return asyncRoutes;
}

function createRoute(router) {
  let route;
  if (router.children != null && router.children.length !== 0) {
    let children = [];
    for (let j = 0; j < router.children.length; j++) {
      children.push(createRoute(router.children[j]));
    }
    route = getRoute(router, children);
  } else {
    route = getRoute(router, []);
  }
  return route;
}

function getRoute(item, children) {

  let icon = '';
  let type = '';
  if(item.icon.indexOf("@") != -1){
    icon = item.icon.split("@")[0];
    type = item.icon.split("@")[1];
  }else{
    icon = item.icon;
  }
  let route = {
    path: item.path,
    component(resolve) {
      let componentPath = ''
      if (item.component === 'layout') {
        require(['../layout'], resolve)
        return
      } else {
        componentPath = item.component;
      }
      require([`../${componentPath}.vue`], resolve)
    },
    redirect: item.redirect,
    name: item.name,
    meta: {
      title: item.name, icon: icon,type:type
    },
    hidden: item.hidden
  };
  if(children.length != 0 ){
    if(!item.hidden){
      route['alwaysShow'] = true;
    }
    route['children'] = children;
  }
  return route;
}


const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
