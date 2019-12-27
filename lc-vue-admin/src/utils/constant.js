/**
 * 常量格式化
 * @param value 值
 * @param constants 常亮
 * @returns {*}
 */
export function constantFormat(value, constants) {
  for (const constant of constants) {
    if (value === constant.value) {
      return constant.label
    }
  }
  return '未知'
}

/**
 * 常量格式化
 * @param value 值
 * @param constants 常亮
 * @returns {*}
 */
export function constantFormatNull(value, constants) {
  for (const constant of constants) {
    if (value === constant.value) {
      return constant.label
    }
  }
  return ''
}

/**
 * 常量标签
 * @param value 值
 * @param constants 常量
 * @returns {*}
 */
export function constantTag(value, constants) {
  for (const constant of constants) {
    if (value === constant.value) {
      return constant.tag
    }
  }
  return ''
}


export function changeStatus(url,param,message){
  this.$axios.put(url, JSON.stringify(param),
    {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
    if (response.code == 200) {
      if(message === 'disabled'){
        param.disabled === 0?this.$message({type: 'success', message: '启用成功!'}):this.$message({type: 'warning', message: '禁用成功!'});
      }else if(message === 'offline'){
        param.offline === 0?this.$message({type: 'success', message: '启用成功!'}):this.$message({type: 'warning', message: '禁用成功!'});
      }else if(message === 'recommend'){
        param.recommend === 1?this.$message({type: 'success', message: '上线成功!'}):this.$message({type: 'warning', message: '下线成功!'});
      }else{
        param.published === 1?this.$message({type: 'success', message: '上线成功!'}):this.$message({type: 'warning', message: '下线成功!'});
      }

    } else {
      this.$message({type: 'success', message: '修改失败!'});
    }
    this.getList();
  });
}

/**
 * 请求方法
 */
export const METHODS = [
  { value: 'GET', label: 'GET', tag: 'primary' },
  { value: 'POST', label: 'POST', tag: 'success' },
  { value: 'PUT', label: 'PUT', tag: 'warning' },
  { value: 'DELETE', label: 'DELETE', tag: 'danger' },
]

/**
 * 分页大小
 */
export const PAGE_SIZE =[10, 20, 30, 50]

/**
 * 是否禁用状态
 */
export const DISABLE_STATUS = [
  {value: 0, label: '启用',tag: 'success'},
  {value: 1, label: '禁用',tag: 'danger'}
]

/**
 * 是否上线状态
 */
export const PUBLISH_STATUS = [
  {value: 0, label: '下线',tag: 'danger'},
  {value: 1, label: '上线',tag: 'success'}
]

/**
 * 系统类型
 */
export const OS_TYPE = [
  {value: "Android", label: "Android"},
  {value: "iOS", label: "iOS"},
]

/**
 * 等级类型
 */
export const LEVEL_TYPE = [
    {value: "5A级", label: "5A级"},
    {value: "4A级", label: "4A级"},
    {value: "3A级", label: "3A级"},
    {value: "2A级及以下", label: "2A级及以下"},]


/**
 * 平台类型
 */
export const PLATFORM_TYPE = [
  {value: "Android", label: "Android"},
  {value: "iOS", label: "iOS"},
  {value: "APPLET", label: "小程序"},
  {value: "H5", label: "H5"}
]

/**
 * 支付状态
 */
export const PAY_STATUS = [
  {value: 0, label: '未支付',tag: 'danger'},
  {value: 1, label: '已支付',tag: 'success'},
  {value: 2, label: '支付中',tag: 'warning'},
  {value: 3, label: '退款中',tag: 'warning'},
  {value: 4, label: '退款失败',tag: 'danger'},
  {value: 5, label: '退款成功',tag: 'success'}
]

/**
 * 支付方式
 */
export const PAY_CHANNEL = [
  {value: "WX", label: '微信',tag: 'success'},
  {value: "AP", label: '支付宝'},
  {value: "UNICOM_H5", label: '联通'}

]

/**
 * 用户等级
 */
export const USER_LEVEL = [
  {value: 1, label: '普通用户'},
  {value: 2, label: 'VIP',tag: 'success'}
]

/**
 * 用户权益状态
 */
export const VAS_STATUS = [
  {value: 0, label: '未激活',tag: 'danger'},
  {value: 1, label: '已激活',tag: 'success'},
  {value: 2, label: '已过期',tag: 'warning'},
  {value: 3, label: '续费停用',tag: 'warning'},
  {value: 5, label: '中止权益',tag: 'warning'},
  {value: 4, label: '退款停用',tag: 'warning'},
]

/**
 * 注册来源
 */
export const REGISTER_SOURCE = [
  {value: 0, label: '平台注册'},
  {value: 1, label: '权益分享注册'},
  {value: 2, label: '渠道注册'}
]

/**
 * 用户类型
 */
export const USER_TYPE = [
  {value: "platform", label: '平台用户'},
  {value: "busi", label: '商务用户'},
  {value: "channel", label: '渠道用户'}
]

/**
 * 用户类型
 */
export const SYS_USER_TYPE = [
  {value: "platform", label: '平台用户'},
  {value: "busi", label: '商务用户'}
]

/**
 * banner展示位置类型
 */
