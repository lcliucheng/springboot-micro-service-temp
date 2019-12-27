<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  components: { adminDashboard,editorDashboard},
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'dashboard'
    ])
  },
  created() {
    if (!this.dashboard.includes('admin-dashboard')) {
      this.currentRole = 'editorDashboard'
    }
  }
}
</script>
