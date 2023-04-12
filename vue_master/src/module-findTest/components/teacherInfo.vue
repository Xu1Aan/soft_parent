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
    :data="listData.filter(data => !search || data.username.toLowerCase().includes(search.toLowerCase()))"
    border
    style="width:110%"
    class="tableTest"
    :default-sort = "{prop: 'date', order: 'descending'}"
    @selection-change="handleSelectionChange"
    >

         <el-table-column >
        <template slot="header" slot-scope="scope">
        <font>搜索教师名字:</font>
        <el-input style="width:40%"
          v-model="search"
          size='mid'
          placeholder="输入关键字搜索"/>
        <span class="button"></span>
        <el-button  type="primary" icon="el-icon-edit" @click="handleSetSelected()"  size="small">编辑</el-button>
      </template>
   
   <el-table-column type="selection" width="50" align="center" />
    <el-table-column
      fixed
      prop="username"
      label="姓名"
      width="180"
      align='center'
      >
    </el-table-column>
    <el-table-column
      prop="mobile"
      label="电话"
      width="150"
      align='center'
      >
    </el-table-column>
    <el-table-column
      prop="departmentName"
      label="教研室"
      width="180"
      align='center'
      >
    </el-table-column>
       <el-table-column
      prop="taste"
      label="任务数"
      width="113"
      align='center'
      >
    </el-table-column>
    <el-table-column
      prop="doTaste"
      label="已完成任务数"
      width="151"
      sortable
      align='center'>
    </el-table-column>

    <el-table-column
      fixed="right"
      label="操作"
      width="150"
      align='center'
      >
      <template slot-scope="scope">
        <el-button @click="handleset(scope.row.username,scope.row.id)" type="text" size="small">编辑</el-button>
      </template>
      </el-table-column>
    </el-table-column>
  </el-table>
  </el-card>
       <div class="pagination">
          <PageTool :paginationPage="requestParameters.page" :paginationPagesize="requestParameters.size" :total="counts" @pageChange="handleCurrentChange" @pageSizeChange="handleSizeChange">
          </PageTool>
        </div>


                </div>
             </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import { list, updateTaste,updateSelectedTaste } from "@/api/base/users";
import PageTool from "./../../components/page/page-tool";
import excel from "./../../utils/excel";
export default {
  name: "teacherInfo",
  components: { PageTool },
  data() {
    return {
      listLoading: true,
      exportParam: {
        fileName: "",
        autoWidth: true,
        type: "xlsx"
      },
      multipleSelection: [],
      listData: [],
      counts: "",
      search: "",
      requestParameters: {
        page: 1,
        size: 10
      },
      map: {
        id: "",
        taste: 0
      },
      users: {
        userInfo:[],
        taste:0,
      }
    };
  },
  methods: {
    doQuery() {
      list(this.requestParameters).then(res => {
        this.listData = res.data.data.rows;
        this.counts = res.data.data.total;
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
      this.usersInfo = val;
      this.doQuery(val);
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleExport() {
      if (this.multipleSelection.length) {
        const params = {
          header: ["姓名", "电话", "教研室", "任务数", "已完成任务数"],
          key: ["username", "mobile", "departmentName", "taste", "doTaste"],
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
    handleSetSelected() {
       if (this.multipleSelection.length){
      this.$prompt("你正在为选中的"+this.multipleSelection.length+"个老师设置监考任务数", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[0-9]\d*$/,
        inputErrorMessage: "你的输入格式不正确，请输入数字"
      })
        .then(({ value }) => { 
          this.users.userInfo = this.multipleSelection;
          this.users.taste = value
          updateSelectedTaste(this.users).then(res => {
            if (res.data.success == true) {
              this.$message({
                type: "success",
                message: "设置成功"
              });
              this.doQuery();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入"
          });
        });
        }else{
          this.$message.warning("请勾选要导出的数据项！");
        }
    },
    handleset(username, id) {
      this.$prompt("你正在为" + username + "设置监考任务数", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[0-9]\d*$/,
        inputErrorMessage: "你的输入格式不正确，请输入数字"
      })
        .then(({ value }) => {
          this.map.id = id;
          this.map.taste = value;
          updateTaste(this.map).then(res => {
            if (res.data.success == true) {
              this.$message({
                type: "success",
                message: username + "的监考任务数是: " + value
              });
              this.doQuery();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入"
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
.el-card {
  min-height: 500px;
}
.boxInfo {
  padding-left: 50px;
}
.headInfo .headText {
  margin: -20px 109px 2px 10px;
}
.headText {
  padding-left: 2px;
}
.card {
  padding-left: 5px;
}
.button {
  padding-right: 370px;
}
</style>
