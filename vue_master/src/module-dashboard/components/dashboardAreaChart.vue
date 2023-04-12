<template>
    <div id="main" class="pieReport" ></div>
  
</template>

<script>
import { taste } from "@/api/base/users";
import echarts from "echarts";
export default {
  name: "",
  data() {
    return {
      tastes: {},
      charts: "",
      opinion: ["已完成人数", "未完成人数"],
      type: "pie",
      opinionData: [
        { value: 400, name: "已完成人数" },
        { value: 600, name: "未完成人数" }
      ]
    };
  },
  methods: {
    drawPie(id) {
      this.charts = echarts.init(document.getElementById(id));
      this.charts.setOption({
        color: ["#A4B6C9", "#4E7095"] /*饼状图的颜色*/,
        tooltip: {
          trigger: "item",
          formatter: "{b}({d}%)" /*饼状图触碰之后显示的注释文字*/
        },
        legend: {
          left: "center" /*标签文字排成一行*/,
          y: "bottom" /*在垂直方向上的底部*/,
          data: this.opinion
        },
        series: [
          {
            type: "pie", //饼状图
            center: ["50%", "40%"], //显示位置
            name: "",
            type: "pie",
            radius: ["0%", "60%"] /*空心圆的占比*/,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              },
              normal: {
                borderWidth: 5 /*隔开的白色边界*/,
                borderColor: "#fff" /*border*/
                // labelLine :{show:true}
              }
            },

            avoidLabelOverlap: false,
            label: {
              normal: {
                show: true,
                position: "inner",
                textStyle: {
                  fontWeight: 200,
                  fontSize: 20 //文字的字体大小
                },
                formatter: "{d}%" /*饼状图内显示百分比*/
                // data:['40%','60%'],
              },

              emphasis: {
                show: false /*空心文字出现*/
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: this.opinionData
          }
        ]
      });
    }
  },
  //调用
  mounted() {
    taste().then(res => {
      this.tastes = res.data.data;
      this.opinionData[0].value = this.tastes.finishNumber;
      this.opinionData[1].value = this.tastes.unfinishNumber;
      this.$nextTick(function() {
        this.drawPie("main");
      });
    });
  }
};
</script>
<style>
.pieReport {
  width: 350px;
  height: 350px;
  margin: 0 auto;
}
</style>
