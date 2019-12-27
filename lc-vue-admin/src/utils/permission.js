import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(fucntId) {
  const route = this.$store.getters.sourceRoute;
  const hasPermission = route.some(item => {
    if(!item){
      return false;
    }
    return item.id == fucntId;
  });

  if (!hasPermission) {
    return false
  }
  return true
}

