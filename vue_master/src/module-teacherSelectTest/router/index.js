/*
 * @Author: itcast 
 * @Description: xxx业务模块 
 * @Date: 2018-04-13 16:13:27 
 * @Last Modified by: hans.taozhiwei
 * @Last Modified time: 2018-09-03 11:12:47
 */

import Layout from '@/module-dashboard/pages/layout'
const _import = require('@/router/import_' + process.env.NODE_ENV)

export default [
  {
    root: true,
    path: '/teacherSelectTest',
    component: Layout,
    redirect: 'noredirect',
    name: 'teacherSelectTest',
    meta: {
      title: '个人信息业务模块管理',
      icon: 'approval'
    },
    children: [
      {
        path: 'index',  //请求地址
        component: _import('teacherSelectTest/pages/index'), //跳转的vue视图
        name: 'teacherSelectTest-index',
        meta: {title: '监考选择', icon: 'approval', noCache: true}
      }
    ]
  }
]
