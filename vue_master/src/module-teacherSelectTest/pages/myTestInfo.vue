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
                :data="listData"
                border
                style="width:100%"
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
                    <el-button @click="handleInfo(scope.row.id)" type="text" size="small">查看</el-button>
                    <el-button @click="handlCancel(scope.row)" type="text" size="small">退选</el-button>
                  </template>
                  </el-table-column>
                </el-table>

                 <component v-bind:is="testInfo" ref="testInfo"></component>

              </div>
            </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import {mytest,canceltest} from "@/api/base/users"
import testInfo from './../components/testInfo'
export default {
  name: 'myTestInfo',
  components: {testInfo},
  props: ['objId'],
  data() {
    return {
      testInfo:'testInfo',
      requestParameters:{
        page: 1,
        size: 10,
      },
      listData: [],
    }
  },
  methods: {
    doQuery(){  
        mytest().then(res => {
          this.listData = res.data.data;
        })
      },
      handleInfo(id) {
        this.$refs.testInfo.dialogFormV(id);
        this.doQuery();
    },
     handlCancel(item) {
        this.$confirm(
        `正在退选《${item.subject}》，您确认退选吗？`,{
         type: 'warning'
        }
      ).then(() => {
       canceltest({id :item.id}).then(res=>{
         if(res.data.success)
          this.$message.success('退选成功！');
         this.doQuery();
       }) 
      })  
    },
   
  },
  // 创建完毕状态
    created(){
      this.doQuery();
    }
}
</script>

<style rel="stylesheet/scss" lang="scss">
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
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
