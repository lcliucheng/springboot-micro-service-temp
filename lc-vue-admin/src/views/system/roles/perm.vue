<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm('postForm')">保存</el-button>
        <el-button v-loading="loading" type="danger" @click="draftForm">取消</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-form-item style="margin-bottom: 30px;" label-width="100px" label="名称"  prop="name">
          <el-input v-model="postForm.name" style="width: 30%" disabled  placeholder="请输入名称" />
        </el-form-item>
        <el-form-item style="margin-bottom: 30px;" label-width="100px" label="权限"  prop="desc">
          <el-tree
            :data="menuData"
            :props="defaultProps"
            @check-change="handleCheckChange"
            show-checkbox
            node-key="id"
            ref="tree"
            default-expand-all
            :default-checked-keys="roleMenu"
            :render-content="renderContent">
          </el-tree>

        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
  import Sticky from '@/components/Sticky' // 粘性header组件

  import bxUtils from '@/utils/zzjr-bx-utils'

  const defaultForm = {
    status:'draft',
    id:undefined,
    name:undefined
  }

  export default {
    components: {Sticky},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        loading: false,
        menuData:[],
        roleMenu:[],
        roleFunct:[],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    created() {
      let id = this.$route.query.id;
      this.postForm.name = this.$route.query.name;
      if (id != null) {
        this.findData(id);
      } else {
        this.postForm = Object.assign({}, defaultForm)
      }
    },
    methods: {
      //根据用户ID查询菜单权限树,回显
      findData(id) {
        this.$axios.get('/roles/menus/tree/'+id).then(response => {
          if(response.code == 200){
            this.postForm.id = id;
            this.menuData = response.data.menus;
            let rf = response.data.roleFunctions;
            let functList = [];
            if(rf != null && rf.length > 0){
              for (let i = 0; i < rf.length; i++) {
                functList.push(rf[i].functionId);
              }
            }
            this.roleFunct = functList;
            let sysRoleMenu = response.data.roleMenus;
            if(sysRoleMenu != null && sysRoleMenu.length > 0){
              for (let i = 0; i < sysRoleMenu.length; i++) {
                for (let j = 0; j < this.menuData.length; j++) {
                    this.filterParent(this.menuData[j],sysRoleMenu[i]);
                }
              }
            }
            this.postForm.status = 'draft';
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
      },
      //保存数据
      submitForm(postForm) {
        //用户ID
        let roleId = this.postForm.id;
        //选中的资源列表
        let resourceList = this.roleFunct;
        //选中的菜单列表
        let menuList = [].concat(this.$refs.tree.getCheckedKeys(), this.$refs.tree.getHalfCheckedKeys())
        //请求后台接口
        let param = new URLSearchParams();
        param.append("functionIds", resourceList.join(","));
        param.append("menuIds", menuList.join(","));
        this.$axios.post('/roles/functions/'+roleId, param).then(response => {
          if(response.code == 200){
            this.$message({message: '保存成功', type: 'success'});
            this.draftForm();
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });

      },
      //取消操作
      draftForm() {
        bxUtils.closeAppointView(this.$route,'/system/roles');
        this.postForm.status = 'draft'
      },
      renderContent(h, { node, data, store }) {
        let userMessage;
        if(data.functions != null && data.functions.length > 0 ){
          var checkbox = [];
          for (let i = 0; i < data.functions.length; i++) {
            checkbox.push(<el-checkbox label={data.functions[i].id}>{data.functions[i].name}</el-checkbox>)
          }
          userMessage = (
              <el-checkbox-group v-model={this.roleFunct}>
                {checkbox}
              </el-checkbox-group>
          );
        }

        return (
          <span style="flex: 1; display: flex; align-items: center; flex-end: space-between; font-size: 14px; padding-right: 8px; ">
          <span>
          <span>{node.label}</span>

        </span>
        <span style="margin-left:30px">
          {userMessage}

          </span>
          </span>
      );
      },
      filterParent(menuData,sysRoleMenu){
        if(menuData.children == null ||  menuData.children.length == 0){
          if(menuData.id == sysRoleMenu.menuId ){
            this.roleMenu.push(sysRoleMenu.menuId);
          }
        }else{
          for (let i = 0; i < menuData.children.length; i++) {
            this.filterParent(menuData.children[i],sysRoleMenu);
          }
        }

      },
      handleCheckChange(data, checked,node) {
        if(data.functions){
          if(data.functions.length > 0){
            if(checked){
                data.functions.forEach(item => {
                  this.roleFunct.push(item.id);
                })
            }else{
              data.functions.forEach(item => {
                let index = bxUtils.getIndex(this.roleFunct,item.id);
                if(index || index === 0){
                  this.roleFunct.splice(index,1);
                }
              })
            }
            }
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

  .el-tree {
    width: 100%;
    overflow: scroll;
  }

  .el-tree>.el-tree-node {
    display: inline-block;
    min-width: 100%;
  }
</style>
