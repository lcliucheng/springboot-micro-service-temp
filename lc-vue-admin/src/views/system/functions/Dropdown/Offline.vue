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
                        否
                    </el-radio>
                    <el-radio :label="1">
                        是
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
                        return '是否下线：否';
                    }
                    if(this.offline==  1){
                        return '是否下线：是';
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
