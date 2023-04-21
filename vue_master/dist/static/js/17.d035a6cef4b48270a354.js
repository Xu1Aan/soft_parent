webpackJsonp([17],{"ITi+":function(t,e){},bsoA:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=a("bd2Q"),s=a("f0da"),r=a("X2Oc"),o=null,n={name:"roleList",components:{PageTool:a("1f1P").a},props:["objId"],data:function(){return{formData:{},treeData:[],checkNodes:[],dialogFormVisible:!1,permFormVisible:!1,dataList:[],counts:0,requestParameters:{page:1,pagesize:10}}},methods:{assignPrem:function(){var t=this;Object(i.b)({id:this.formData.id,permIds:this.$refs.tree.getCheckedKeys()}).then(function(e){t.$message({message:e.data.message,type:e.data.success?"success":"error"}),t.permFormVisible=!1})},handlerPerm:function(t){var e=this;Object(i.c)({id:t.id}).then(function(t){e.formData=t.data.data,e.checkNodes=t.data.data.permIds,s.b({type:0,pid:null,enVisible:1}).then(function(t){e.treeData=r.a.transformTozTreeFormat(t.data.data),e.permFormVisible=!0})})},handlerAdd:function(){this.formData={},this.dialogFormVisible=!0},handleDelete:function(t){var e=this;this.$confirm("本次操作将删除"+t.name+",删除后角色将不可恢复，您确认删除吗？").then(function(){Object(i.f)({id:t.id}).then(function(t){e.$message({message:t.data.message,type:t.data.success?"success":"error"}),e.doQuery()})})},handleUpdate:function(t){var e=this;Object(i.c)({id:t.id}).then(function(a){e.formData=a.data.data,e.formData.id=t.id,e.dialogFormVisible=!0})},saveOrUpdate:function(){null==this.formData.id||void 0==this.formData.id?this.save():this.update()},update:function(){var t=this;Object(i.g)(this.formData).then(function(e){t.$message({message:e.data.message,type:e.data.success?"success":"error"}),e.data.success&&(t.formData={},t.dialogFormVisible=!1,t.doQuery())})},save:function(){var t=this;Object(i.a)(this.formData).then(function(e){t.$message({message:e.data.message,type:e.data.success?"success":"error"}),e.data.success&&(t.formData={},t.dialogFormVisible=!1,t.doQuery())})},doQuery:function(){var t=this;Object(i.e)(this.requestParameters).then(function(e){t.dataList=e.data.data.rows,t.counts=e.data.data.total})},handleSizeChange:function(t){this.requestParameters.pagesize=t,1===this.requestParameters.page&&o.doQuery(this.requestParameters)},handleCurrentChange:function(t){this.requestParameters.page=t,o.doQuery()}},mounted:function(){},created:function(){o=this,this.doQuery()},updated:function(){}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"boxInfo"},[a("div",{staticClass:"formInfo"},[a("div",[a("div",{staticClass:"userInfo"},[a("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},on:{click:t.handlerAdd}},[t._v("新增角色")]),t._v(" "),a("el-table",{staticStyle:{width:"100%","margin-top":"10px"},attrs:{data:t.dataList,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{type:"index",index:1,label:"序号",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"",prop:"name",label:"角色名",width:"150"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"",prop:"description",label:"描述"}}),t._v(" "),a("el-table-column",{attrs:{fixed:"right",label:"操作",align:"center",width:"250"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){return t.handlerPerm(e.row)}}},[t._v("分配权限")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){return t.handleUpdate(e.row)}}},[t._v("修改")]),t._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){return t.handleDelete(e.row)}}},[t._v("删除")])]}}])})],1),t._v(" "),a("div",{staticClass:"pagination"},[a("PageTool",{attrs:{paginationPage:t.requestParameters.page,paginationPagesize:t.requestParameters.pagesize,total:t.counts},on:{pageChange:t.handleCurrentChange,pageSizeChange:t.handleSizeChange}})],1)],1)])]),t._v(" "),a("el-dialog",{staticStyle:{hight:"100px","line-height":"1px"},attrs:{title:"编辑角色",visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[a("el-form",{staticStyle:{"margin-top":"20px"},attrs:{model:t.formData,"label-width":"90px"}},[a("el-form-item",{attrs:{label:"角色名称"}},[a("el-input",{staticStyle:{width:"90%"},attrs:{autocomplete:"off"},model:{value:t.formData.name,callback:function(e){t.$set(t.formData,"name",e)},expression:"formData.name"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"角色描述"}},[a("el-input",{staticStyle:{width:"90%"},attrs:{autocomplete:"off"},model:{value:t.formData.description,callback:function(e){t.$set(t.formData,"description",e)},expression:"formData.description"}})],1)],1),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:t.saveOrUpdate}},[t._v("确 定")])],1)],1),t._v(" "),a("el-dialog",{staticStyle:{hight:"100px","line-height":"1px"},attrs:{title:"为【"+t.formData.name+"】分配权限",visible:t.permFormVisible},on:{"update:visible":function(e){t.permFormVisible=e}}},[a("el-tree",{ref:"tree",attrs:{data:t.treeData,"default-expand-all":"","show-checkbox":"","node-key":"id","check-strictly":!0,"default-checked-keys":t.checkNodes,props:{label:"name"}}}),t._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.permFormVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:t.assignPrem}},[t._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var c=a("C7Lr")(n,l,!1,function(t){a("ITi+"),a("wxdr")},"data-v-8ffb5386",null);e.default=c.exports},wxdr:function(t,e){}});