<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const { icon, title,type } = context.props
    const vnodes = []

    if (icon) {
      if(type && type == 'element'){
        vnodes.push(<i style="vertical-align: middle;margin-right: 17px;  width: 1em;" class={icon}></i>)
      }else{
        vnodes.push(<svg-icon icon-class={icon}/>)

      }
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>
