<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    value: {
      type: Array
    }
  },
  computed: {
    barList: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      let initCharts = echarts.init(this.$el, 'macarons');
      let xdata = [];
      this.barList.forEach(item => xdata.push(item.name));
      let registerNum = [];
      this.barList.forEach(item => registerNum.push(item.userRegister));
      let orderSucc = [];
      this.barList.forEach(item => orderSucc.push(item.orderSucc));
      let buyUser = [];
      this.barList.forEach(item => buyUser.push(item.buyUser));
      let activate = [];
      this.barList.forEach(item => activate.push(item.activate));
      let option = {
        tooltip : {
          trigger: 'axis',
          axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data: ['总注册用户数', '支付成功订单总数','总购买用户数','总激活用户数']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        yAxis:  {
          type: 'value'
        },
        xAxis: {
          type: 'category',
          data: xdata,
          axisLabel:{
            interval:0,//横轴信息全部显示
          }
        },
        series: [
          {
            name: '总注册用户数',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: registerNum
          },
          {
            name: '支付成功订单总数',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: orderSucc
          },
          {
            name: '总购买用户数',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: buyUser
          },
          {
            name: '总激活用户数',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                position: 'insideRight'
              }
            },
            data: activate
          }
        ]
      };
      initCharts.setOption(option)
    }
  },
  watch: {
    value: {
      handler(){
        this.initChart()
      },
      deep: true
    }
  }
}
</script>
