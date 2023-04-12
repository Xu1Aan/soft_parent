<template>
  <div class="add-form"> 
    <el-dialog title="查看已选教师" :visible.sync="dialogFormVisible">
      <div class="leaveInfo">
              <!-- :span-method="objectSpanMethod" -->
      <span>还有{{remain}}个监考名额，已选教师名单如下</span>
 <el-table
      :data="formData"
      border
      style="width: 100%; margin-top: 20px">
      <el-table-column
        prop="address"
        label="考试地点"
        width="200">
      </el-table-column>
      <el-table-column
        prop="username"
        label="监考教师姓名"
        >
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="联系方式"
        >
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
import { testinfo, selecttest } from "@/api/base/users";

export default {
  name: "selectTest",

  data() {
    return {
      dialogFormVisible: false,
      formData: [],
      subject: "",
      remain:0,
      testData: {
        testId: "",
        addressId: ""
      }
    };
  },
  methods: {
    // 业务方法
    // 弹层显示
    dialogFormV(id,remain) {
      this.remain = remain;
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
