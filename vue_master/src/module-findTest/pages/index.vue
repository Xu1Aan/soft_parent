<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <el-tabs v-model="activeName" class="infoPosin">
            <el-tab-pane name="first" class="rInfo">
              <span slot="label">考试信息</span>
              <component v-bind:is="testInfo" :objId='objId' ref="user"></component>
            </el-tab-pane>
            <el-tab-pane name="two" class="rInfo">
                <span slot="label">教师任务</span>
                <component v-bind:is="teacherInfo" :objId='objId' ref="user"></component>
            </el-tab-pane>
        </el-tabs>
      </el-card>
     </div>
  </div>
</template>

<script>
import testInfo from './../components/testInfo'
import teacherInfo from './../components/teacherInfo'

  export default {
     components: { testInfo, teacherInfo},
     data() {
      return {
        testInfo:'testInfo',
        teacherInfo: 'teacherInfo',
        activeName: 'first',
      objId: this.$route.params.id,
      }
    },
    methods: {  
      
    }

  }

</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.never{
  width: auto;
}
.alert {
  margin: 10px 0px 0px 0px;
}
.pagination {
  margin-top: 10px;
  text-align: right;
}
.el-tabs__content {
  overflow: initial;
}
.logInfo {
  width: 250px;
  float: right;

  h2 {
    margin: 0 0 0 20px;
    padding: 0 0 0 20px;
    height: 40px;
    line-height: 40px;
    border-bottom: 2px solid #e4e7ed;
    font-size: 14px;
    font-weight: normal;
  }
}
.logList {
  li {
    list-style: none;
    padding-left: 80px;
    .logRinfo {
      position: relative;
      padding: 25px 0 0;
      margin-left: 15px;
      .time {
        position: absolute;
        left: -100px;
        top: 25px;
      }
      .logContent {
        position: relative;
      }
      .logContent:before {
        content: '';
        width: 14px;
        height: 14px;
        display: block;
        border-radius: 50px;
        position: absolute;
        left: -24px;
        top: 0;
        z-index: 20;
        border: 1px solid #26a69a;
        background: #fff;
      }
    }
    .logRinfo:before {
      content: '';
      position: absolute;
      left: -18px;
      top: -15px;
      width: 2px;
      height: 40px;
      background-color: #e0e0e0;
    }
  }
}

</style>
