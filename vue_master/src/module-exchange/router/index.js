/*
 * @Author: 陶峙巍 <taoshiwei@itcast.cn> 
 * @Description: 公司设置 
 * @Date: 2018-04-13 16:13:27 
 * @Last Modified by: hans.taozhiwei
 * @Last Modified time: 2018-09-03 11:13:19
 */

import Layout from '@/module-dashboard/pages/layout'
const _import = require('@/router/import_' + process.env.NODE_ENV)

export default [
  {
    root: true,
    path: '/exchange',
    component: Layout,
    redirect: 'noredirect',
    name: 'exchange',
    meta: {
      title: '考试交换',
      icon: 'message'
    },
    children: [
      {
        path: 'index',
        component: _import('exchange/pages/index'),
        name: 'exchange-index',
        meta: {title: '考试交换', icon: 'message', noCache: true}
      }
    ]
  }
]
