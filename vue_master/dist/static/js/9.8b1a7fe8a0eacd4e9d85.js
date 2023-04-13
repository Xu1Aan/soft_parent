webpackJsonp([9,32],{"2XSS":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("aFGL"),n={data:function(){return{parentId:"",dialogFormVisible:!1,dept:{}}},methods:{saveDept:function(){var t=this;this.dept.pid=this.parentId,Object(s.d)(this.dept).then(function(e){t.$message({message:e.data.message,type:e.data.success?"success":"error"}),e.data.success&&location.reload()})}}},i={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-dialog",{attrs:{title:"编辑部门",visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("el-form",{attrs:{model:t.dept,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"部门名称"}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:t.dept.name,callback:function(e){t.$set(t.dept,"name",e)},expression:"dept.name"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"部门编码"}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:t.dept.code,callback:function(e){t.$set(t.dept,"code",e)},expression:"dept.code"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"部门负责人"}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:t.dept.manager,callback:function(e){t.$set(t.dept,"manager",e)},expression:"dept.manager"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"部门介绍"}},[a("el-input",{attrs:{autocomplete:"off"},model:{value:t.dept.introduce,callback:function(e){t.$set(t.dept,"introduce",e)},expression:"dept.introduce"}})],1)],1),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:t.saveDept}},[t._v("确 定")])],1)],1)},staticRenderFns:[]};var o=a("C7Lr")(n,i,!1,function(t){a("nsod")},null,null);e.default=o.exports},FK6Z:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("aFGL"),n=a("X2Oc"),i={components:{deptAdd:a("2XSS").default},data:function(){return{deptAdd:"deptAdd",activeName:"first",departData:{},depts:[]}},methods:{handlAdd:function(t){this.$refs.addDept.parentId=t,this.$refs.addDept.dialogFormVisible=!0},handUpdate:function(t){var e=this;Object(s.b)({id:t}).then(function(t){e.$refs.addDept.dept=t.data.data,e.$refs.addDept.dialogFormVisible=!0})},handleDelete:function(t){var e=this;this.$confirm("是否删除此条记录?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(s.a)({id:t}).then(function(t){e.$message({message:t.data.message,type:t.data.success?"success":"error"}),t.data.success&&location.reload()})})},getList:function(){var t=this;Object(s.c)().then(function(e){t.departData=e.data.data,t.depts=n.a.transformTozTreeFormat(e.data.data.depts)})}},created:function(){this.getList()}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-container"},[a("div",{staticClass:"app-container"},[a("el-card",{attrs:{shadow:"never"}},[a("div",{staticClass:"organization-index"},[a("div",{staticClass:"organization-index-top"},[a("div",{staticClass:"main-top-title"},[a("el-tabs",{model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"组织结构",name:"first"}}),t._v(" "),a("div",{staticClass:"el-tabs-report"},[a("a",{staticClass:"el-button el-button--primary el-button--mini",attrs:{title:"导出"}},[t._v("导入")]),t._v(" "),a("a",{staticClass:"el-button el-button--primary el-button--mini",attrs:{title:"导出"}},[t._v("导出")])])],1)],1)]),t._v(" "),a("div",{staticClass:"treBox",staticStyle:{overflow:"scroll","white-space":"nowrap"}},[a("div",{staticClass:"treeCon clearfix"},[a("span",[a("i",{staticClass:"fa fa-university",attrs:{"aria-hidden":"true"}}),t._v(" "),a("span",[a("strong",[t._v(t._s(t.departData.companyName))])])]),t._v(" "),a("div",{staticClass:"fr"},[a("span",{staticClass:"treeRinfo"},[a("div",{staticClass:"treeRinfo"},[a("span",[t._v(t._s(t.departData.companyManage))]),t._v(" "),a("span",[t._v("在职  "),a("em",{staticClass:"colGreen",attrs:{title:"在职人数"}},[t._v("---")]),t._v("  ("),a("em",{staticClass:"colGreen",attrs:{title:"正式员工"}},[t._v("---")]),t._v(" / "),a("em",{staticClass:"colRed",attrs:{title:"非正式员工"}},[t._v("---")]),t._v(")")])]),t._v(" "),a("div",{staticClass:"treeRinfo"},[a("el-dropdown",{staticClass:"item"},[a("span",{staticClass:"el-dropdown-link"},[t._v("\n                              操作"),a("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),t._v(" "),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handlAdd("")}}},[t._v("添加子部门")])],1),t._v(" "),a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handleList()}}},[t._v("查看待分配员工")])],1)],1)],1)],1)])])]),t._v(" "),a("el-tree",{attrs:{props:{label:"name"},data:t.depts,"node-key":"id","default-expand-all":""},scopedSlots:t._u([{key:"default",fn:function(e){var s=e.node,n=e.data;return a("div",{staticClass:"generalClass",staticStyle:{width:"99%"}},[a("span",[s.isLeaf?a("i",{staticClass:"fa fa-male"}):a("i",{class:s.expanded?"fa fa-minus-square-o":"fa fa-plus-square-o"}),t._v(" "),a("span",[t._v(t._s(s.label))])]),t._v(" "),a("div",{staticClass:"fr"},[a("span",{staticClass:"treeRinfo"},[a("div",{staticClass:"treeRinfo"},[a("span",[t._v(t._s(t.departData.companyManage))]),t._v(" "),a("span",[t._v("在职  "),a("em",{staticClass:"colGreen",attrs:{title:"在职人数"}},[t._v("---")]),t._v("  ("),a("em",{staticClass:"colGreen",attrs:{title:"正式员工"}},[t._v("---")]),t._v(" / "),a("em",{staticClass:"colRed",attrs:{title:"非正式员工"}},[t._v("---")]),t._v(")")])]),t._v(" "),a("div",{staticClass:"treeRinfo"},[a("el-dropdown",{staticClass:"item"},[a("span",{staticClass:"el-dropdown-link"},[t._v("\n                                  操作"),a("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),t._v(" "),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handlAdd(n.id)}}},[t._v("添加子部门")])],1),t._v(" "),a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handUpdate(n.id)}}},[t._v("查看部门")])],1),t._v(" "),a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handleList()}}},[t._v("查看待分配员工")])],1),t._v(" "),a("el-dropdown-item",[a("el-button",{attrs:{type:"text"},on:{click:function(e){return t.handleDelete(n.id)}}},[t._v("删除部门")])],1)],1)],1)],1)])])])}}])})],1)])])],1),t._v(" "),a(t.deptAdd,{ref:"addDept",tag:"component"})],1)},staticRenderFns:[]};var l=a("C7Lr")(i,o,!1,function(t){a("nhpY"),a("Gl3/")},"data-v-6453d608",null);e.default=l.exports},"Gl3/":function(t,e){},nhpY:function(t,e){},nsod:function(t,e){}});