<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
                  <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-col :span="11" >
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="父级区域"  prop="parentCode">
            <el-cascader
              clearable
              style="width: 60%"
              v-model="postForm.parentCode"
              :options="areaGroupList"
              :props="props"></el-cascader>
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="简称"  prop="shortName">
            <el-input v-model="postForm.shortName" style="width: 60%"  placeholder="请输入简称" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="经度"  prop="longitude">
            <el-input v-model="postForm.longitude" style="width: 60%"  placeholder="请输入" />
          </el-form-item>

          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="序号"  prop="sort">
            <el-input v-model="postForm.sort" style="width: 60%"  placeholder="请输入序号" />
          </el-form-item>
        </el-col>
        <el-col :span="11" >
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="编码"  prop="code">
            <el-input v-model="postForm.code" style="width: 60%"  placeholder="请输入编码" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="全称"  prop="name">
            <el-input v-model="postForm.name" style="width: 60%"  placeholder="请输入全称" />
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="维度"  prop="latitude">
              <el-input v-model="postForm.latitude" style="width: 60%"  placeholder="请输入维度" />
          </el-form-item>
        </el-col>
      </div>
    </el-form>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件

  import bxUtils from '@/utils/zzjr-bx-utils'

  const defaultForm = {
    status:'draft',
    id:null,
    code:null,
    parentCode:[],
    name:null,
    shortName:null,
    longitude:null,
    latitude:null,
    level:null,
    sort:null,
    disabled:null,
    ext:{
      cascader:null
    }
  }

  export default {
    components: {Sticky},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        rules: {
          parentCode: [
            { required: true, message: '请选择父级区域',trigger: 'blur'},
          ],
          code:[
            {required: true, message: '请输入编码',trigger: 'blur'}
          ],
          name: [
            { required: true, message: '请输入全称',trigger: 'blur'},
            { max: 200, message: '最大长度为 200',trigger: 'blur'},
          ],
          shortName: [
            { required: true, message: '请输入简称',trigger: 'blur'},
            { max: 200, message: '最大长度为 200',trigger: 'blur'},
          ],
          longitude: [
            { required: true, message: '请输入经度',trigger: 'blur'},
            { max: 12, message: '最大长度为 12',trigger: 'blur'},
          ],
          latitude: [
            { required: true, message: '请输入维度',trigger: 'blur'},
            { max: 12, message: '最大长度为 12',trigger: 'blur'},
          ],
          sort: [
            { required: true, message: '请输入序号',trigger: 'blur'},
          ],
          status: [
            { required: true, message: '请输入',trigger: 'blur'},
          ],
        },
        areaGroupList:null,
        props: {
          checkStrictly: true,
          value:'code',
          label:'name',
          lazy: true,
          lazyLoad:async (node, resolve) => {
            if(!node.value){
              return ;
            }
            let nodes = [];
            await this.findAllArea(node.value,null).then(response => {
              if(response.code == 200){
                if(response.data){
                  response.data.forEach(item => {
                    nodes.push({
                      code: item.code,
                      name: item.name,
                      leaf: item.level >= 5
                    })
                  })
                }
              }else{
                this.$message({message: response.msg, type: 'error'})
              }
            });
            // 通过调用resolve将子节点数据返回，通知组件数据加载完成
            resolve(nodes);
          }
        }
      }
    },
    async created() {
      let id = this.$route.query.id;
      if (id != null) {
        this.findData(id);
      } else {
        await this.findAllArea(null,2).then(response => {
          if(response.code == 200){
            this.areaGroupList = response.data;
            this.postForm.status = 'draft';
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
        this.postForm = Object.assign({}, defaultForm)
      }
    },
    methods: {
      //根据ID查询数据,回显
      findData(id) {
        this.$axios.get( '/areas/'+id).then( async response => {
          if(response.code == 200){
            this.postForm = Object.assign({}, response.data);
            this.postForm.status = 'draft';
            this.postForm.parentCode = response.data.ext.cascader
            this.areaGroupList = response.data.groupList;
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            this.postForm.level = this.postForm.parentCode.length;
            this.postForm.ext.cascader = this.postForm.parentCode;
            this.postForm.parentCode = this.postForm.parentCode[this.postForm.parentCode.length-1];
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
        bxUtils.closeAppointView(this.$route, '/system/area');
        this.postForm.status = 'draft'
      },
      editPost(){
          if(this.postForm.id != null){
              return this.$axios.put('/areas/'+this.postForm.id , JSON.stringify(this.postForm),
                      {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
          }else{
              return this.$axios.post('/areas', JSON.stringify(this.postForm),
                      {headers: {'Content-Type': 'application/json;charset-UTF-8'}})
          }
      },
      findAllArea(code,level){
        if(!code && !level){
          return ;
        }
        let param = {
          deleted: 0,
          parentCode:code,
          level:level
        };
        return this.$axios.get( '/areas/getOptionsData',{params:param})
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
