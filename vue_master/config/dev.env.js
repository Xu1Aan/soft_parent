'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

//  /company/xxx     api/company
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"api"'
})
