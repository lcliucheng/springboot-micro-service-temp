<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.menuName" placeholder="菜单名称" class="filter-item" style="width: 200px;"
                @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.offline" placeholder="状态" class="filter-item" clearable style="width: 110px">
        <el-option v-for="item in DISABLE_STATUS" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <div class="filter-item">
        <el-date-picker v-model="listQuery.createdAt" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        start-placeholder="开始日期" end-placeholder="结束日期"/>
      </div>
      <el-button v-if="checkPermission('5')" style="margin-left: 30px;" class="filter-item" type="primary"
                 icon="el-icon-search" @click="handleFilter">搜索
      </el-button>
      <el-button v-if="checkPermission('7')" style="margin-left: 10px; " class="filter-item" type="primary"
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
      <el-table-column label="菜单名称" prop="menuName" align="center">
      </el-table-column>
      <el-table-column label="名称" prop="name" align="center">
      </el-table-column>
      <el-table-column label="序号" prop="sortNo" align="center">
      </el-table-column>
      <el-table-column label="方法" prop="method" align="center">
        <template scope="scope">
          <el-tag size="small" :type="constantTag(scope.row.method,METHODS)" disable-transitions>
            {{ constantFormat(scope.row.method,METHODS) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="地址" prop="url" align="center">
      </el-table-column>
      <el-table-column label="描述" prop="desc" align="center">
      </el-table-column>
      <el-table-column label="是否下线" prop="offline" sortable="custom" align="center">
        <template scope="scope">
          <el-tag v-if="!checkPermission('9')"  size="small" :type="constantTag(scope.row.offline,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.offline,DISABLE_STATUS) }}
          </el-tag>
          <el-switch
            v-else
            v-model="scope.row.offline"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/functions/'+scope.row.id,{offline: scope.row.offline},'offline')">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdAt" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="修改时间" prop="lastModifiedAt" align="center">
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" v-if="checkPermission('9')" @click="handleUpdate(row)">编辑</el-button>
          <el-button size="mini" type="danger" v-if="checkPermission('8')" @click="handleDelete(row)">删除</el-button>
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
  import {METHODS,DISABLE_STATUS, constantFormat, constantTag, changeStatus} from '@/utils/constant'

  export default {
    components: {Pagination},
    data() {
      return {
        constantFormat,
        constantTag,
        changeStatus,
        DISABLE_STATUS,
        METHODS,
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          size: 10,
          serviceId: undefined,
          menuName: undefined,
          offline: undefined,
          createdAt: undefined,
        },
        sortBy: []
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
          serviceId: this.listQuery.serviceId,
          menuName: this.listQuery.menuName,
          offline: this.listQuery.offline,
          createdAt: this.listQuery.createdAt,
        };
        //请求后台接口
        this.$axios.get('/functions', {
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
        this.$router.push({path: '/system/functions/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/functions/edit', query: {id: row.id}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/functions/' + row.id).then(response => {
            let result = response.data;
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

