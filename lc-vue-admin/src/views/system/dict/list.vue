<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.code" placeholder="字典编码" class="filter-item" style="width: 200px;" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.disabled" placeholder="状态" class="filter-item" clearable style="width: 90px">
        <el-option v-for="item in DISABLE_STATUS" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button v-if="checkPermission('1123156148163162197')" class="filter-item" style="margin-left: 30px;" type="primary"
                 icon="el-icon-search" @click="handleFilter">搜索
      </el-button>
      <el-button v-if="checkPermission('1123156148163162194')" class="filter-item" style="margin-left: 10px; " type="primary"
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
      <el-table-column label="字典编码" width="150" prop="code" align="center">
      </el-table-column>
      <el-table-column label="字典名称" width="200" prop="name" align="center">
      </el-table-column>
      <el-table-column label="字典值" prop="value" width="200" align="center">
      </el-table-column>
      <el-table-column label="字典描述" prop="desc" width="300" align="center">
      </el-table-column>
      <el-table-column label="扩展" prop="ext" :formatter="fmExt" width="200" align="center">
      </el-table-column>
      <el-table-column label="排序" prop="sortNo" width="80" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="状态" width="100" prop="disabled" align="center">
        <template scope="scope">
          <el-switch
            v-if="checkPermission('1123156148163162196')"
            v-model="scope.row.disabled"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/dicts/'+scope.row.id,{disabled: scope.row.disabled},'disabled')">
          </el-switch>
          <el-tag v-else  size="small" :type="constantTag(scope.row.disabled,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.disabled,DISABLE_STATUS) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdAt"  width="180" align="center">
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="checkPermission('1123156148163162196')" type="primary" size="mini"
                     @click="handleUpdate(row)">编辑
          </el-button>
          <el-button v-if="checkPermission('1123156148163162195')" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
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
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          size: 10,
          name: undefined,
          disabled: undefined,
          code:null
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
          deleted: 0,
          in:'1,code,STRING,FAQ',
          code:this.listQuery.code
        };
        //请求后台接口
        this.$axios.get('/dicts', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }
        }).then(response => {

          if (response.code == 200) {
            this.list = response.data.records;
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
        this.$router.push({path: '/system/dict/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/dict/edit', query: {id: row.id}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.put('/dicts/' + row.id, JSON.stringify({deleted: 1}),
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
      handleStatus(row) {
        this.$confirm('确定操作,则立即生效?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.put('/dicts/' + row.id, JSON.stringify({disabled: row.disabled == 1 ? 0 : 1}),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
            if (response.code == 200) {
              this.$message({type: 'success', message: '修改成功!'});
              this.getList();
            } else {
              this.$message({type: 'success', message: '修改失败!'});
            }

          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      fmExt(row, column, cellValue){
        if(cellValue){
          return JSON.stringify(cellValue);
        }
      }
    }
  }
</script>

