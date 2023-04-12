<template>
  <div class="boxInfo">
    <!-- 表单内容 -->
    <div class="formInfo">
      <div>
        <!-- 头部信息  -->
        <div class="userInfo">
             <div class="headInfo clearfix">
               <div class="headText">
<el-card class="card">
          <el-form
        ref="searchForm"
        :inline="true"
        :model="exportParam"
        label-width="80px"
        class="search-form"
      >
        <el-form-item label="文件名:">
          <el-input v-model="exportParam.fileName" placeholder="文件名" />
        </el-form-item>
        <el-form-item label="自动宽度:">
          <el-radio-group v-model="exportParam.autoWidth">
            <el-radio :label="true">自动</el-radio>
            <el-radio :label="false">固定</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="文件类型:">
          <el-select v-model="exportParam.type" placeholder="文件类型">
            <el-option value="xlsx" label="xlsx" />
            <el-option value="csv" label="csv" />
            <el-option value="txt" label="txt" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 15px" @click="handleExport">导出Excel</el-button>
        </el-form-item>
      </el-form>
    <el-table
    stripe
    :data="listData.filter(data => !search || data.subject.toLowerCase().includes(search.toLowerCase()))"
    border
    style="width:110%"
    class="tableTest"
    :default-sort = "{prop: 'date', order: 'descending'}"
    @selection-change="handleSelectionChange"
    >
        
         <el-table-column >
        <template slot="header" slot-scope="scope">
        <font>搜索考试:</font>
        <el-input style="width:40%"
          v-model="search"
          size='mid'
          placeholder="输入关键字搜索"/>
      </template>
   <el-table-column type="selection" width="50" align="center" />
      
    <el-table-column
      fixed
      prop="subject"
      label="科目"
      width="180"
      align='center'
      >
    </el-table-column>
    <el-table-column
      prop="type"
      label="类型"
      width="120"
      align='center'
      >
    </el-table-column>
    <el-table-column
      prop="date"
      label="日期"
      width="120"
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
      prop="number"
      label="已选人数"
      width="80"
      align='center'>
    </el-table-column>
    <el-table-column
      label="考试可选状态"
      width="143"
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
      width="185"
      align='center'
      >
      <template slot-scope="scope">
        <el-button @click="handlefind(scope.row.id,scope.row.subject)" type="text" size="small">查看</el-button>
        <el-button @click="handleset(scope.row.id,scope.row.subject)" type="text" size="small">编辑</el-button>
      </template>
      </el-table-column>
    </el-table-column>
  </el-table>
  </el-card>
       <div class="pagination">
          <PageTool :paginationPage="requestParameters.page" :paginationPagesize="requestParameters.size" :total="counts" @pageChange="handleCurrentChange" @pageSizeChange="handleSizeChange">
          </PageTool>
        </div>

         <component v-bind:is="testmessage" ref="testmessage"></component>
         <component v-bind:is="testSet" v-bind:callback="doQuery" ref="testSet"></component>    
                </div>
             </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import testmessage from "./testmessage";
import testSet from "./testSet";
import { listtest } from "@/api/base/users";
import PageTool from "./../../components/page/page-tool";
import excel from "./../../utils/excel";
export default {
  name: "testInfo",
  components: { PageTool, testmessage, testSet },
  data() {
    return {
      testmessage: "testmessage",
      testSet:"testSet",
      listLoading: true,
      exportParam: {
        fileName: "",
        autoWidth: true,
        type: "xlsx"
      },
      multipleSelection: [],
      testmessage: "testmessage",
      testSet: "testSet",
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
    doQuery() {
      listtest(this.requestParameters).then(res => {
        this.listData = res.data.data.rows;
        this.counts = res.data.data.total;
        var i = 0; 
        this.listData.forEach(element => {
          this.listData[i++].number = element.number - parseInt(element.remain);
          
        });
      });
    },
    // 每页显示信息条数
    handleSizeChange(size) {
      this.requestParameters.size = size;
      if (this.requestParameters.page === 1) {
        this.doQuery(this.requestParameters);
      }
    },
    // // 进入某一页
    handleCurrentChange(val) {
      this.requestParameters.page = val;
      this.doQuery();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleExport() {
      if (this.multipleSelection.length) {
        const params = {
          header: ["科目", "类型", "日期", "时间", "监考人数", "考试状态"],
          key: ["subject", "type", "date", "time", "number", "status"],
          data: this.multipleSelection,
          autoWidth: this.exportParam.autoWidth,
          fileName: this.exportParam.fileName,
          bookType: this.exportParam.type
        };
        excel.exportDataToExcel(params);
        this.$refs.multipleTable.clearSelection();
      } else {
        this.$message.warning("请勾选要导出的数据项！");
      }
    },
    handlefind(id,subject) {
      this.$refs.testmessage.dialogFormV(id,subject);
    },
    handleset(id,subject) {
      this.$refs.testSet.dialogFormV(id,subject);
    },

    // //
    //  handleSelect(id,status,remain) {
    //   console.log(status);
    //   if(status != 1 || remain == '0'){
    //     this.$message("无法选择该考试!");
    //     return;
    //   }
    //   this.$refs.selectTests.dialogFormV(id);
    // },
    // handlSet(id) {
    //   this.$refs.setTests.dialogSetTest(id)
    // },
    // handldelete(item) {
    //   this.$confirm(
    //     `《${item.subject}》删除后将不可恢复，您确认删除吗？`,{
    //      type: 'warning'
    //     }
    //   ).then(() => {
    //   deletetest({id : item.id}).then(res =>{
    //     if(res.data.success){
    //       this.$message.success("删除成功")}
    //   })
    // })
    // },
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
.el-card {
  min-height: 500px;
}
.boxInfo {
  padding-left: 50px;
}
.headInfo .headText {
  margin: -20px 109px 2px 10px;
}
.headText{
  padding-left: 2px;
}
.card{
  padding-left: 5px;
}
</style>
