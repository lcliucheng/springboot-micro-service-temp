<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="名称" class="filter-item" style="width: 200px;"
                @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.disabled" placeholder="状态" class="filter-item" clearable style="width: 90px">
        <el-option v-for="item in DISABLE_STATUS" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <div class="filter-item">
        <el-date-picker v-model="listQuery.createdAt" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        start-placeholder="开始日期" end-placeholder="结束日期"/>

      </div>
      <el-button style="margin-left: 30px;" class="filter-item" v-if="checkPermission('1')" type="primary"
                 icon="el-icon-search" @click="handleFilter">搜索
      </el-button>
      <el-button style="margin-left: 10px; " class="filter-item" v-if="checkPermission('20')" type="primary"
                 icon="el-icon-edit" @click="handleCreate">新增
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange">
      <el-table-column label="编号" prop="id" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="名称" prop="name" align="center">
      </el-table-column>
      <el-table-column label="描述" prop="desc" align="center">
      </el-table-column>
      <el-table-column label="状态" prop="disabled" sortable="custom" align="center">
        <template scope="scope">
          <el-tag v-if="!checkPermission('21')"  size="small" :type="constantTag(scope.row.offline,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.offline,DISABLE_STATUS) }}
          </el-tag>
          <el-switch
            v-else
            v-model="scope.row.disabled"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/roles/'+scope.row.id,{disabled: scope.row.disabled},'disabled')">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" prop="createdAt" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="修改日期" prop="lastModifiedAt" align="center">
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="260" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="small" v-if="checkPermission('21')" @click="handleUpdate(row)">编辑</el-button>
          <el-button type="primary" size="small" v-if="checkPermission('2')" @click="handlePerm(row)">配置权限</el-button>
          <el-button :disabled="row.name === 'channel'" size="small" type="danger" v-if="checkPermission('22')" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination align="right" :page-sizes="PAGE_SIZES" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.size"
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
        paht: this.$route.path,
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          size: 10,
          name: undefined,
          disabled: undefined,
          createdAt: undefined,
        },
        sortBy: [],

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
          sort: this.sortBy,
          name: this.listQuery.name,
          disabled: this.listQuery.disabled,
          createdAt: this.listQuery.createdAt,
        };
        //请求后台接口
        this.$axios.get('/roles', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }
        }).then(response => {
          let result = response.data;
          if (response.code == 200) {
            this.list = result.records;
            this.total = parseInt(result.total);
          } else {
            this.$message({
              message: response.msg,
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
        this.$router.push({path: '/system/roles/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/roles/edit', query: {id: row.id}});
      },
      handlePerm(row) {
        this.$router.push({path: '/system/roles/perm', query: {id: row.id, name: row.name}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/roles/' + row.id).then(response => {
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
      }
    }
  }
</script>

