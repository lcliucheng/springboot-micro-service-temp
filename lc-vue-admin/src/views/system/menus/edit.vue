<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
          <OfflineDropdown v-model="postForm.offline" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="父编号"  prop="parentId">
              <el-input v-model="postForm.parentId" style="width: 30%"  placeholder="请输入父编号" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="名称"  prop="name">
              <el-input v-model="postForm.name" style="width: 30%"  placeholder="请输入名称" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="级别"  prop="level">
              <el-input v-model="postForm.level" style="width: 30%"  placeholder="请输入级别" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="序号"  prop="sortNo">
              <el-input v-model="postForm.sortNo" style="width: 30%"  placeholder="请输入序号" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="是否隐藏"  >
            <el-radio-group v-model="postForm.hidden">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="组件"  prop="component">
              <el-input v-model="postForm.component" style="width: 30%"  placeholder="请输入组件" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="页面地址"  prop="menuPage">
              <el-input v-model="postForm.menuPage" style="width: 30%"  placeholder="请输入页面地址" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="访问路径"  prop="path">
              <el-input v-model="postForm.path" style="width: 30%"  placeholder="请输入访问路径" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="图标"  prop="icon">
            <el-button v-if="iconType == null" @click="dialogVisible = true"><i class="el-icon-plus"></i>选择图标</el-button>
            <div v-else>
              <i style="margin-left:20px;font-size: 35px;cursor:pointer" v-if="iconType == 'element'" @click="dialogVisible = true" :class="postForm.icon"></i>
              <svg-icon v-else class="icon-item" @click="dialogVisible = true" :icon-class="postForm.icon" />
            </div>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="重定向路径"  prop="redirect">
              <el-input v-model="postForm.redirect" style="width: 30%"  placeholder="请输入重定向路径" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="描述"  prop="desc">
              <el-input v-model="postForm.desc" style="width: 30%"  placeholder="请输入描述" />
          </el-form-item>
      </div>
    </el-form>

    <el-dialog
      title="选择图标"
      :visible.sync="dialogVisible"
      width="80%"
      style="overflow:auto;margin-top: -8vh">
      <iconView :lazy="true" v-model="selectIcon"/>
    </el-dialog>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件

  import {OfflineDropdown  } from './Dropdown'
  import bxUtils from '@/utils/zzjr-bx-utils'
  import iconView from '@/views/svg-icons/index'

  const defaultForm = {
    status:'draft',
    parentId:null,
    name:'',
    level:null,
    sortNo:null,
    component:'',
    menuPage:'',
    path:'',
    icon:'',
    redirect:'',
    desc:'',
    offline:0,
    hidden:1,
    deleted:0
  }

  export default {
    name:"新增菜单",
    components: {Sticky,OfflineDropdown,iconView},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          name: [
            { required: true, message: '请输入名称', trigger: 'blur' },
            { max: 64, message: '最大长度为 64', trigger: 'blur' },
          ],
          level: [
            { required: true, message: '请输入级别', trigger: 'blur' },
          ],
          sortNo: [
            { required: true, message: '请输入序号', trigger: 'blur' },
          ],
          hidden: [
            { required: true, message: '请输选择是否隐藏', trigger: 'blur' },
          ],
          component: [
            { required: true, message: '请输入组件', trigger: 'blur' },
            { max: 128, message: '最大长度为 128', trigger: 'blur' },
          ],
          menuPage: [
            { required: true, message: '请输入页面地址', trigger: 'blur' },
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          path: [
            { required: true, message: '请输入访问路径', trigger: 'blur' },
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          icon: [
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          redirect: [
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
          desc: [
            { max: 255, message: '最大长度为 255', trigger: 'blur' },
          ],
        },
        dialogVisible: false,
        selectIcon:{name:null,type:null},
        iconType:null
      }
    },
    watch:{
      selectIcon:{
        handler:function(val){
          this.dialogVisible = false;
          this.postForm.icon = this.selectIcon.name;
          this.iconType = this.selectIcon.type;
        },
        deep:true
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
        this.$axios.get( '/menus/'+id).then(response => {
          let result = response.data;
          if(response.code == 200){
            console.log(result);
            this.postForm = Object.assign({}, result);
            this.postForm.status = 'draft';
            let icon = this.postForm.icon;
            if(icon.indexOf("@") != -1){
              let iconSp = icon.split("@");
              this.postForm.icon = iconSp[0];
              this.iconType = iconSp[1];
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
            this.postForm.icon = this.postForm.icon + '@' +this.selectIcon.type;
            this.editPost().then(response => {
              if(response.code == 200){
                this.$message({message: '保存成功', type: 'success'});
                this.draftForm();
              }else{
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
        bxUtils.closeAppointView(this.$route,'/system/menus');
        this.postForm.status = 'draft'
      },
      editPost(){
        if(this.postForm.id != null){
          return this.$axios.put('/menus/'+this.postForm.id , JSON.stringify(this.postForm),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
        }else{
          return this.$axios.post('/menus/', JSON.stringify(this.postForm),
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
<style>
  .icon-item {
    margin: 0 20px 0 20px;
    height: 60px;
    text-align: center;
    width: 100px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

</style>
