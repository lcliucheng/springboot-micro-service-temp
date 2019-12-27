<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
          <DisabledDropdown v-model="postForm.disabled" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-col :span="11" >
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="字典编码"  prop="code">
            <el-input v-model="postForm.code" style="width: 60%"  placeholder="请输入字典编码" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="字典值"  prop="value">
            <el-input v-model="postForm.value" style="width: 60%"  placeholder="请输入字典值" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="排序号"  prop="sortNo">
            <el-input v-model.number="postForm.sortNo" style="width: 60%"  placeholder="请输入排序号" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="扩展"  prop="ext">
            <el-input v-model="postForm.ext" style="width: 60%" type="textarea" :rows="4"   />
          </el-form-item>
        </el-col>
        <el-col :span="11" >
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="字典名称"  prop="name">
            <el-input v-model="postForm.name" style="width: 60%"  placeholder="请输入字典名称" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="字典描述"  prop="desc">
            <el-input v-model="postForm.desc" type="textarea" :rows="4" style="width: 60%"  placeholder="请输入字典描述" />
          </el-form-item>


        </el-col>
      </div>
    </el-form>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件

  import {DisabledDropdown} from './Dropdown'
  import bxUtils from '@/utils/zzjr-bx-utils'

  const defaultForm = {
    status:'draft',
    ext:null,
    code:null,
    name:'',
    value:'',
    sortNo:null,
    disabled:0,
    deleted:0
  }

  export default {
    name:"字典管理",
    components: {Sticky,DisabledDropdown},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          code: [
            {required: true, message: '请输入字典编码' },
            { max: 255, message: '最大长度为 15', trigger: 'blur' },
          ],
          name: [
            { max: 255, message: '最大长度为 15', trigger: 'blur' },
          ],
          value: [
            { max: 1024, message: '最大长度为 1024', trigger: 'blur' },
          ],
          sortNo: [
            {required: true, message: '请输入序号' },
          ]
        },
        publishedOptions: [{"value": 1, "desc": "上线"}, {"value": 0, "desc": "下线"}],
        appList: [],
        apks:[],
        os:["Android", "iOS"],
      }
    },
    async created() {
      this.appList = await bxUtils.getApks();
      //获取分类
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
        this.$axios.get( '/dicts/'+id).then(response => {
          if(response.code == 200){
            this.postForm = Object.assign({}, response.data);
            this.postForm.status = 'draft';
            if(this.postForm.ext){
              this.postForm.ext = JSON.stringify(this.postForm.ext);
            }
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            if(this.postForm.ext){
              this.postForm.ext = JSON.parse(this.postForm.ext);
            }
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
        bxUtils.closeAppointView(this.$route,'/system/dict');
        this.postForm.status = 'draft'
      },editPost(){
            if(this.postForm.id != null){
                return this.$axios.put('/dicts/'+this.postForm.id , JSON.stringify(this.postForm),
                        {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
            }else{
                return this.$axios.post('/dicts/', JSON.stringify(this.postForm),
                        {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
            }
        },
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
