webpackJsonp([15],{EM5h:function(t,e){},FVwz:function(t,e){},"vd+D":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("Q0Ca"),i=a("uDR4"),n=a("Sejq"),o=a("5W9E"),r={name:"employeesDetails",components:{userInfo:n.default,postInfo:o.default},data:function(){return{userInfo:"userInfo",postInfo:"postInfo",activeName:"first",activeSecond:"two",userName:"user",postName:"post",printId:"",boxHeight:"",headImg:"",objId:this.$route.params.id,dataList:[]}},methods:{listDate:function(t){var e=this;Object(i.j)().then(function(t){e.dataList=t.data.items})}},mounted:function(){this.headImg=this.$refs.user.formData.staffPhoto},created:function(){this,this.printId=this.$route.params.id,this.boxHeight=Object(s.minHeight)(),this.listDate()},updated:function(){}},c={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-container"},[a("div",{staticClass:"app-container"},[a("el-card",{style:{minHeight:t.boxHeight}},[a("el-tabs",{staticClass:"infoPosin",model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{staticClass:"rInfo",attrs:{name:"first"}},[a("div",{staticClass:"fr"},[a("router-link",{staticClass:"fa fa-print",attrs:{to:{path:"/employees/print/"+t.printId,query:{name:t.userName}},"aria-hidden":"true",title:"打印"}}),t._v(" "),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:"点击打印按钮->右击打印预览界面->点击'打印'",placement:"top-end"}},[a("i",{staticClass:"fa fa-question-circle-o",attrs:{"aria-hidden":"true"}})])],1),t._v(" "),a("span",{attrs:{slot:"label"},slot:"label"},[t._v("个人信息")]),t._v(" "),a(t.userInfo,{ref:"user",tag:"component",attrs:{objId:t.objId}})],1),t._v(" "),a("el-tab-pane",{staticClass:"rInfo",attrs:{name:"second"}},[a("div",{staticClass:"fr"},[a("router-link",{staticClass:"fa fa-print",attrs:{to:{path:"/employees/print/"+t.printId,query:{name:t.postName}},"aria-hidden":"true",title:"打印"}}),t._v(" "),a("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:"点击打印按钮->右击打印预览界面->点击'打印'",placement:"top-end"}},[a("i",{staticClass:"fa fa-question-circle-o",attrs:{"aria-hidden":"true"}})])],1),t._v(" "),a("span",{attrs:{slot:"label"},slot:"label"},[t._v("岗位信息")]),t._v(" "),a(t.postInfo,{tag:"component",attrs:{objId:t.objId,headImg:t.headImg}})],1)],1)],1)],1)])},staticRenderFns:[]};var l=a("C7Lr")(r,c,!1,function(t){a("FVwz"),a("EM5h")},"data-v-cf4cdf86",null);e.default=l.exports}});