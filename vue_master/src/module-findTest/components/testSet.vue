<template>
  <div class="add-form"> 
    <el-dialog title="查看已选教师" :visible.sync="dialogFormVisible">
      <div class="leaveInfo">
              <!-- :span-method="objectSpanMethod" -->
      <span>考试科目：{{subject}}</span>
 <el-table
      :data="formData"

      border
      style="width: 100%; margin-top: 20px">
      <el-table-column
        prop="address"
        label="考试地点"
        width="200"
        align='center'
        >
      </el-table-column>
      <el-table-column
        prop="username"
        label="监考教师姓名"
        align='center'
        >
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="联系方式"
        align='center'
        >
      </el-table-column>
        <el-table-column
      fixed="right"
      label="操作"
      align='center'
      >
      <template slot-scope="scope">
        <el-button @click="handlCancel(scope.row)" type="text" size="small">退选</el-button>
      </template>
      </el-table-column>
    </el-table>
      </div>
      <div class="selectAddress">
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="formBtn">确定</el-button>
      </div>
      </div>
    </el-dialog>
    
  </div>
</template>

<script>
import {cancelbyadmin, testinfo, selecttest } from "@/api/base/users";

export default {
  name: "selectTest",
   props: ['callback'],
  data() {
    return {
      dialogFormVisible: false,
      formData: [],
      subject: "",
      testData: {
        testId: "",
        addressId: ""
      }
    };
  },
  methods: {
    // 业务方法
    // 弹层显示
    dialogFormV(id, subject) {
      this.subject = subject;
      this.testData.testId = id;
      testinfo({ id: id }).then(res => {
        this.formData = res.data.data.testInfo;
      });
      this.dialogFormVisible = true;
    },
    //确定
    formBtn() {
      this.dialogFormVisible = false;
    },
    handlCancel(item) {
      this.$confirm(`正在为${item.username}老师退选，您确认退选吗？`, {
        type: "warning"
      }).then(() => {
        cancelbyadmin({id: item.testId,userId:item.userId}).then(res => {
          if (res.data.success) this.$message.success("退选成功！");
          this.dialogFormV(this.testData.testId , this.subject);
          this.callback();
        });
      });
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 2 === 0) {
          return {
            rowspan: 2,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.inputText {
  width: 400px;
  height: 32px;
  resize: none;
  line-height: 22px;
  overflow: hidden;
  font-size: 12px;
  border: 1px solid #dddee1;
  padding: 4px 7px;
  border-radius: 5px;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
.upload-list {
  padding: 0;
  margin: 0;
  li {
    list-style: none;
  }
  p {
    margin-top: 10%;
    padding-top: 10px;
  }
}
.selectAddress {
  padding-left: 5%;
}
.dialog-footer {
  padding-top: 2%;
}
.headInfo {
  margin-left: 0%;
}
</style>
