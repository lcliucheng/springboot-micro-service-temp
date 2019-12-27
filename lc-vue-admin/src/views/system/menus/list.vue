<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="名称" class="filter-item" style="width: 200px;"
                @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.offline" placeholder="状态" class="filter-item" clearable style="width: 90px">
        <el-option v-for="item in DISABLE_STATUS" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <div class="filter-item">
        <el-date-picker v-model="listQuery.createdAt" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        start-placeholder="开始日期" end-placeholder="结束日期"/>

      </div>
      <el-button v-if="checkPermission('1122052874693484545')" class="filter-item" style="margin-left: 30px;"
                 type="primary" icon="el-icon-search" @click="handleFilter">搜索
      </el-button>
      <el-button v-if="checkPermission('1122052874693484545')" class="filter-item" style="margin-left: 10px; "
                 type="primary" icon="el-icon-edit" @click="handleCreate">新增
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
      row-key="id"
      lazy
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="编号" min-width="180" prop="id" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="父编号" prop="parentId" align="center">
      </el-table-column>
      <el-table-column label="名称" prop="name" align="center">
      </el-table-column>
      <el-table-column label="级别" prop="level" align="center">
      </el-table-column>
      <el-table-column label="序号" prop="sortNo" align="center">
      </el-table-column>
      <el-table-column label="组件" prop="component" align="center">
      </el-table-column>
      <el-table-column label="页面地址" prop="menuPage" align="center">
      </el-table-column>
      <el-table-column label="访问路径" prop="path" align="center">
      </el-table-column>
      <el-table-column label="图标" prop="icon" align="center">
        <template scope="scope">
          <i style="font-size: 35px;cursor:pointer" v-if="scope.row.iconType && scope.row.iconType == 'element'" @click="clickIcon(scope.row.id)" :class="scope.row.icon"></i>
          <svg-icon v-else class="icon-item-list" @click="clickIcon(scope.row.id)" :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column label="重定向路径" prop="redirect" align="center">
      </el-table-column>
      <el-table-column label="描述" prop="desc" align="center">
      </el-table-column>
      <el-table-column label="状态" prop="offline" sortable="custom" align="center">
        <template scope="scope">
          <el-tag v-if="!checkPermission('19')"  size="small" :type="constantTag(scope.row.offline,DISABLE_STATUS)" disable-transitions>
            {{ constantFormat(scope.row.offline,DISABLE_STATUS) }}
          </el-tag>
          <el-switch
            v-else
            v-model="scope.row.offline"
            class="switchStyle"
            :active-value="0"
            :inactive-value="1"
            active-color="#00A854"
            @change="changeStatus('/menus/'+scope.row.id,{offline: scope.row.offline},'offline')">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdAt" sortable="custom" align="center">
      </el-table-column>
      <el-table-column label="修改时间" prop="lastModifiedAt" align="center">
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" v-if="checkPermission('19')" size="mini" @click="handleUpdate(row)">编辑</el-button>
          <el-button size="mini" v-if="checkPermission('18')" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination align="right" :page-sizes="PAGE_SIZES" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.size"
                @pagination="getList"/>

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


  import Pagination from '@/components/Pagination' //重写分页插件
  import bxUtils from '@/utils/zzjr-bx-utils'
  import {DISABLE_STATUS, constantFormat, constantTag, changeStatus} from '@/utils/constant'
  import iconView from '@/views/svg-icons/index'
  import axios from 'axios'
  import {MessageBox, Message} from 'element-ui'

  export default {
    components: {Pagination,iconView},
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
          parentId: undefined,
          name: undefined,
          level: undefined,
          sortNo: undefined,
          component: undefined,
          menuPage: undefined,
          path: undefined,
          icon: undefined,
          redirect: undefined,
          desc: undefined,
          offline: undefined,
          createdAt: undefined,
        },
        sortBy: [],
        dialogVisible:false,
        selectIcon:{id:null,name:null,type:null},
      }
    },
    created() {
      this.getList()
    },
    watch:{
      selectIcon:{
        handler:function(val){
          if(!val || val.name == null){
            return
          }
          this.dialogVisible = false;
          this.$axios.put('/menus/'+val.id , JSON.stringify({icon:this.selectIcon.name+"@"+this.selectIcon.type}),
            {headers: {'Content-Type': 'application/json;charset-UTF-8'}}).then(response => {
              if(response.code == 200){
                this.$message({message: '保存成功', type: 'success'});
                this.getList();
              }else{
                this.$message({message: response.msg || '保存失败', type: 'error'})
              }
          });

          this.selectIcon={id:null,name:null,type:null};

        },
        deep:true
      }
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
          offline: this.listQuery.offline,
        };
        param['level'] = 1;
        //请求后台接口
        this.$axios.get('/menus', {
          params: param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }
        }).then(response => {

          let result = response.data;
          if (response.code == 200) {
            let records = result.records;
            for (let i = 0; i < records.length ; i++) {
              let icon = records[i]['icon'];
              if(icon.indexOf("@") != -1){
                let iconSp = icon.split("@");
                records[i]['icon'] = iconSp[0];
                records[i]['iconType'] = iconSp[1];
              }
            }
            this.list = result.records;
            this.total = parseInt(result.total);
            this.list.forEach(item =>{
              item['hasChildren'] = true;
            })
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
        this.$router.push({path: '/system/menus/edit'});
      },
      handleUpdate(row) {
        this.$router.push({path: '/system/menus/edit', query: {id: row.id}});
      },
      handleDelete(row) {
        this.$confirm('删除记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete('/menus/' + row.id).then(response => {
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
      },
      clickIcon(id){
        this.selectIcon.id = id;
        this.dialogVisible = true
      },
      load: async (tree, treeNode, resolve)=>{
        let param = {
          deleted: 0,
          parentId:tree.id,
        };
        await axios.get( '/menus/all',{params:param,
          paramsSerializer: function (params) {
            //请求前参数处理，主要处理数组类型
            return bxUtils.preDeal(params);

          }}).then(response => {
          if(response.code == 200){
            if(response.data){
              response.data.forEach(item =>{
                item['hasChildren'] = true;
                if(item['icon'].indexOf("@") != -1){
                  let iconSp = item['icon'].split("@");
                  item['icon'] = iconSp[0];
                  item['iconType'] = iconSp[1];
                }
              })
              resolve(response.data);
            }
          }else{
            Message({message: response.msg, type: 'error'});
          }
        });
      }
    }
  }
</script>
<style>
  .icon-item-list {
    height: 60px;
    text-align: center;
    width: 100px;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

</style>
