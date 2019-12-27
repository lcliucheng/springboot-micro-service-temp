<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
        <CommentDropdown v-model="postForm.disabled"/>
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存
        </el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="用户名" prop="username">
            <el-input v-model="postForm.username" disabled style="width: 30%" placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="姓名" prop="name">
            <el-input v-model="postForm.name"  style="width: 30%" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="新密码">
            <el-input v-model="postForm.passwd" type="password" style="width: 30%" placeholder="请输入新密码"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="确认密码">
            <el-input v-model="postForm.passwordre" type="password" style="width: 30%" placeholder="请输入确认密码"/>
          </el-form-item>

      </div>
    </el-form>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件
  //省市县选择器
  import {regionDataPlus, CodeToText} from 'element-china-area-data'
  import {CommentDropdown} from './Dropdown'
  import bxUtils from '@/utils/zzjr-bx-utils'

  const defaultForm = {
    status: 'draft',
    id:null,
    name:null,
    originalPassword:null,
    passwd:'',
    passwordre:'',
    disabled:0
  }

  export default {
    name: '新增用户',
    components: {Sticky, CommentDropdown},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          name:[
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ]
        },
        imageUrl: '',
        options: regionDataPlus,
        passwordre: ''
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
        this.$axios.get('/users/' + id).then(response => {
          let result = response.data;
          if (response.code == 200) {
            this.postForm = Object.assign({}, result);
            this.postForm.password = '';
            this.postForm.status = 'draft';
          } else {
            this.$message({message: response.msg || '查询异常', type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            if((this.postForm.passwd && this.postForm.passwd != '' ) || (this.postForm.passwordre && this.postForm.passwordre != '')){
              if (this.postForm.passwd != this.postForm.passwordre) {
                this.$message({message: '两次密码输入不一致！', type: 'error'});
                return;
              }

            }
            let param = {
              name:this.postForm.name,
              password:this.postForm.passwd
            }
            this.$axios.put('/users/update/'+ this.postForm.id, JSON.stringify(param),
              {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
              if (response.code == 200) {
                this.$message({message: '保存成功', type: 'success'});
                this.draftForm();
              } else {
                this.$message({message: response.msg || '保存失败', type: 'error'})
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
        bxUtils.closeAppointView(this.$route,'/system/users');
        this.postForm.status = 'draft'
      },
      // 图片预览
      handlePictureCardPreview(file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      handleChange(value) {
        this.postForm.province = CodeToText[value[0]];
        this.postForm.city = CodeToText[value[1]];
        this.postForm.district = CodeToText[value[2]];
        console.log(this.postForm)

      }
    }
  }
</script>

<style lang="scss">
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

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;

  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }

  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }

  .form-left {
    width: 50%;
    padding-left: 1rem;
    padding-right: 1rem;
    padding-top: 1rem;
  }

  .form-right {
    width: 50%;
    padding-left: 1rem;
    padding-right: 1rem;
    padding-top: 1rem;
  }
</style>
