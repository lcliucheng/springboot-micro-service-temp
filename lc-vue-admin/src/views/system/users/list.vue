<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.username" placeholder="用户名" class="filter-item" style="width: 200px;"
                @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.type" placeholder="用户类型" class="filter-item" clearable style="width: 120px">
        <el-option v-for="item in USER_TYPE" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-select v-model="listQuery.disabled" placeholder="状态" class="filter-item" clearable style="width: 90px">
        <el-option v-for="item in DISABLE_STATUS" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <div class="filter-item">
        <el-date-picker v-model="listQuery.rangeDate" type="daterange" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </div>
      <el-button style="margin-left: 30px;" v-if="checkPermission('10')" type="primary" class="filter-item" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button style="margin-left: 10px; " v-if="checkPermission('12')" type="primary" class="filter-item" icon="el-icon-edit" @click="handleAddUpdate">新增</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange">

      <el-table-column label="序号" prop="id" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="用户名" prop="username" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="用户姓名" prop="name" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="用户类型" prop="type" sortable="custom" align="center">
        <template scope="scope">
          <el-tag  size="small" :type="constantTag(scope.row.type,USER_TYPE)" disable-transitions>
            {{ constantFormat(scope.row.type,USER_TYPE) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="disabled" align="center" width="200" >
        <template scope="scope">
          <el-tag v-if="!checkPermission('14')"  size="small" :type="constantTag(scope.row.disabled,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.disabled,DISABLE_STATUS) }}
          </el-tag>
          <el-switch
            v-else
            v-model="scope.row.disabled"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/users/'+scope.row.id,{disabled: scope.row.disabled},'disabled')">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt">
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="300" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" v-if="checkPermission('11')" size="small" @click="handleUpdate(row)">编辑</el-button>
          <el-button type="primary" v-if="checkPermission('1122386680197644294')" size="small" @click="roleConf(row)">配置角色</el-button>
          <el-button :disabled="row.type !== 'platform'" size="small" v-if="checkPermission('13')" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination align="right" :page-sizes="PAGE_SIZES" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.size"
                @pagination="getList"/>

    <el-dialog title="新增用户"
               :visible.sync="dialogAdd"
               width="30%">

      <el-form v-if="dialogAdd" ref="postForm" :model="postForm" :rules="rules" class="form-container">

        <div class="createPost-main-container">
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="用户名" prop="username">
            <el-input v-model="postForm.username" style="width: 60%" placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="用户类型" prop="type">
            <el-select v-model="postForm.type" placeholder="用户类型" style="width: 60%" >
              <el-option v-for="item in SYS_USER_TYPE" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="姓名" prop="name">
            <el-input v-model="postForm.name" style="width: 60%" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="新密码" prop="password">
            <el-input v-model="postForm.password" type="password" style="width: 60%" placeholder="请输入新密码"/>
          </el-form-item>
          <el-form-item style="margin-bottom: 30px;" label-width="100px" label="确认密码" prop="passwordre">
            <el-input v-model="postForm.passwordre" type="password" style="width: 60%" placeholder="请输入确认密码"/>
          </el-form-item>

        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button  @click="dialogAdd = false">取 消</el-button>
            <el-button type="primary" v-if="checkPermission('1122386680197644293')" @click="handBtnSaveUser('postForm')">确 定</el-button>
          </span>
    </el-dialog>


    <el-dialog
      title="配置角色"
      :visible.sync="dialogVisible"
      width="30%">

      <el-table
        v-loading="roleLoading"
        :data="roleList"
        border
        fit
        highlight-current-row
        style="width: 100%;"
        ref="multipleTable">

        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column label="名称" prop="name" sortable="custom" align="center">
        </el-table-column>
        <el-table-column label="描述" align="desc" prop="desc">
        </el-table-column>
        <el-table-column label="序号" prop="createdAt" sortable="custom" align="center">
        </el-table-column>

      </el-table>
      <pagination align="right" :page-sizes="PAGE_SIZES" :total="roleTotal" :page.sync="roleListQuery.page" :limit.sync="roleListQuery.size"
                  @pagination=""/>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handBtnSave">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>


  import Pagination from '@/components/Pagination' //重写分页插件
  import bxUtils from '@/utils/zzjr-bx-utils'
  import { DISABLE_STATUS,SYS_USER_TYPE,USER_TYPE, constantFormat, constantTag, changeStatus} from '@/utils/constant'
  const defaultForm = {
    status: 'draft',
    id: null,
    username:'',
    name:'',
    type:'',
    password: null,
    passwordre: null,
    avatar:'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    disabled:0
  }

  export default {

    components: {Pagination},
    data() {
      return {
        postForm: Object.assign({}, defaultForm),
        SYS_USER_TYPE,
        USER_TYPE,
        constantFormat,
        constantTag,
        changeStatus,
        list: null,
        total: 0,
        roleTotal:0,
        listLoading: true,
        listQuery: {
          page: 1,
          size: 10,
          disabled: undefined,
          username: undefined,
          rangeDate: undefined,
          type:undefined
        },
        roleListQuery: {
          page: 1,
          size: 10,
          disabled: undefined,
          username: undefined,
          rangeDate: undefined,
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          name:[
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择用户类型', trigger: 'blur'}
          ],
          passwordre: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        sortBy: [],
        dialogVisible: false,
        dialogAdd: false,
        roleLoading: true,
        roleList: [],
        roleTotal: 0,
        currUserId: null,
        DISABLE_STATUS
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {

        //查询条件
        let param = {
          page: this.listQuery.page,
          size: this.listQuery.size,
          username: this.listQuery.username,
          disabled: this.listQuery.disabled,
          sort: this.sortBy,
          type:this.listQuery.type
        };
        param['in'] = 'type,STRING,platform,busi';
        //请求后台接口
        this.$axios.get('/users', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }
        }).then(response => {
          if (response.code == 200) {
            let result = response.data;
            this.listLoading = true;
            this.list = result.records;
            this.total = parseInt(result.total);
          } else {
            this.$message({
              message: result.msg,
              type: 'error'
            })
          }
          this.listLoading = false;
        })
      },
      handleFilter() {
        this.listQuery.page = 1;
        this.getList();
      },
      sortChange(data) {
        const {prop, order} = data;
        this.sortBy = [];
        if (prop != null) {
          if (order == 'ascending') {
            this.sortBy.push(prop + ',asc');
          } else {
            this.sortBy.push(prop + ',desc');
          }
        }
        this.handleFilter();
      },
      handleCreate() {
        this.$router.push({path: '/system/users/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/users/edit', query: {id: row.id}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/users/' + row.id).then(response => {
            if (response.code == 200) {
              this.$message({type: 'success', message: '删除成功!'});
              this.getList();
            } else {
              this.$message({type: 'success', message: response.msg || '删除失败!'});
            }

          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //获取角色列表
      roleConf(row) {
        this.currUserId = row.id;
        this.roleLoading = true;
        //查询条件
        let param = {
          page: this.roleListQuery.page,
          size: this.roleListQuery.size,
          name: this.roleListQuery.name,
          disabled: this.roleListQuery.disabled,
          createdAt: this.roleListQuery.createdAt,
        };
        this.$axios.get('/roles', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }
        }).then(response => {
          this.roleLoading = false;
          if (response.code == 200) {
            let result = response.data;
            this.roleList = result.records;
            this.roleTotal = parseInt(result.total);
            this.$axios.get('/users/roles/' + this.currUserId).then(response => {
              let result = response.data;
              this.roleList.forEach(item => {
                result.forEach(sysRole => {
                  if (item.id == sysRole.roleId) {
                    this.$refs.multipleTable.toggleRowSelection(item);
                  }
                })
              })
            })
          } else {
            this.$message({
              message: response.msg,
              type: 'error'
            })
          }
        })
        this.dialogVisible = true
      },
      //保存用户角色关系
      handBtnSave() {
        let roleList = this.$refs.multipleTable.selection;
        let roleId = '';
        roleList.forEach(item => {
          roleId += item.id + ",";
        })
        let param = new URLSearchParams();
        param.append("roleIds", roleId);
        this.$axios.post('/users/roles/' + this.currUserId, param,
          {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(response => {
          this.dialogVisible = false;
          this.$message({message: '保存成功', type: 'success'});
        })
      },
      handBtnSaveUser(postForm){
        this.$refs[postForm].validate((valid) => {
          if (valid) {
            if (this.postForm.password != this.postForm.passwordre) {
              this.$message({message: '两次密码输入不一致！', type: 'error'});
              return;
            }
            let param = {
              type:this.postForm.type,
              name:this.postForm.name,
              username:this.postForm.username,
              password:this.postForm.password,
              avatar:'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
              disabled:this.postForm.disabled,
            }
            this.$axios.post('/users/register/', JSON.stringify(param),
              {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
              if (response.code == 200) {
                this.$message({message: '保存成功', type: 'success'});
                this.dialogAdd = false;
                this.getList();
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
      handleAddUpdate(){
        Object.assign(this.postForm, this.$options.data().postForm);
        this.dialogAdd = true;
      }
    }
  }
</script>


