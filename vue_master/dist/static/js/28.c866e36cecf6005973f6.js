webpackJsonp([28],{WmWL:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("gUTm"),i={name:"saas-clients-index",data:function(){return{dataList:[]}},methods:{getList:function(){var t=this;Object(n.b)().then(function(e){t.dataList=e.data.data})}},created:function(){this.getList()}},r={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-container"},[a("div",{staticClass:"app-container"},[a("el-card",{attrs:{shadow:"never"}},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.dataList,border:""}},[a("el-table-column",{attrs:{fixed:"",type:"index",label:"序号",width:"80"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"",prop:"name",label:"企业名称",width:"280"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"",prop:"version",label:"版本",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"",prop:"companyphone",label:"联系电话",width:"180"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"",prop:"expirationDate",label:"截至时间",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"",prop:"state",label:"状态",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-switch",{attrs:{"inactive-value":0,"active-value":1,disabled:"","active-color":"#13ce66","inactive-color":"#ff4949"},model:{value:e.row.state,callback:function(a){t.$set(e.row,"state",a)},expression:"scope.row.state"}})]}}])}),t._v(" "),a("el-table-column",{attrs:{fixed:"right",label:"操作",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("router-link",{attrs:{to:"/saas-clients/details/"+e.row.id}},[t._v("查看")])]}}])})],1)],1)],1)])},staticRenderFns:[]};var l=a("C7Lr")(i,r,!1,function(t){a("hLDf")},"data-v-30b44000",null);e.default=l.exports},gUTm:function(t,e,a){"use strict";a.d(e,"b",function(){return i}),a.d(e,"a",function(){return r});var n=a("vLgD"),i=function(t){return Object(n.a)("/company","get",t)},r=function(t){return Object(n.a)("/company/"+t.id,"get",t)}},hLDf:function(t,e){}});