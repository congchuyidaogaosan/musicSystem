<template>
  <div>
    <div class="top">用户管理</div>
    <div class="content">
      <table>
        <thead>
          <tr class="head">
            <th>序号</th>
            <th>用户名</th>
            <th>用户账号</th>
            <th>密码</th>
            <th>手机号</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody label-width="120px">
          <tr v-for="user in list" :key="user.id">
            <td>{{user.userId}}</td>
            <td>{{user.userName}}</td>
            <td>{{user.account}}</td>
            <td>{{user.password}}</td>
            <td>{{user.userPhone}}</td>
            <td><input type="button" class="but" value="删除" @click="del(user.userId)"/></td>
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
    mounted () {
        this.getList()
    },
    methods: {
        getList () {
            this.$http.get(URL_CONFIG.UrlConfig.allUser)
            .then(res => {
                console.log(res);
            if (res.data.code === '200') {
                this.list = res.data.data
            } else {
              this.$message.error(res.data.msg);
            }
          })
        },
        del (id) {
            this.$http.get(URL_CONFIG.UrlConfig.delUser + '?userId=' + id)
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
              width: 60%;
              margin-bottom: 5px;
            }
          }
        }
      }
    }
  }
}
</style>
