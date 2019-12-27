<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
          <OfflineDropdown v-model="postForm.offline" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="服务编号"  prop="serviceId">
              <el-input v-model="postForm.serviceId" style="width: 30%"  placeholder="请输入服务编号" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="菜单编号"  prop="menuId">
            <el-input v-model="postForm.menuId" style="width: 30%"  placeholder="请输入菜单编号" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="名称"  prop="name">
            <el-input v-model="postForm.name" style="width: 30%"  placeholder="请输入名称" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="序号"  prop="sortNo">
            <el-input v-model.number="postForm.sortNo" style="width: 30%"  placeholder="请输入序号" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="方法"  prop="method">
              <el-select v-model="postForm.method" placeholder="请输入方法" clearable style="width: 30%" >
                <el-option v-for="item in METHODS"  :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="地址"  prop="url">
              <el-input v-model="postForm.url" style="width: 30%"  placeholder="请输入地址" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="描述"  prop="desc">
              <el-input v-model="postForm.desc" style="width: 30%"  placeholder="请输入描述" />
          </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件

  import {OfflineDropdown} from './Dropdown'
  import bxUtils from '@/utils/zzjr-bx-utils'
  import {METHODS} from '@/utils/constant'

  const defaultForm = {
    status:'draft',
    serviceId:'zzjr-insurance-service-admin',
    menuId:null,
    name:'',
    sortNo:null,
    method:'',
    url:'',
    desc:'',
    offline:0,
    deleted:0
  }

  export default {
    name:"资源管理",
    components: {Sticky,OfflineDropdown},
    data() {
      return {
        METHODS,
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          serviceId: [
            { required: true, message: '请输入服务编号', trigger: 'blur' },
            { max: 64, message: '最大长度为 64', trigger: 'blur' },
          ],
          menuId: [
            { required: true, message: '请输入菜单编号', trigger: 'blur' },
          ],
          name: [
            { required: true, message: '请输入名称', trigger: 'blur' },
            { max: 64, message: '最大长度为 64', trigger: 'blur' },
          ],
          sortNo: [
            { required: true, message: '请输入序号', trigger: 'blur' },
            { type: 'number', message: '序号必须为数字值', trigger: 'blur' },
          ],
          method: [
            { required: true, message: '请输入方法', trigger: 'blur' },
            { max: 32, message: '最大长度为 32', trigger: 'blur' },
          ],
          url: [
            { required: true, message: '请输入地址', trigger: 'blur' },
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          desc: [
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ]
        }

      }
    },
    created() {
      let id = this.$route.query.id;
      if (id != null) {
        this.findData(id);
      } else {
        this.postForm = Object.assign({}, defaultForm)
      }
    },
    methods: {
      //根据ID查询数据,回显
      findData(id) {
        this.$axios.get( '/functions/'+id).then(response => {
          let result = response.data;
          if(response.code == 200){
            console.log(result);
            this.postForm = Object.assign(this.postForm, result);
            this.postForm.status = 'draft';
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            this.editPost().then(response => {
              let result = response.data;
              if(response.code == 200){
                this.$message({message: '保存成功', type: 'success'});
                this.draftForm();
              }else{
                this.$message({message: response.msg, type: 'error'})
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      //取消操作
      draftForm() {
        bxUtils.closeAppointView(this.$route,'/system/functions');
        this.postForm.status = 'draft'
      },editPost(){
        if(this.postForm.id != null){
          return this.$axios.put('/functions/'+this.postForm.id , JSON.stringify(this.postForm),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
        }else{
          return this.$axios.post('/functions/', JSON.stringify(this.postForm),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
        }
      }
    }
  }
</script>

<style lang="scss" >
  @import "~@/styles/mixin.scss";
  .createPost-container {
    position: relative;
    .createPost-main-container {
      padding: 20px 45px 20px 50px;
      .postInfo-container {
        position: relative;
        @include clearfix;
        margin-bottom: 10px;
        .postInfo-container-item {
          float: left;
        }
      }
    }
    .word-counter {
      width: 40px;
      position: absolute;
      right: -10px;
      top: 0px;
    }
  }

</style>
