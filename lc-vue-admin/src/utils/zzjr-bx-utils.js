import store from '../store'
import router from '../router'
import axios from "axios/index";
import { getToken } from '@/utils/auth'
import { constantFormat, constantTag,changeStatus,PLATFORM_TYPE} from '@/utils/constant'

const baseUrl = process.env.VUE_APP_BASE_API;

//保存前处理
export function preDeal(params,isOs,isApk) {
  let par = '';
  let json = null;
  if(!isOs){
    //处理 os 和 apks
    if (params['os'] != null && params['os'] !== '') {
      json = 'os,,STRING,1,' + params['os'];
      delete params['os'];
    }
  }
  if(!isApk){
    if (params['apks'] != null && params['apks'] !== '') {
      if (json != null) {
        json += ';' + 'apks,$[*].packageName,STRING,1,'+ params['apks'];
      } else {
        json = 'apks,$[*].packageName,STRING,1,'+ params['apks'];
      }
      delete params['apks'];
    }
  }

  for (var key in params) {
    //只遍历对象自身的属性，而不包含继承于原型链上的属性。
    if (params.hasOwnProperty(key) === true) {
      if (params[key] != null && params[key] !== '' && !(params[key] instanceof Array)) {
        par += (key) + '=' + (params[key]) + '&';
      }
    }
  }

  if(json != null){
    par += 'json=' + json;
  }
  let sortAry = params['sort'];
  if(sortAry != null && sortAry.length > 0 ){
    let sortStr = '&sort=';
    for (let i = 0; i < sortAry.length; i++) {
      sortStr += sortAry[i];
      if(i != sortAry.length -1){
        sortStr+=";";
      }
    }
    par+=sortStr;

  }
  return par;
}

//关闭当前标签页
export function closeCurrTag(route) {
  const views = store.state.tagsView.visitedViews;
  let view = null;
  for (var item in views) {
    if (route.path == views[item].path) {
      view = views[item];
      break;
    }
  }
  store.dispatch('tagsView/delView', view).then(({visitedViews}) => {
    if (view.path === route.path) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        router.push(latestView)
      } else {
        // now the default is to redirect to the home page if there is no tags-view,
        // you can adjust it according to your needs.
        if (view.name === 'Dashboard') {
          // to reload home page
          router.replace({path: '/redirect' + view.fullPath})
        } else {
          router.push('/')
        }
      }
    }
  })
}

//关闭指定的标签页
export function closeAppointView(route,redirect) {
  const views = store.state.tagsView.visitedViews;
  let view = null;
  for (var item in views) {
    if (route.path == views[item].path) {
      view = views[item];
      break;
    }
  }
  let listView;
  for (var item in views) {
    if(isString(redirect) ){
      if (redirect == views[item].path) {
        listView = views[item];
        break;
      }
    }else{
      if (views[item].path.indexOf(redirect) != -1 ) {
        listView = views[item];
        break;
      }
    }
  }
  if(listView){
    store.dispatch('tagsView/delView', listView);
  }
  store.dispatch('tagsView/delView', view).then(({visitedViews}) => {
    if (view.path === route.path) {
        router.push(redirect)
    }
  })
}

//判断是不是字符串
export function isString(str){
  return (typeof str=='string')&&str.constructor==String;
}

//获取包名
export async function getApks() {
  let appList = []
  await axios.get('/apk/all', {
    params: {"deleted": 0}
  }).then(response => {
    if (response.code == 200) {
      appList = response.data;
    }
  })
  return appList;

}


export function dealApkData(postForm) {
  if(postForm.platform && postForm.platform.length > 0){
    postForm.apks = {platform:postForm.platform};
  }
}

export function joinParam(params) {

  let json = null;
  //处理 os 和 apks
  if (params['os'] != null && params['os'] !== '') {
    json = 'os,,STRING,1,' + params['os'];
    delete params['os'];
  }
  if (params['apks'] != null && params['apks'] !== '') {
    if (json != null) {
      json += ';' + 'apks,$[*].packageName,STRING,1,'+ params['apks'];
    } else {
      json = 'apks,$[*].packageName,STRING,1,'+ params['apks'];
    }
    delete params['apks'];
  }

  if(json != null){
    params['json'] = json;
  }
  let par = '';

  for(var key in params){
    //只遍历对象自身的属性，而不包含继承于原型链上的属性。
    if (params.hasOwnProperty(key) === true){
      if(params[key] != null ){
        if(!(params[key] instanceof Array)){
          if((params[key]+'').length > 0){
            par+=(key)+'='+(params[key])+'&';
          }
        }else{
          let value = params[key];
          if(value != null && value.length > 0){
            value.forEach(item => {
              par+=(key)+'='+item+'&';
            })
          }
        }
      }
    }
  }


  par.replace("&&","&");
  return par;
}



