<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
            <div class='organization-index'>
              <div class='organization-index-top'>
                <div class='main-top-title'>
                  <el-tabs v-model="activeName">
                    <el-tab-pane label="组织结构" name="first"></el-tab-pane>
                    <div class="el-tabs-report">
                     
                    </div>
                  </el-tabs>
                </div>
              </div>
              <div style="overflow: scroll;white-space:nowrap"  class="treBox">
                <div class="treeCon clearfix">
                    <span>
                      <i class="fa fa-university" aria-hidden="true"></i>
                      <span ><strong>{{departData.companyName}}</strong></span>
                    </span>
                    <div class="fr">
                      <span class="treeRinfo">
                        <div class="treeRinfo">
                        </div>
                        <div class="treeRinfo">
                          <el-dropdown class="item">
                            <span class="el-dropdown-link">
                              操作<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>
                                  <el-button type="text" @click="handlAdd('')">添加子部门</el-button>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                          </el-dropdown>
                        </div>
                      </span>  
                    </div>
                  </div>

                  <!-- 
                    构造树形列表
                      叶子 <i class="fa fa-male"></i>
                      非叶子 
                        展开 <i class="fa fa-minus-square-o">
                        闭合 <i class="fa fa-plus-square-o">
                    <div class="generalClass" slot-scope="{node,data}" style="width:99%">
                  -->
                  <el-tree :props="{label:'name'}" :data="depts" node-key="id" default-expand-all>
                    <!--
                      node : 是否展开，是否叶子节点
                      data：部门对象
                            id，name
                     -->
                    <div class="generalClass" slot-scope="{node,data}" style="width:99%">
                        <span>
                           <i v-if="node.isLeaf" class="fa fa-male"></i>
                           <i v-else :class="node.expanded?'fa fa-minus-square-o':'fa fa-plus-square-o'"></i>
                          <span>{{ node.label }}</span>
                        </span>
                         <div class="fr">
                          <span class="treeRinfo">
                            <div class="treeRinfo">
                              
                            </div>
                            <div class="treeRinfo">
                              <el-dropdown class="item">
                                <span class="el-dropdown-link">
                                  操作<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                      <el-button type="text" @click="handlAdd(data.id)">添加子部门</el-button>
                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                      <el-button type="text" @click="handUpdate(data.id)">查看部门</el-button>
                                    </el-dropdown-item>
                                  <el-dropdown-item>
                                    <el-button type="text" @click="handleDelete(data.id)">删除部门</el-button>
                                  </el-dropdown-item>
                                </el-dropdown-menu>
                              </el-dropdown>
                            </div>
                          </span>  
                        </div>
                      </div>
                    </el-tree>
              </div>
            </div>    
      </el-card>
    </div>
    <!--:visible.sync 是否显示 -->
    <!--引入组件-->
    <component v-bind:is="deptAdd" ref="addDept"></component>
</div>
</template>
 
<!-- 引入组件 -->
<script>
//引入api
import {list,saveOrupdate,find,deleteById} from "@/api/base/dept"
import commonApi from '@/utils/common'
import deptAdd from './../components/add'
export default {
  components:{deptAdd},
  data() {
    return {
      deptAdd:'deptAdd',
      activeName: 'first', 
      departData:{},
      depts:[]
    }
  },
  methods: {
      //添加部门
    handlAdd(parentId) {
      //父页面调用子组件中的内容
      this.$refs.addDept.parentId = parentId;
      this.$refs.addDept.dialogFormVisible = true
    },
    //查看部门
    handUpdate(id) {
      //根据id查询部门
      find({id:id}).then(res => {
         //数据绑定到dept对象中
         this.$refs.addDept.dept = res.data.data;
         this.$refs.addDept.dialogFormVisible = true
      })
    },
  
    handleDelete(id) {
       this.$confirm('是否删除此条记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
               deleteById({id:id}).then(res=> {
                this.$message({
                  message: res.data.message,
                  type: res.data.success?'success':'error'
                });
                if(res.data.success) {
                  location.reload();
                }
              })
        })
    },

    //构造查询方法
    getList() {
      list().then(res => {
       this.departData = res.data.data
       //将普通的数据转化为父子接口
       this.depts = commonApi.transformTozTreeFormat(res.data.data.depts);
      })
    }
  },
  created: function() {
    this.getList();
  },
}
</script>
<style rel="stylesheet/scss" lang="scss">
.el-dropdown {
  color: #000000
}
.el-tree-node__content>.el-tree-node__expand-icon {
  padding:0px;
}
.el-tree-node__expand-icon {
  color:#ffffff
}
.generalClassNode {
  padding-left: 20px;
}
.el-tree-node__content{
  font-size: 16px;
  line-height: 36px;
  height:36px;
}
.custom-tree-node{
  padding-left: 20px;
}
.objectTree {
  overflow: auto;
  z-index: 100;
  width: 300px;
  border: 1px solid #dcdfe6;
  margin-top: 5px;
  left: 70px;
}
.el-tabs__content {
  overflow: initial;
}
.boxpad {
  margin-left: -40px;
}
</style>
<style  rel="stylesheet/scss" lang="scss" scoped>
.el-tree-node__expand-icon{
 
}
.el-icon-caret-right{}
.el-tree-node__content{
  font-size: 14px;
  line-height: 36px;
}
.generalClass {
  font-size: 14px;
  line-height: 36px;
  color:#000000
}
.all {
  position: relative;
  min-height: 100%;
  padding-bottom: 200px;
}
.organization-main:after,
.organization-index-top:after {
  display: block;
  clear: both;
  content: '';
  visibility: hidden;
  height: 0;
}
.organization-main {
  font-size: 14px;
  font-size: 14px;
}

.organization-index {
  padding-bottom: 20px;
  margin-left: 20px;
}
.main-top-title {
  padding-left: 20px;
  padding-top: 20px;
  text-align: left;
}

::-webkit-scrollbar-thumb {
  background-color: #018ee8;
  height: 50px;
  outline-offset: -2px;
  outline: 8px solid #fff;
  -webkit-border-radius: 4px;
}
::-webkit-scrollbar-track-piece {
  background-color: #fff;
  -webkit-border-radius: 0;
}
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
::-webkit-scrollbar-thumb:hover {
  background-color: #fb4446;
  height: 50px;
  -webkit-border-radius: 4px;
}
.modal-total {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background: #000;
  z-index: 90;
  opacity: 0.2;
}
.modal {
  width: 400px;
  height: 300px;
  background-color: #ffffff;
  z-index: 999;
  position: absolute;
  left: 45%;
  top: 20%;
  text-align: center;
}
.treBox {
  padding: 30px 120px 0;
}
.organization-index-top {
  position: relative;

  .el-tabs-report {
    position: absolute;
    top: -50px;
    right: 15px;
  }
}
.treeCon {
  border-bottom: 1px solid #cfcfcf;
  padding: 10px 0;
  margin-bottom: 10px;
  .el-dropdown {
    color: #333;
  }
}
.treeRinfo {
  display: inline-block;
}
.treeRinfo span {
  padding-left: 30px;
}
</style>
