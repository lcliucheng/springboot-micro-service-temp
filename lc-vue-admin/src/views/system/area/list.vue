<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.code" placeholder="请输入编码" clearable style="width: 200px;" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.name" placeholder="请输入全称" clearable style="width: 200px;" @keyup.enter.native="handleFilter"/>
      <el-button v-if="checkPermission('1123156148163162231')" style="margin-left: 30px;" type="primary"
                 icon="el-icon-search" @click="handleFilter">搜索
      </el-button>
      <el-button v-if="checkPermission('1123156148163162233')" style="margin-left: 10px; " type="primary"
                 icon="el-icon-edit" @click="handleCreate">新增
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      row-key="id"
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
      lazy
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="编号" prop="id" align="center">
      </el-table-column>
      <el-table-column label="父级编码" prop="parentCode" align="center">
      </el-table-column>
      <el-table-column label="编码" prop="code" align="center">
      </el-table-column>
      <el-table-column label="全称" prop="name" align="center">
      </el-table-column>
      <el-table-column label="简称" prop="shortName" align="center">
      </el-table-column>
      <el-table-column label="经度" prop="longitude" align="center">
      </el-table-column>
      <el-table-column label="维度" prop="latitude" align="center">
      </el-table-column>
      <el-table-column label="级别" prop="level" align="center">
      </el-table-column>
      <el-table-column label="序号" prop="sortNo" align="center">
      </el-table-column>
      <el-table-column label="状态" prop="disabled" align="center">
        <template scope="scope">
          <el-switch
            v-if="checkPermission('1123156148163162235')"
            v-model="scope.row.disabled"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/areas/'+scope.row.id,{disabled: scope.row.disabled},'disabled')">
          </el-switch>
          <el-tag v-else  size="small" :type="constantTag(scope.row.disabled,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.disabled,DISABLE_STATUS) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdAt" sortable="custom" align="center"  min-width="120">
      </el-table-column>
      <el-table-column label="修改时间" prop="lastModifiedAt" sortable="custom" align="center"  min-width="120">
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="checkPermission('1123156148163162232')" type="primary" size="mini"
                     @click="handleUpdate(row)">编辑
          </el-button>
          <el-button v-if="checkPermission('1123156148163162234')" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination align="right" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.size"
                @pagination="getList"/>

  </div>
</template>

<script>

  import Pagination from '@/components/Pagination' //重写分页插件
  import bxUtils from '@/utils/zzjr-bx-utils'
  import {DISABLE_STATUS, constantFormat, constantTag, changeStatus} from '@/utils/constant'

  export default {
    components: {Pagination},
    data() {
      return {
        constantFormat,
        constantTag,
        changeStatus,
        DISABLE_STATUS,
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          size: 10,
          name: '',
          code: ''
        },
        sortBy: ['level,asc','sortNo,asc','createdAt,desc']
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true;
        //查询条件
        let param = {
          page: this.listQuery.page,
          size: this.listQuery.size,
          deleted: 0,
          sort: this.sortBy,
          name: this.listQuery.name,
          code: this.listQuery.code
        };
        if(this.listQuery.name === '' && this.listQuery.code === ''){
          param['level'] = 2;
        }
        //请求后台接口
        this.$axios.get('/areas', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);
          }
        }).then(response => {
          if (response.code == 200) {
            this.list = response.data.records;
            this.list.forEach(item =>{
              item['hasChildren'] = true;
            })
            this.total = parseInt(response.data.total);
          } else {
            this.$message({message: response.msg || "查询失败", type: 'error'})
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
        this.$router.push({path: '/system/area/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/area/edit', query: {id: row.id}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.put('/areas/' + row.id, JSON.stringify({deleted: 1}),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
            if (response.code == 200) {
              this.$message({type: 'success', message: '删除成功!'});
              this.getList();
            } else {
              this.$message({type: 'success', message: '删除失败!'});
            }

          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      load: async (tree, treeNode, resolve)=>{
        let param = {
          deleted: 0,
          parentCode:tree.code,
          sort: 'level,asc;sortNo,asc;createdAt,desc'
        };
        await this.$axios.get( '/areas/getOptionsData',{params:param}).then(response => {
          if(response.code == 200){
            if(response.data){
              response.data.forEach(item =>{
                item['hasChildren'] = true;
              })
              resolve(response.data);
            }
          }else{
            this.$message({message: response.msg, type: 'error'})
          }
        });
      }
    }
  }
</script>
