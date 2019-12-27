<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #90F7EC 10%, #32CCBC 100%);" class="card-panel">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">
            <p v-for="item in countData['duList']" v-if="item.platform" style="font-weight: bold;font-size: 13px">
              {{constantFormat(item.platform,PLATFORM_TYPE)+'：'+item.total}}</p>
          </div>
          <div class="wrapper">
            <div class="item1">激活用户总数</div>
            <div class="item5">
              <count-to :start-val="0" :end-val="countData['duCount']?parseInt(countData['duCount']):0"
                        :duration="2000" style="font-size: 25px"/>
            </div>
            <div class="item4">
              截止{{countData.currTime}}
            </div>
          </div>
        </el-tooltip>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient(to right, #ff9569 0%, #e92758 100%)" class="card-panel">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">
            <p v-for="item in countData['userList']" v-if="item.platform" style="font-weight: bold;font-size: 13px">
              {{constantFormat(item.platform,PLATFORM_TYPE)+'：'+item.total}}</p>
          </div>
          <div class="wrapper">
            <div class="item1">注册用户总数</div>
            <div class="item5">
              <count-to :start-val="0" :end-val="countData['userCount']?parseInt(countData['userCount']):0"
                        :duration="2000" style="font-size: 25px"/>
            </div>
            <div class="item4">
              截止{{countData.currTime}}
            </div>
          </div>
        </el-tooltip>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient(-90deg, #29bdd9 0%, #276ace 100%)" class="card-panel">
        <div class="wrapper">
          <div class="item1">付费用户总数</div>
          <div class="item5">
            <count-to :start-val="0" :end-val="countData['payOrderTotal']?parseInt(countData['payOrderTotal']):0"
                      :duration="2000" style="font-size: 25px"/>
          </div>
          <div class="item4">
            截止{{countData.currTime}}
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #81FBB8 10%, #28C76F 100%);" class="card-panel">
        <div class="wrapper">
          <div class="item1">卡片激活用户总数</div>
          <div class="item5">
            <count-to :start-val="0" :end-val="countData['vasTotal']?parseInt(countData['vasTotal']):0"
                      :duration="2000" style="font-size: 25px"/>
          </div>
          <div class="item4">
            截止{{countData.currTime}}
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #E2B0FF 10%, #9F44D3 100%);" class="card-panel">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">
            <p v-for="item in countData['orderList']" style="font-weight: bold;font-size: 13px">
              {{constantFormat(item.payStatus,PAY_STATUS)+'：'+item.total}}</p>
          </div>
          <div class="wrapper">
            <div class="item1">订单总数</div>
            <div class="item5">
              <count-to :start-val="0" :end-val="countData['orderCount']?parseInt(countData['orderCount']):0"
                        :duration="2000" style="font-size: 25px"/>
            </div>
            <div class="item4">
              截止{{countData.currTime}}
            </div>
          </div>
        </el-tooltip>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #2AFADF 10%, #4C83FF 100%);" class="card-panel">
        <div class="wrapper">
          <div class="item1">昨日新增H5注册用户总数</div>
          <div class="item5">
            <count-to :start-val="0" :end-val="countData['yesUserNum']?parseInt(countData['yesUserNum']):0"
                      :duration="2000" style="font-size: 25px"/>
          </div>
          <div class="item4">
            截止{{countData.currTime}}
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #FFA8A8 10%, #FCFF00 100%);" class="card-panel">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">
            <p v-for="item in countData['yesDuList']" v-if="item.platform" style="font-weight: bold;font-size: 13px">
              {{constantFormat(item.platform,PLATFORM_TYPE) +'：'+item.total}}</p>
            <p v-if="!countData['yesDuList'] || countData['yesDuList'].length === 0 ">无数据</p>
          </div>
          <div class="wrapper">
            <div class="item1">昨日新增App激活用户总数</div>
            <div class="item5">
              <count-to :start-val="0" :end-val="countData['yesDuCount']?parseInt(countData['yesDuCount']):0"
                        :duration="2000" style="font-size: 25px"/>
            </div>
            <div class="item4">
              截止{{countData.currTime}}
            </div>
          </div>
        </el-tooltip>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div style="background-image: linear-gradient( 135deg, #FFF5C3 10%, #9452A5 100%);" class="card-panel">
        <div class="wrapper">
          <div class="item1">H5推广渠道总数</div>
          <div class="item5">
            <count-to :start-val="0" :end-val="countData['channelCount']?parseInt(countData['channelCount']):0"
                      :duration="2000" style="font-size: 25px"/>
          </div>
          <div class="item4">
            截止{{countData.currTime}}
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import CountTo from 'vue-count-to'
  import {PAY_STATUS, PLATFORM_TYPE, constantFormat, constantTag} from '@/utils/constant'

  export default {
    components: {
      CountTo
    },
    data() {
      return {
        PAY_STATUS,
        PLATFORM_TYPE,
        constantFormat,
        constantTag
      };
    },
    props: {
      value: {
        type: Object
      }
    },
    computed: {
      countData: {
        get() {
          return this.value
        },
        set(val) {
          this.$emit('input', val)
        }
      }
    },
    methods: {}
  }
</script>

<style lang="scss">
  .panel-group {
    margin-top: 10px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 140px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 30px;
        }
      }
    }
  }

  .wrapper {
    display: grid;

    .item1 {
      margin-top: 20px;
      grid-column-start: 1;
      grid-column-end: 4;
      text-align: center;
      color: rgba(0, 0, 0, 0.55);
      font-size: 18px;
      font-weight: bold;
    }

    .item2 {
      margin-top: 10px;
      grid-column-start: 1;
      grid-column-end: 2;
      text-align: center;
      color: rgba(0, 0, 0, 0.9);
      font-size: 16px;
      font-weight: bold;
    }

    .item3 {
      margin-top: 10px;
      grid-column-start: 3;
      grid-column-end: 4;
      text-align: center;
      color: rgba(0, 0, 0, 0.9);
      font-size: 16px;
      font-weight: bold;
    }

    .item4 {
      margin-top: 10px;
      grid-column-start: 1;
      grid-column-end: 4;
      text-align: center;
      color: rgba(0, 0, 0, 0.25);
      font-size: 14px;
      font-weight: bold;
    }

    .item5 {
      padding: 10px 10px 0px 10px;
      grid-column-start: 1;
      grid-column-end: 4;
      text-align: center;
      color: rgba(0, 0, 0, 0.9);
      font-size: 14px;
      font-weight: bold;
    }

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .item1 {
        color: #ee9900;
      }

      .item2 {
        color: #36a3f7;
      }

      .item3 {
        color: #f4516c;
      }

      .item4 {
        color: #13ce66;
      }

      .item5 {
        color: #f4516c;
      }
    }
  }
</style>
