<template>
  <el-dropdown :show-timeout="100" trigger="click">
    <el-button plain value="111">
      {{ btnContent}}
      <i class="el-icon-caret-bottom el-icon--right"/>
    </el-button>
    <el-dropdown-menu slot="dropdown" class="no-padding">
      <el-dropdown-item>
        <el-radio-group v-model="disabled" style="padding: 10px;">
          <el-radio :label="0">
            启用
          </el-radio>
          <el-radio :label="1">
            禁用
          </el-radio>
        </el-radio-group>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
  export default {
    props: {
      value: {
        type: Number,
        default: 0
      }
    },
    computed: {
      btnContent: {
        get() {
          if (this.disabled == 0) {
            return '状态：启用';
          }
          if (this.disabled == 1) {
            return '状态：禁用';
          }
        }
      },
      disabled: {
        get() {
          return this.value;
        },
        set(val) {
          this.$emit('input', val);
        }
      }
    }
  }
</script>