// 导出模板
export function exportData(exportUrl,param) {
  //查询条件
  let paramStr = joinParam(param);
  window.open(baseUrl+exportUrl+paramStr+"&Authorization="+getToken());
}
export function  isBlank(str) {
  if(str==null || str.length ==0){
      return true;
  }

  return false;
}

//获取文件上传请求路径
export function getUploadPath() {
  return process.env.VUE_APP_BASE_API +'upload?Authorization='+getToken();
}

//获取文件上传请求路径
export function getUploadPathExtend(val) {
  return process.env.VUE_APP_BASE_API + val +'?Authorization='+getToken();
}

//金额 分转元 格式化

export function amountFormat(val){
//金额转换 分->元 保留2位小数 并每隔3位用逗号分开 1,234.56
  let str = (val/100).toFixed(2) + '';
  let intSum = str.substring(0,str.indexOf(".")).replace( /\B(?=(?:\d{3})+$)/g, ',' );//取到整数部分
  let dot = str.substring(str.length,str.indexOf("."))//取到小数部分搜索
  let ret = intSum + dot;
  return ret;
}

//获取值在数组中的位置
export function getIndex(arr, value) {
  var i = arr.length;
  while (i--) {
    if (arr[i] === value) {
      return i;
    }
  }
  return false;
}

export function convertAppIdToName(appId) {
  let apkMap = store.state.permission.apkMap;
  let apk = apkMap[appId];
  if(apk) {
      return apk.name;
  }
  return "未知";
}

//处理应用名称
export function packageConvertAppName(row,apkMap) {
  let apkName = '';
  if(row.apks && apkMap) {
    row.apks.forEach(item => {
    let appName = apkMap[item.appId];
    if(appName){
      apkName += appName.name + ','
    }
  });
}
  return apkName.substr(0, apkName.length - 1);
}

export function packageConvertPlatform(row,apkMap) {
  let apkName = '';
  if(row.apks && apkMap) {
    row.apks.forEach(item => {
      let appName = apkMap[item.appId];
      if(appName){
        apkName += appName.name +'[';
        if(item.platform) {
          item.platform.forEach(platform => {
            apkName +=constantFormat(platform,PLATFORM_TYPE)+",";
          });
        }
        apkName = apkName.substr(0, apkName.length - 1);
        apkName += '],';
      }
    });
  }
  return apkName.substr(0, apkName.length - 1);
}

//处理包名和名称转换
export function packageCconvertName(row) {
  let apks = row.apks;
  let apkName = '';
  if(apks && apks.platform){
    apks.platform.forEach(item => {
      let dict = PLATFORM_TYPE.find(platform => item == platform.value);
      if(dict){
        apkName += dict.label + ','
      }
    })
  }
  return apkName.substr(0, apkName.length - 1);
}

/**
 * 点击关联应用更新终端类型
 * */
export function updateOsType(apks) {

  let osTypeList = [];
  for (let j = 0; j < apks.length; j++) {
    let appInfo = store.state.permission.apkMap[apks[j]];
    if(appInfo && appInfo.osList){
      for (let i = 0; i < appInfo.osList.length; i++) {
        let index = osTypeList.findIndex(item => item === appInfo.osList[i]);
        if(index === -1){
          osTypeList.push(appInfo.osList[i]);
        }

      }
    }
  }

  return osTypeList;
}

/**
 * 获取一级渠道
 * */
export async function getChannelUser(){
  let channelList = [];
  await axios.get('/users/getAll',{params: {type:"channel"}}).then(response => {
    if (response.code == 200) {
      channelList = response.data;
    } else {
      this.$message({message: response.msg, type: 'error'})
    }
  });
  return channelList;
}

export default {
  preDeal,
  closeCurrTag,
  closeAppointView,
  getApks,
  dealApkData,
  joinParam,
  exportData,
  isString,
  isBlank,
  getUploadPath,
  amountFormat,
  getIndex,
  convertAppIdToName,
  packageCconvertName,
  packageConvertAppName,
  packageConvertPlatform,
  updateOsType,
  getChannelUser
}
