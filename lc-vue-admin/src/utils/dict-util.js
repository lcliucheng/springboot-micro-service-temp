import {MessageBox, Message} from 'element-ui'

/**
 * 修改字典值
 * */
export  function changeDict(code,value) {
  let param = new URLSearchParams();
  param.append("value", value);
   this.$axios.post('/dicts/updateByCode/'+code,param,
    {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(response => {
    if (response.code == 200) {
      Message({message:'修改成功！', type: 'success'});
    } else {
      Message({message:'修改失败！', type: 'error'});
    }

  });
}

/**
 * 查询字典值
 * */
export async function  getDictByCode(code) {
  let value;
  await this.$axios.get('/dicts/findByCode/' + code).then(response => {
    if (response.code == 200) {
      if (response.data[0]) {
        value =  response.data[0].value;
      } else {
        Message({message:'查询失败！', type: 'error'});
      }
    }
  });
  return value;
}


export default {
  changeDict,
  getDictByCode
}
