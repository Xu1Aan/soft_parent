<template>
  <div class="import-excel-wrapper">
    <Hints>
      <template slot="hintInfo">
        <span>导入科目：</span>
        <el-select v-model="testId" filterable placeholder="请选择科目"  @change="change()" >
          <el-option
           v-for="item in tests"
           :key="item.id"
           :label="item.subject"
           :value="item.id" 
           >
          </el-option>
        </el-select>
        <el-button @click="handleSelect()" type="primary" icon="el-icon-search">查看考试信息</el-button>
        <span class="uplodButton"></span>
        <el-button @click="handleUpload()" type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
      </template>
    </Hints>
    <el-card shadow="always">
      <UploadExcel @on-success="handleSuccess" />
      <el-table
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        size="medium"
      >
        <el-table-column v-for="item in tableHeader" :key="item" :prop="item" :label="item" align="center" />
      </el-table>
    </el-card>
    <component v-bind:is="testmessage" ref="testmessage"></component>
  </div>
</template>

<script>
import testmessage from "./testmessage";
import UploadExcel from "./UploadExcel";
import Hints from "./Hints";
import testInfoVue from "../../module-findTest/components/testInfo.vue";
import { findAll, selectByAdmin } from "@/api/base/users";

export default {
  name: "importInfo",
  components: { UploadExcel, Hints, testmessage },
  data() {
    return {
      testmessage: "testmessage",
      tableHeader: [],
      tableData: [],
      FormData: {
        testId: "",
        tableData: []
      },
      tests: [],
      testId: "",
      remain: 0,
      subject: ""
    };
  },
  methods: {
    handleSuccess({ header, results }) {
      this.tableHeader = header;
      this.tableData = results;
      this.FormData.tableData = this.tableData;
      console.log(this.FormData.tableData);
    },
    doQuery() {
      findAll().then(res => {
        this.tests = res.data.data;
      });
    },
    change() {
      this.tests.forEach(item => {
        if (this.testId == item.id) {
          this.remain = parseInt(item.remain);
          console.log(this.remain);
        }
      });
    },
    handleSelect() {
      if (this.testId == "") {
        this.$confirm("请选择科目", "警告", {
          confirmButtonText: "确定",
          type: "warning"
        });
      } else {
        this.$refs.testmessage.dialogFormV(this.testId, this.remain);
      }
    },
    handleUpload() {
      if (this.testId == "") {
        this.$confirm("请选择科目", "警告", {
          confirmButtonText: "确定",
          type: "warning"
        });
        return;
      }
      if (this.tableData.length == 0) {
        this.$confirm("你还未上传文件", "警告", {
          confirmButtonText: "确定",
          type: "warning"
        });
        return;
      }
      if (this.tableData.length > this.remain) {
        this.$confirm(
          "你所安排人数大于当前考试科目剩余人数，请重新安排",
          "警告",
          {
            confirmButtonText: "确定",
            type: "warning"
          }
        );
        return;
      }
      this.remain = this.remain - this.tableData.length;
      this.FormData.tableData = this.tableData;
      this.FormData.testId = this.testId;
      selectByAdmin(this.FormData).then(res => {
        if (res.data.success) this.$message.success("上传成功！");
      });
    }
  },
  created() {
    this.doQuery();
  }
};
</script>

<style lang="scss">
.import-excel-wrapper {
  .el-card {
    min-height: 400px;
  }
  .search-form {
    padding-top: 18px;
    margin-bottom: 15px;
    background-color: #f7f8fb;
  }
  .el-table thead {
    font-weight: 600;
    th {
      background-color: #f2f3f7;
    }
  }
  // .uplodButton{
  //   padding-right: 800px
  // }
}
</style>
