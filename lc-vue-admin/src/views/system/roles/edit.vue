<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
        <DisabledDropdown v-model="postForm.disabled" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="名称" prop="name">
              <el-input v-model="postForm.name" style="width: 30%" :disabled="disabledStatus"   placeholder="请输入名称" />
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

  import {
        DisabledDropdown,
  } from './Dropdown'
  import bxUtils from '@/utils/zzjr-bx-utils'

  const defaultForm = {
    status:'draft',
    name:undefined,
    desc:undefined,
    disabled:0,
    deleted:0
  }

  export default {
    name: '新增角色',
    components: {Sticky
        ,DisabledDropdown
    },
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          name: [
            { required: true, message: '请输入名称', trigger: 'blur' },
            { max: 64, message: '最大长度为 64', trigger: 'blur' },
          ],
          desc: [
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          disabled: [
            { max: 3, message: '最大长度为 3',  },
          ]
        },
        disabledOptions: [{value:0,desc:'启用'},{value:1,desc:'禁用'}],
        disabledStatus:false

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
        this.$axios.get('/roles/'+id).then(response => {
          let result = response.data;
          if(response.code == 200){
            console.log(result);
            this.postForm = Object.assign({}, result);
            this.postForm.status = 'draft';
            if(result.name  === 'channel'){
              this.disabledStatus = true
            }
          }else{
            this.$message({message: response.msg || '查询失败', type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            this.editPost().then(response => {
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
        bxUtils.closeAppointView(this.$route,'/system/roles');
        this.postForm.status = 'draft'
      },
      editPost(){
        if(this.postForm.id != null){
          return this.$axios.put('/roles/'+this.postForm.id , JSON.stringify(this.postForm),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
        }else{
          return this.$axios.post('/roles/', JSON.stringify(this.postForm),
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
