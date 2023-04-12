<template>
  <div class="settingBox">
    <!-- <div class="settingTop">
      <div class="setTop">
        <el-tabs v-model="activeName" class="topLab">
            <el-tab-pane name="role" class="rInfo">
              <span slot="label">角色管理</span>
              <component v-bind:is="roleList"></component>
            </el-tab-pane>
            <el-tab-pane name="companyInfo" class="rInfo">
              <span slot="label">部门信息</span>
              <component v-bind:is="roleList"></component>
            </el-tab-pane>
        </el-tabs>
      </div>
    </div> -->
    <span class="setTop">
      <i class="el-icon-share"></i><i>
      有{{number}}人邀请您交换监考信息</i>
      <span class="button"></span>
      <el-badge :value="number" :hidden="flag" class="item">
    <el-button @click="handleAllExchange()" type="primary" >
  查看监考交换
</el-button>
</el-badge>
    </span>
<el-dialog title="考试交换确认" :visible.sync="dialogTableVisible">
  <el-table stripe :data="exchangeTests">
    <el-table-column property="username" label="邀请人" width="120" align='center'></el-table-column>
    <el-table-column property="subject" label="科目" width="150" align='center'></el-table-column>
    <el-table-column property="date" label="日期" width="120" align='center'></el-table-column>
    <el-table-column property="time" label="时间" width="100" align='center'></el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="120"
      
      >
      <template slot-scope="scope">
        <el-button @click="handleAccess(scope.row.testUserId)" type="text" size="small">接收交换</el-button>
      </template>
    </el-table-column>
  </el-table>
</el-dialog>

 <el-divider></el-divider>
    <div class="formInfo">
      <div>
        <!-- 头部信息  -->
        <div class="userInfo">
            <div class="headInfo clearfix">
              <div class="headText">
                <el-table
                stripe
                :data="listData"
                border
                style="width:94%"
                class="tableTest"
                :default-sort = "{prop: 'date', order: 'descending'}"
                >    
                <el-table-column
                  fixed
                  prop="subject"
                  label="科目"
                  width="250"
                  align='center'
                  >
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="类型"
                  width="150"
                  align='center'
                  >
                </el-table-column>
                <el-table-column
                  prop="date"
                  label="日期"
                  width="150"
                  sortable
                  align='center'
                  >
                </el-table-column>
                  <el-table-column
                  prop="time"
                  label="时间"
                  width="100"
                  align='center'
                  >
                </el-table-column>
                <el-table-column
                  prop="remain"
                  label="剩余监考数"
                  width="150"
                  sortable
                  align='center'
                  >
                </el-table-column>
                  <el-table-column
                    label="考试可选状态"
                    width="175"
                    align='center'
                    >   
                  <template slot-scope="scope">  
                    <el-switch
                      v-model="scope.row.status"
                      disabled
                      :active-value="1"
                      :inactive-value="2"
                      >
                    </el-switch>
                    </template>
                </el-table-column>
              
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="210"
                  align='center'
                  >
                  <template slot-scope="scope">
                    <el-button @click="handleInfo(scope.row.id,scope.row.remain)" type="text" size="small">查看考试信息</el-button>
                    <el-button @click="handleExchange(scope.row)" type="text" size="small">交换考试</el-button>
                  </template>
                  </el-table-column>
                </el-table>

                  <el-dialog title="交换监考" :visible.sync="dialogFormVisible">
                    <el-form :model="testUser">
                       <el-form-item label="监考科目:" :label-width="formLabelWidth" >
                        <el-input v-model="testUser.subject" autocomplete="off" :disabled="true" ></el-input>
                      </el-form-item>
                      <el-form-item label="交换教师:" :label-width="formLabelWidth">
                        <el-select v-model="testUser.exchangeTeacherId" filterable placeholder="请选择">
                         <el-option
                           v-for="item in simpleUser"
                           :key="item.id"
                           :label="item.username"
                           :value="item.id">
                         </el-option>
                       </el-select>
                      </el-form-item> 
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="dialogFormVisible = false">取 消</el-button>
                      <el-button type="primary" @click="handleOK()">确 定</el-button>
                    </div>
                  </el-dialog>
                 <component v-bind:is="testInfo" ref="testInfo"></component>

              </div>
            </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { mytest, canceltest,exchangeTest,simple,findExchangeTest,accessExchangeTest } from "@/api/base/users";
import testInfo from "./../components/testInfo";
export default {
  name: "exchange-index",
  components: { testInfo },
  data() {
    return {
      testInfo: "testInfo",
      dialogFormVisible: false,
      dialogTableVisible : false,
        requestParameters:{
        page: 1,
        size: 10,
      },
      number:0,
      flag:false,
      listData: [],
      testUser:{
        exchangeTeacherId:'',
      },
      simpleUser:{},
      formLabelWidth: '120px',
      exchangeTests:[],
      mapData:{
        id:'',
      },
    }
  },
  methods: {
    doQuery() {
      mytest().then(res => {
        this.listData = res.data.data;
      });
    },
    doQuerySimpleUser(){
      simple().then(res=>{
        this.simpleUser = res.data.data;
      })
      findExchangeTest().then(res =>{
        this.exchangeTests = res.data.data.exchangeTests;
        this.number = this.exchangeTests.length;
        if(this.number==0){
          this.flag=true;
        }
      })
    },
    handleInfo(id,remain) {
      this.$refs.testInfo.dialogFormV(id,remain);
      this.doQuery();
    },
    handleExchange(item){
      this.dialogFormVisible = true;
      this.testUser = item;
    },
    handleOK(){
      exchangeTest(this.testUser).then(res=>{
        if(res.data.success){
           this.$message.success("等待对方确认");
        }
      })
      this.dialogFormVisible = false;
    },
    handleAllExchange(){
      findExchangeTest().then(res =>{
        this.exchangeTests = res.data.data.exchangeTests;
      })
      this.dialogTableVisible =true;
    },
    handleAccess(id){
      this.mapData.id = id;  
      accessExchangeTest(this.mapData).then(res=>{
         if(res.data.success){
           this.$message.success("成功交换");
           this.doQuery();
           this.doQuerySimpleUser();
        }
      })
    }
    
  },
  created() {
    this.doQuery();
    this.doQuerySimpleUser();
  }
};
</script>
<style>
.disabled .el-upload--picture-card {
  display: none !important;
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
@import "./../../styles/variables.scss";
.alert {
  margin: 5px 0px 0px 0px;
}
.pagination {
  margin-top: 5px;
  text-align: right;
}
.headInfo{
    margin-left: -10%;
}
.settingBox{
  padding-top: 20px;
}
.headText{
  padding-left: 10px;
}
.setTop{
  padding-left: 100px;
}
.button{
  padding-left: 50px;
}
</style>
