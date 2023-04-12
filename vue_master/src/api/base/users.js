import {createAPI} from '@/utils/request'

export const list = data => createAPI('/sys/user', 'get', data)
export const simple = data => createAPI('/sys/user/simple', 'get', data)
export const add = data => createAPI('/sys/user', 'post', data)
export const update = data => createAPI(`/sys/user/${data.id}`, 'put', data)
export const remove = data => createAPI(`/sys/user/${data.id}`, 'delete', data)
export const detail = data => createAPI(`/sys/user/${data.id}`, 'get', data)
export const assignRoles = data => createAPI(`/sys/user/assignRoles`, 'put', data)
export const updateTaste = data => createAPI(`/sys/user/updateTaste`, 'post', data)
export const taste = data => createAPI(`/sys/user/taste`, 'get', data)
export const updateSelectedTaste = data => createAPI(`/sys/user/updateSelectedTaste`, 'post', data)

export const savetest = data => createAPI(`/sys/teacher/test`, 'post', data)
export const listtest = data => createAPI(`/sys/teacher/test`, 'get', data)
export const mytest = data => createAPI(`/sys/teacher/test/myTest`, 'get', data)
export const selecttest = data => createAPI(`/sys/teacher/test/select`, 'post', data)
export const detailtest = data => createAPI(`/sys/teacher/test/${data.id}`, 'get', data)
export const settest = data => createAPI(`/sys/teacher/test/setTest/${data.id}`, 'get', data)
export const updatetest = data => createAPI(`/sys/teacher/test/updateTest/${data.id}`, 'put', data)
export const deletetest = data => createAPI(`/sys/teacher/test/${data.id}`, 'delete', data)
export const canceltest = data => createAPI(`/sys/teacher/test/cancel/${data.id}`, 'delete', data)
export const testinfo = data => createAPI(`/sys/teacher/testinfo/${data.id}`, 'get', data)
export const cancelbyadmin = data => createAPI(`/sys/teacher/test/cancelbyadmin`, 'delete', data)
export const findAll = data => createAPI(`/sys/teacher/findAllTest`, 'get', data)
export const selectByAdmin = data => createAPI(`/sys/teacher/test/selectByAdmin`, 'post', data)
export const exchangeTest = data => createAPI(`/sys/teacher/test/exchangeTest`, 'post', data)
export const findExchangeTest = data => createAPI(`/sys/teacher/test/findExchangeTest`, 'get', data)
export const accessExchangeTest = data => createAPI(`/sys/teacher/test/accessExchangeTest`, 'post', data)



