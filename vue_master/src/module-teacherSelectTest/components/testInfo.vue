<template>
  <div class="add-form"> 
    <el-dialog title="查看考试" :visible.sync="dialogFormVisible">
      <div class="leaveInfo">
        <el-row>
          <el-col :span="20">
            <div class="headInfo">
              <p><strong><span>考试名称:</span>{{formData.subject}}</strong></p>
              <p><span>考试日期:</span><em>{{formData.date}}</em></p>
              <p><span>考试时间:</span><em>{{formData.time}}</em></p>
              <p><span>考试类型:</span><em>{{formData.type}}</em></p>
              <p><span>监考人数:</span><em>{{formData.number}}人</em></p>
               <p><span>剩余监考人数:</span><em>{{formData.remain}}人</em></p>
               <p><span>考场:</span><em>{{formData.address}}</em></p>
            </div>
          </el-col>
        </el-row>
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

import {detailtest,selecttest} from "@/api/base/users"
export default {
  name: 'selectTest',

  data() {
    return {
        dialogFormVisible: false,
        formData:[],
        address:'',
        testData:{
          testId:'',
          addressId:''
        },
    }
  },
  methods: {
    // 业务方法
    // 弹层显示
    dialogFormV(id) {
      this.address="";
      this.testData.testId = id;
      detailtest({ id: id }).then(res => {
        this.formData = res.data.data;
      })
      this.dialogFormVisible = true
    },
    // 表单提交
    formBtn() {
        this.dialogFormVisible = false
    }
  },
}
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
p{
  margin-top:10%;
  padding-top:10px;
}
}
.selectAddress{
  padding-left:5%;
}
.dialog-footer{
   padding-top:2%;
}
.headInfo{
    margin-left: 0%;
}
</style>
