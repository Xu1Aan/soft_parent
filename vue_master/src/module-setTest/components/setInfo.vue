<template>
  <div class="boxInfo">
    <!-- 表单内容 -->
    <div class="formInfo">
      <div>
        <!-- 头部信息  -->
        <div class="userInfo">
             <div class="headInfo clearfix">
               <div class="headText">
                   <el-form ref="form" :model="form" label-width="100px">
                    <el-form-item label="考试名称" >
                        <el-input v-model="form.subject" style="width: 51%;"  placeholder="请输入考试名称（必填）"></el-input>
                    </el-form-item>
                    <el-form-item label="考试类型">
                        <el-select v-model="form.type" placeholder="请选择考试类型（必填）">
                        <el-option label="公共考试" value="公共考试"></el-option>
                        <el-option label="专业考试" value="专业考试"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="考试日期" >
                        <el-col :span="10">
                        <el-date-picker type="date" placeholder="选择日期（必填）" v-model="form.date"  label-width="100px"></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">考试时间</el-col>
                        <el-col :span="10">
                        <el-time-select v-model="form.time" 
                            :picker-options="{
                            start: '07:00',
                            step: '00:30',
                            end: '21:00'
                            }"
                            label-width="100px"
                            placeholder="选择时间（必填）">
                          </el-time-select>
                        </el-col>
                    </el-form-item>
                        <el-form-item label="指定监考教师" >
                        <el-select v-model="form.teacher"  multiple  filterable placeholder="请指定任课教师（可为空）">
                        <el-option
                        v-for="item in teachers"
                        :key="item.id"
                        :label="item.username"
                        :value="item.id"
                        >
                        </el-option>
                    </el-select>
                    </el-form-item>
                      <el-form :model="addressForm" ref="addressForm" label-width="auto" class="demo-dynamic address" >
                        <el-form-item
                            v-for="(address, index) in addressForm.address"
                            :label="'考试地点' + index"
                            :key="address.key"
                            :prop="'address.' + index + '.value'"
                            :rules="{
                                required: true, message: '考试地点不能为空', trigger: 'blur'
                            }"
                        > 
                        
                            <el-input v-model="address.value" style="width: 25%;" placeholder="请输入考试地点（可删除）"></el-input>
                            <span class="jiankao"> 监考人数 </span>
                            <el-input-number v-model="address.number" 
                             :min="0" :max="10" label="选择监考人数"></el-input-number>
                            
                            <el-button @click.prevent="removeAddress(address)">删除</el-button>
                            
                            </el-form-item>
                          
                            <el-form-item>
                            <el-button @click="addAddress">新增地址</el-button>
                            </el-form-item>
                        </el-form>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">立即创建</el-button>
                        <el-button type="reset" @click="onReset">重置</el-button>
                    </el-form-item>
                    </el-form>
               </div>
             </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import {savetest,simple} from "@/api/base/users"
export default {
  name: 'setInfo',
  props: ['objId'],
  data() {
    return {
        form: {
          subject:'',
          type:'',
          date:'',
          time:'',
          teacher:[],
          remain:'',
          address:[],
          numbers:[],

        },
        addressForm: {
          address: [{
            key:'1',
            value: '',
            number:''
          }]
        },
        teachers:[],
    }
  },
  methods: {

   addAddress() {
        this.addressForm.address.push({
          value: '',
          number:0,
          key: Date.now()
        });
      },
      removeAddress(item) {
        var index = this.addressForm.address.indexOf(item)
        if (index !== -1) {
          this.addressForm.address.splice(index, 1);
        }
      },
        onSubmit() {
        //this.form.address = this.addressForm.address
        var i = 0; 
        this.form.remain = 0;
        this.addressForm.address.forEach(element => {
           this.form.address[i] = element.value;
           this.form.numbers[i++] =  element.number;
           this.form.remain = parseInt(this.form.remain) + parseInt(element.number);
        });
        
        if(this.form.subject == '' || this.form.type ==''  || this.form.date =='' || this.form.time ==''
          || this.form.remain =='' ){
            this.$message("请录入完整信息");
            return ;
          }
        savetest(this.form).then(res => {
        this.$message({message:res.data.message,type:res.data.success?"success":"error"});
      })
      },
      getTeacher(){
        simple().then(res=>{
          this.teachers = res.data.data
        })
      },
      onReset(){
        Object.assign(this.$data.form, this.$options.data().form);
      },   
    }, 
    created(){
      this.getTeacher();
    }


}
</script>

<style rel="stylesheet/scss" lang="scss">
.el-collapse-item__arrow {
  float: left;
}

.el-collapse-item {
  position: relative;
  // width: 80%;
  // .el-collapse-item__header{width: 80%;}
  .infoR {
    position: absolute;
    background: #fff;
    display: inline-block;
    width: 100px;
    height: 35px;
    line-height: 35px;
    text-align: right;
    right: -100px;
    top: 0px;
  }
}
.line{
  margin-left: -10%;
}
.line2{
  margin-left: -30%;
}
.address{
  padding-left: 1%;
  margin-right: 20%;
  
}
.linkage {
  display: inline-block;
}
.textBotm {
  vertical-align: text-bottom;
}
.navInfo {
  height: auto;
  font-size: 30px;
  color: #333;
  background-color: #e4e4e4;
  text-align: center;
  border-bottom: 1px solid #333;
}
.step {
  position: fixed;
  left: 220px;
  top: 50%;
  margin-top: -150px;
  background: #fff;
  z-index: 9;
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
.jiankao {
padding-left: 125px;
padding-right: 35px;
}
</style>