export const BANNER_SHOW_TYPE = [
  {value: 1, label: '景区-顶部Banner',tag:'info'},
  {value: 2, label: '网红爆点-内页Banner',tag:'info'},
  {value: 3, label: '会盒首页-顶部',tag:'info'},
]

/**
 * 广告类型
 */
export const ADVERTISING_SHOW_TYPE = [
  {value: 1, label: '首页弹窗广告',tag:'info'},
  {value: 2, label: '启动页广告',tag:'info'},
  {value: 3, label: '首页悬浮窗广告',tag:'info'},
  {value: 4, label: '我的页面悬浮窗广告',tag:'info'},
]

/**
 * 链接类型
 */
export const LINK_TYPE = [
  {value: 0, label: '无跳转',tag:'info'},
  {value: 1, label: 'H5地址',tag:'info'},
  {value: 2, label: 'APP页面',tag:'info'},
  {value: 3, label: '景区详情',tag:'info'},
  {value: 4, label: '权益详情',tag:'info'},
]

/**
 * 更新类型
 */
export const UPDATE_TYPE = [
  {value: 1, label: "强制更新",tag: 'danger'},
  {value: 0, label: "非强制更新",tag: 'warning'}
]

/**
 * 用户等级
 */
export const USER_GRADE_TYPE = [
  {value: 1, label: '普通用户'},
  {value: 2, label: 'VIP',tag:'success'},
]

/**
 * 实体卡状态
 */
export const CARD_STATUS = [
  {value: 1, label: "未激活"},
  {value: 2, label: "已激活"}
]

/**
 * 是否注册
 */
export const IS_REGISTER = [
  {value: 1, label: "已注册"},
  {value: 0, label: '未注册'}
]

/**
 * 开团状态
 */
export const GROUP_STATUS = [
  {value: 1, label: "拼团中"},
  {value: 2, label: '拼团成功',tag:'success'},
  {value: 3, label: '拼团失败',tag: 'danger'},
  {value: 4, label: '人工终止',tag: 'danger'}
]

/**
 * 服务时长单位
 */
export const DURATION_TYPE = [
  {value: 1, label: "天"},
  {value: 2, label: '月'},
  {value: 3, label: '年'},
  {value: 4, label: '周'}
]

/**
 * 审核状态 0-初始状态 1-审核中 2-审核成功 3-审核失败
 */
export const AUDIT_STATUS = [
  {value: 0, label: "初始状态"},
  {value: 1, label: "审核中",tag:'warning'},
  {value: 2, label: '审核成功',tag:'success'},
  {value: 3, label: '审核失败',tag: 'danger'}
]

/**
 * 角色类型 1-开团人 2-参团人
 */
export const MEMBER_TYPE = [
  {value: 1, label: "团长"},
  {value: 2, label: '团员'}
]

/**
 * 服务时长单位 1:天 2:月 3:年 4:周
 */
export const CARD_SERVICE_TYPE = [
  {value: 1, label: "日卡"},
  {value: 2, label: '月卡'},
  {value: 3, label: '年卡'},
  {value: 4, label: '周卡'},
  {value: 6, label: '季卡'},
]

/**
 * 酒店类型 1：普通酒店 2：连锁酒店
 */
export const HOTEL_INFO_TYPE = [
  {value: 1, label: "普通酒店"},
  {value: 2, label: '连锁酒店'},
]


/**
 * 权益卡兑换码状态
 */
export const RIGHTS_CARD_STATUS = [
  {value: 1, label: "已提供给第三方"},
  {value: 2, label: '已购买'},
  {value: 3, label: "已使用"},
  {value: 4, label: '已失效'},
]

/**
 * 权益卡兑换码状态
 */
export const RIGHTS_STATUS = [
  {value: 1, label: "已导入"},
  {value: 2, label: '已领取'},
  {value: 3, label: "已失效"},
]

/**
 * 是否
 */
export const YES_NO = [
  {value: 0, label: '否',tag: 'danger'},
  {value: 1, label: '是',tag: 'success'}
]

/**
 * 权益卡色值
 */
export const RIGHTS_CARD_BACK_COLOR = [
  {value: '#BDFBEA', label: "绿色"},
  {value: '#C4ECFB', label: '蓝色'},
  {value: '#FFECCB', label: "橙色"},
  {value: '#FDD7CE', label: "红色"}
]

/**
 * 权益页面类型
 */
export const RIGHTS_PAGE_TYPE = [
  {value: "1", label: "首页"},
  {value: "2", label: '权益页'},
  {value: "3", label: "我的"},
]


/**
 * 订单类型
 */
export const ORDER_TYPE = [
  {value: 1, label: "权益首次购买"},
  {value: 2, label: '权益续费'},
  {value: 3, label: '旅游开团支付'},
  {value: 4, label: "码上有礼支付"},
  {value: 5, label: "参团支付"},
  {value: 6, label: '领取权益'},
  {value: 7, label: "会盒权益订单"},
  {value: 8, label: "秒杀"},
]


/**
 * 会盒二级订单类型
 */
export const HUI_HE_SEC_ORDER_TYPE = [
  {value: 1, label: "普通零售"},
  {value: 2, label: '会盒卡'},
  {value: 4, label: '拼团'},
  {value: 5, label: "会盒卡折扣权益"},
  {value: 6, label: "会盒卡免费权益"},
]
