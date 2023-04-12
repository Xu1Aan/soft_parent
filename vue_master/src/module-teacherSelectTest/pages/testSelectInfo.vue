<template>
  <div class="boxInfo">
    <!-- 表单内容 -->
    <div class="formInfo">
      <div>
        <!-- 头部信息  -->
        <div class="userInfo">
             <div class="headInfo clearfix">
               <div class="headText">
  <el-table
    stripe
    :data="listData.filter(data => !search || data.subject.toLowerCase().includes(search.toLowerCase()))"
    border
    style="width:100%"
    class="tableTest"
    :default-sort = "{prop: 'date', order: 'descending'}"
    >    
         <el-table-column >
        <template slot="header" slot-scope="scope">
        <font>搜索考试:</font>
        <el-input style="width:40%"
          v-model="search"
          size='mid'
          placeholder="输入关键字搜索"/>
      </template>
      
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
        :inactive-value="0"
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
        <el-button @click="handleSelect(scope.row.id,scope.row.status,scope.row.remain)" type="text" size="small">选择</el-button>
      </template>
      </el-table-column>
    </el-table-column>
  </el-table>
   <div class="pagination">
          <PageTool :paginationPage="requestParameters.page" :paginationPagesize="requestParameters.size" :total="counts" @pageChange="handleCurrentChange" @pageSizeChange="handleSizeChange">
          </PageTool>
        </div>

       
         <!-- <component v-bind:is="setTest" ref="setTests"></component> -->
         <setTest ref="setTest" v-bind:callback="doQuery"></setTest>
         <selectTest v-bind:callback="doQuery" ref="selectTests"></selectTest>
        
                </div>
             </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import selectTest from "./../components/selectTest";
import setTest from "./../components/setTest";
import { listtest, deletetest } from "@/api/base/users";
import PageTool from "./../../components/page/page-tool";
export default {
  name: "settest",
  components: { PageTool, selectTest, setTest },
  data() {
    return {
      setTest: "setTest",
      selectTest: "selectTest",
      listData: [],
      counts: "",
      search: "",
      requestParameters: {
        page: 1,
        size: 10
      }
    };
  },
  methods: {
    //判断时间是否过期
    judgeTime(time) {
      var myDate = new Date();
      //2012年12月30日 13:13:00
      var now = myDate.toLocaleDateString() + " " + myDate.toLocaleTimeString();
      //2012-12-30 13:13:00
      now = now
        .replace("年", "-")
        .replace("月", "-")
        .replace("日", "");
      now = new Date(now.replace(/-/g, "/")); //2012/12/30 13:13:00
      time = new Date(time.replace(/-/g, "/"));
      if (time == null || Date.parse(time) - Date.parse(now) < 0) {
        return true;
      } else {
        return false;
      }
    },
    doQuery() {
      console.log(111);
      listtest(this.requestParameters).then(res => {
        this.listData = res.data.data.rows;
        this.counts = res.data.data.total;
        // var i = 0;
        // this.listData.forEach(element => {
        //   if (!this.judgeTime(element.date) && element.status == 1) {
        //     this.listData[i++].status = 0;
        //   }
        // });
      });
    },
    // 每页显示信息条数
    handleSizeChange(size) {
      this.requestParameters.size = size;
      if (this.requestParameters.page === 1) {
        this.doQuery(this.requestParameters);
      }
    },
    // 进入某一页
    handleCurrentChange(val) {
      this.requestParameters.page = val;
      this.doQuery();
    },
    //
    handleSelect(id, status, remain) {
      console.log(status);
      if (status != 1 || remain == "0") {
        this.$message("无法选择该考试!");
        return;
      }
      this.$refs.selectTests.dialogFormV(id);
      this.doQuery();
    },
    handlSet(id) {
      // this.$refs.setTests.dialogSetTest(id);
      this.$refs.setTest.dialogSetTest(id);
    },
    handldelete(item) {
      this.$confirm(`《${item.subject}》删除后将不可恢复，您确认删除吗？`, {
        type: "warning"
      }).then(() => {
        deletetest({ id: item.id }).then(res => {
          if (res.data.success) {
            this.$message.success("删除成功");
          }
          this.doQuery();
        });
      });
    }
  },
  created() {
    this.doQuery();
  }
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.alert {
  margin: 5px 0px 0px 0px;
}
.pagination {
  margin-top: 5px;
  text-align: right;
}
</style>
