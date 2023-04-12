# SAAS IRHM 系统

## 运行

### 1. 安装 nodejs

https://nodejs.org/en/

### 2. 安装包依赖

```sh
yarn
```

### 3. 配置 API 地址

编辑文件 config/index.js

修改 `api` `upfile` 转发配置

```js
module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '',
    proxyTable: {
      '/api': {
        target: 'https://mock.boxuegu.com/mock/29',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/upfile': {
        target: 'http://172.17.0.65/system/upfile',
        changeOrigin: true,
        pathRewrite: {
          '^/upfile': ''
        }
      }
    },
```

### 4. 运行

```sh
npm start
```

## 资源

- 前端 vue
  http://git.itcast.cn/development/project-saas-hrm-vue.git

- 后端 java
  http://git.itcast.cn/course/project-saas-hrm-java-api.git

- 后端 java 配置中心
  http://git.itcast.cn/development/project-saas-hrm-java-config.git

- UED 产品业务原型
  http://research.itcast.cn/itcast-frontpage/SaaS-HRM

- API 接口设计与 Mock
  https://mock.boxuegu.com/project/29/interface/api

- 持续集成 外网域名
  http://hrm.research.itcast.cn

- 代码质量分析
  http://sonar.itcast.cn/projects

---

@传智研究院-研发部

江苏传智播客教育科技股份有限公司 &nbsp;版权所有 Copyright 2006-2018, All Rights Reserved
