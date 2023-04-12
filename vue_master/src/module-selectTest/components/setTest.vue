<template>
  <div class="add-form"> 
    <el-dialog title="编辑考试" :visible.sync="dialogFormVisible">
        <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="考试名称" >
                        <el-input v-model="form.subject" style="width: 51%;"  placeholder="请输入考试名称"></el-input>
                    </el-form-item>
                    <el-form-item label="考试类型">
                        <el-select v-model="form.type" placeholder="请选择考试类型">
                        <el-option label="公共考试" value="公共考试"></el-option>
                        <el-option label="专业考试" value="专业考试"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考试日期" >
                        <el-col :span="11">
                        <el-date-picker type="date" placeholder="选择日期" v-model="form.date"  label-width="100px"></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="3">考试时间</el-col>
                        <el-col :span="10">
                          <el-time-select v-model="form.time" 
                            :picker-options="{
                            start: '08:00',
                            step: '00:30',
                            end: '21:00'
                            }"
                            label-width="100px"
                            placeholder="选择时间">
                          </el-time-select>
                         
                        </el-col>
                    </el-form-item>
                        <el-form-item label="已选监考教师" >
                        <el-select v-model="teachers"  multiple  filterable placeholder="请选择任课教师">
                        <el-option
                        v-for="item in form.simpleResults"
                        :key="item.id"
                        :label="item.username"
                        :value="item.id">
                        </el-option>
                    </el-select>
                    </el-form-item>
                      <el-form :model="form" ref="form" label-width="auto" class="demo-dynamic address" >
                        <el-form-item
                            v-for="(address, index) in form.testAddresses"
                            :label="'考试地点' + index"
                            :key="address.id"
                            :prop="'testAddresses[' + index + '].address'"
                            :rules="{
                                required: true, message: '考试地点不能为空', trigger: 'blur'
                            }"
                        >
                            <el-input v-model="address.address" style="width: 37%;">
                              </el-input>
                              <span class="jiankao"> 监考人数 </span>
                            <el-input-number v-model="address.number" style="width: 20%;"
                             :min="0" :max="10" label="选择监考人数">
                             </el-input-number>
                              <el-button @click.prevent="removeAddress(address)">删除</el-button>
                            </el-form-item>
                            <el-form-item>
                            <el-button @click="addAddress">新增地址</el-button>
                            </el-form-item>
                        </el-form>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">确定修改</el-button>
                        <el-button type="reset" @click="onCancel">取消</el-button>
                    </el-form-item>
                    </el-form>
    </el-dialog>
  </div>
    
</template>

<script>
import { settest, updatetest } from "@/api/base/users";
// import testSelectInfo from './../pages/testSelectInfo'
export default {
  name: "selectTest",
  props: ["objId", "callback"],
  // components: {testSelectInfo},
  data() {
    return {
      // testSelectInfo:'testSelectInfo',
      dialogFormVisible: false,
      test: [],
      form: {
        id: "",
        subject: "",
        type: "",
        date: "",
        time: "",
        teacher: [],
        number: "",
        remain:'',
        testAddresses: {}
      },
      teachers: []
    };
  },
  methods: {
    // 业务方法
    // 弹层显示
    dialogSetTest(id) {
      this.form.id = id;
      settest({ id: id }).then(res => {
        this.form = res.data.data;
        this.form.originalNumber = this.form.number;
        this.teachers = [];
        this.form.simpleResults.forEach(element => {
          this.form.testUsers.forEach(test => {
            if (element.id == test.userId) {
              this.teachers.push(element.id);
            }
          });
        });
      });
      this.dialogFormVisible = true;
    },
    // 表单提交
    onSubmit() {
      this.form.teacher = this.teachers;
      this.form.number = 0;
      this.form.testAddresses.forEach(element => {
        this.form.number = parseInt(this.form.number) + parseInt(element.number);
      });
      // var i = 0;
      // this.form.teacher.forEach(element => {
      //   i++;
      // });
      // this.remain = this.form.number - i;
      this.$confirm(`修改后系统将随机为已选教师分配考场，您确认修改吗？`, {
        type: "warning"
      }).then(() => {
        updatetest(this.form).then(res => {
          if (res.data.success) this.$message.success("修改成功！");
          this.callback();
        });
      });
      this.dialogFormVisible = false;
    },
    addAddress() {
      this.form.testAddresses.push({
        address: "",
        number: 0,
        ids: Date.now(),
        testId: this.form.id
      });
    },
    removeAddress(item) {
      var index = this.form.testAddresses.indexOf(item);
      if (index !== -1) {
        this.form.testAddresses.splice(index, 1);
      }
    },
    onCancel() {
      this.dialogFormVisible = false;
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
.headInfo {
  margin-left: 0%;
}
.jiankao {
  padding-left: 60px;
  padding-right: 20px;
}
</style>
