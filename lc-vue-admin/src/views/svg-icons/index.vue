<template>
  <div class="icons-container">
    <el-tabs type="border-card" style=" width: 100%;max-height: 600px;overflow-y: scroll;">
      <el-tab-pane label="Icons">
        <div v-for="item of iconsMap" :key="item" @click="handleClipboard(item,'svg',$event)">
          <div class="icon-item">
            <svg-icon :icon-class="item" class-name="disabled" />
            <span>{{ item }}</span>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Element-UI Icons">
        <div v-for="item of elementIcons" :key="item" @click="handleClipboard(item,'element',$event)">
          <div class="icon-item">
            <i :class="item" />
            <span>{{ item }}</span>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import icons from './require-icons'
import elementIcons from './element-icon.json'


export default {
  name: 'Icons',
  props: {
    value: {
      type: Object
    }
  },
  computed: {
    selectIcon: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit('input', val);
      }
    }
  },
  data() {
    return {
      iconsMap: icons,
      elementIcons: elementIcons
    }
  },
  methods: {

    generateElementIconCode(symbol) {
      return `<i class="el-icon-${symbol}" />`
    },
    handleClipboard(text,type, event) {
      this.selectIcon = {id:this.selectIcon.id,name:text,type:type}
    }
  }
}
</script>

<style lang="scss" scoped>
.icons-container {
  margin: 10px 20px 0;
  overflow: hidden;

  .icon-item {
    margin: 20px;
    height: 60px;
    text-align: center;
    width: 100px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

  span {
    display: block;
    font-size: 16px;
    margin-top: 10px;
  }

  .disabled {
    pointer-events: none;
  }
}
</style>
