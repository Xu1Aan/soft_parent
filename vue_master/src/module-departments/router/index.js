import Layout from '@/module-dashboard/pages/layout'
const _import = require('@/router/import_' + process.env.NODE_ENV)

export default [
  {
    root: true,
    path: '/departments',
    component: Layout,
    redirect: 'noredirect',
    name: 'departments',
    meta: {
      title: '专业管理',
      icon: 'architecture'
    },
    children: [
      {
        path: 'index',
        component: _import('departments/pages/index'),
        name: 'organizations-index',
        meta: {title: '专业管理', icon: 'architecture', noCache: true}
      }
    ]
  }
]
