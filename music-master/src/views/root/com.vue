<template>
    <div>
        <div class="top">评论管理</div>
        <div class="content">
            <table>
                <thead>
                    <tr class="head">
                        <th>序号</th>
                        <th>评论</th>
                        <th>用户ID</th>
                        <th>评论时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody label-width="120px">
                    <tr v-for="com in list" :key="com.id">
                        <td>{{ com.commId }}</td>
                        <td>{{ com.commDetails }}</td>
                        <td>{{ com.commUserId }}</td>
                        <td>{{ com.commTime }}</td>
                        <td><input type="button" class="but" value="删除" @click="del(com.commId)" /></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>
<script>
import URL_CONFIG from '@/assets/js/UrlConfig.js';
export default {
    data() {
        return {
            list: []
        }
    },
    mounted() {
        this.getList()
    },
    methods: {
        getList() {
            this.$http.get(URL_CONFIG.UrlConfig.allComm)
                .then(res => {
                    console.log(res);
                    if (res.data.code === '200') {
                        this.list = res.data.data
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
        },
        del(id) {
            this.$http.delete(URL_CONFIG.UrlConfig.delComm + '/' + id)
                .then(res => {
                    console.log(res);
                    if (res.data.code === '200') {
                        alert('删除成功!');
                        this.list = [];
                        this.getList();
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
        }
    }
}
</script>
<style lang="scss" scoped>
tr,
td {
    border: 1px solid #999;
    text-align: center;
    padding: 10px 0;
}

div {
    .top {
        display: flex;
        height: 100px;
        align-items: center;
        font-size: 25px;
        padding-left: 30px;
        font-weight: 600;
        color: #545454;
        float: left;
    }

    .content {
        width: 100%;

        table {
            width: 100%;
            border-collapse: collapse;

            thead {
                tr {
                    background-color: #545a6c;
                    color: #fff;

                    th {
                        border: 1px solid #999;
                        text-align: center;
                        padding: 10px 0;
                    }
                }
            }

            tbody {
                tr {
                    &:nth-child(odd) {
                        background-color: #eee;
                    }

                    &:hover {
                        background-color: #ccc;
                    }

                    td {
                        text-align: center;

                        &:first-child {
                            color: #3d598a;
                        }
                    }

                    td {
                        .but {
                            background-color: #545a6c;
                            border: none;
                            line-height: 30px;
                            color: #fff;
                            width: 75px;
                            margin-bottom: 5px;
                        }
                    }
                }
            }
        }
    }
}
</style>
