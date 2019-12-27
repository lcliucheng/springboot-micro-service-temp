<template>
  <div class="dashboard-editor-container">
    <el-col :xs="{span: 24}" :sm="{span: 24}" style="padding-right:8px;margin-bottom:20px;">
      <span style="font-size: 18px">数据统计概览</span><span
      style="margin-left: 20px;font-size: 14px;color: rgba(0, 0, 0, 0.25)">截止{{currTime}}</span>
    </el-col>
    <panel-group  v-model="countData" v-if="countData"/>

    <el-row :gutter="8">
      <el-col :xs="{span: 24}" :sm="{span: 24}" style="padding-right:8px;margin-bottom:20px;">
        <span style="font-size: 18px">所有H5渠道PV和UV排行榜</span><span
        style="margin-left: 20px;font-size: 14px;color: rgba(0, 0, 0, 0.25)">截止{{currTime}}</span>
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}">
        <div style="text-align: center">
          <span style="font-weight: bold;">所有H5推广渠道着陆页访问PV和UV排行</span>
        </div>
        <el-table
          style="margin-top: 10px;"
          :data="openList"
          highlight-current-row>
          <el-table-column label="排名" type="index" align="center">
          </el-table-column>
          <el-table-column label="渠道名称" width="350px" prop="name" align="center">
          </el-table-column>
          <el-table-column label="PV" prop="pv" align="center">
          </el-table-column>
          <el-table-column label="UV" prop="uv" align="center">
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-col :xs="{span: 24}" :sm="{span: 24}" style="padding-right:8px;margin-top:20px;">
      <span style="font-size: 18px">所有H5一级渠道用户数据统计</span><span
      style="margin-left: 20px;font-size: 14px;color: rgba(0, 0, 0, 0.25)">截止{{currTime}}</span>
    </el-col>
    <BarChart v-model="barList"/>
    <div style="height: 100px"></div>


  </div>
</template>

<script>
  import PanelGroup from './components/PanelGroup'
  import TransactionTable from './components/TransactionTable'
  import BarChart from './components/BarChart'
  import format from "@/utils/date-util";


  export default {
    name: 'DashboardAdmin',
    components: {
      PanelGroup,
      TransactionTable,
      BarChart
    },
    data() {
      return {
        openList: [],
        barList: [],
        countData: null,
        currTime: null
      }
    },
    created() {
      let date = new Date();
      this.currTime = format.formatDateTime(new Date(), "yyyy-MM-dd hh:mm:ss");
      this.getList();
    },
    methods: {
      getList() {
        //请求后台接口
        this.$axios.get('/admin/index').then(response => {
          let result = response.data;
          if (response.code == 200) {
            this.countData = result;
            this.openList = result.dataList;
            this.barList = result.barList;
            this.countData['currTime'] = this.currTime;
          } else {
            this.$message({
              message: result.msg,
              type: 'error'
            })
          }
        })
      }
    }
  }
</script>


<style lang="scss" scoped>

  .dashboard-editor-container {
    padding: 10px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .github-corner {
      position: absolute;
      top: 0px;
      border: 0;
      right: 0;
    }

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }

    .transition-box {
      margin-top: 10px;
      margin-bottom: 10px;
      width: 260px;
      height: 120px;
      border-radius: 4px;
      background-color: #409EFF;
      text-align: center;
      color: #fff;
      padding: 40px 20px;
      box-sizing: border-box;
      margin-right: 20px;
    }
  }
</style>
