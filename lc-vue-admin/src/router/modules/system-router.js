

const systemRouter = {
  path: '/system',
  component: () => import('@/layout'),
  redirect: '',
  name: 'system',
  meta: {
    title: '系统管理', icon: 'table'
  },
  children: [
    {
      path: 'users',
      component: () => import('@/views/default/index'),
      name: 'users',
      meta: { title: '用户管理', icon: 'table' },
      alwaysShow:true,
      children:[
        {
          path: 'roles',
          component: () => import('@/views/default/index'),
          name: 'roles',
          meta: { title: '角色1', icon: 'table' },
          alwaysShow:true,
          children:[
            {
              path: 'roles',
              component: () => import('@/views/system/roles/list'),
              name: 'roles',
              meta: { title: '角色2', icon: 'table' }

            }
          ]
        }
      ]
    },{
      path: 'users/edit',
      component: () => import('@/views/system/users/edit'),
      hidden: true,
      name: 'users-edit',
      meta: { title: '新增用户' }
    },{
      path: 'roles',
      component: () => import('@/views/system/roles/list'),
      name: 'roles',
      meta: { title: '角色管理', icon: 'table' }

    },{
      path: 'roles/edit',
      component: () => import('@/views/system/roles/edit'),
      hidden: true,
      name: 'roles-edit',
      meta: { title: '新增角色' }
    },{
      path: 'roles/perm',
      component: () => import('@/views/system/roles/perm'),
      hidden: true,
      name: 'roles-edit',
      meta: { title: '配置权限' }
    },{
      path: 'generator',
      component: () => import('@/views/system/generator/list'),
      name: 'generator',
      meta: { title: '代码生成', icon: 'table' }

    },{
      path: 'menus',
      component: () => import('@/views/system/menus/list'),
      name: 'menus',
      meta: { title: '菜单管理', icon: 'table' }

    },{
      path: 'menus/edit',
      component: () => import('@/views/system/menus/edit'),
      hidden: true,
      name: 'menus-edit',
      meta: { title: '新增菜单' }
    },{
      path: 'functions',
      component: () => import('@/views/system/functions/list'),
      name: 'functions',
      meta: { title: '资源管理', icon: 'table' }

    },{
      path: 'functions/edit',
      component: () => import('@/views/system/functions/edit'),
      hidden: true,
      name: 'functions-edit',
      meta: { title: '新增资源' }
    }
  ]
}
export default systemRouter
