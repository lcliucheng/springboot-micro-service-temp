<template>
    <el-dropdown :show-timeout="100" trigger="click">
        <el-button plain value="111">
            {{ btnContent}}
            <i class="el-icon-caret-bottom el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown" class="no-padding">
            <el-dropdown-item>
                <el-radio-group v-model="offline" style="padding: 10px;">
                    <el-radio :label="0">
                        上线
                    </el-radio>
                    <el-radio :label="1">
                        下线
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
        data() {
            return {
            }
        },
        computed: {
            btnContent:{
                get(){
                    if(this.offline==  0){
                        return '状态：上线';
                    }
                    if(this.offline==  1){
                        return '状态：下线';
                    }

                }
            },
        offline: {
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
