<template>
  <div>
    <div>
      <el-steps :active="active" finish-status="success">
        <el-step title="输入信息"></el-step>
        <el-step title="绑定手机"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>
      <!-- <el-button style="margin-top: 12px;" @click="next">下一步</el-button> -->
    </div>
    <div class="registerBox">
      <div class="typeBox" v-if="active == 1">
        <div class="textHeader">个人信息</div>
        <div class="userinfoBox">
          <div class="rowitem">
            <div class="textName">用户名:</div>
            <el-input v-model="account" placeholder="请输入用户名"></el-input>
          </div>
          <div class="rowitem">
            <div class="textName">密码:</div>
            <el-input v-model="password" placeholder="请输入密码" show-password></el-input>
          </div>
          <div class="rowitem">
            <div class="textName">确认密码:</div>
            <el-input v-model="confirmPass" placeholder="请再次确认密码" show-password></el-input>
          </div>
        </div>
        <div class="btnBox" @click="registerfun()">下一步</div>
      </div>
      <div class="typeBox" v-else-if="active == 2">
        <div class="userinfoBox">
          <div class="textHeader">绑定手机</div>
          <div class="phoneitem">
            <div class="textName">手机号码:</div>
            <el-input v-model="userPhone" placeholder="请输入手机号"></el-input>
          </div>
          <div class="phoneitem">
            <div class="btnBox" @click="finish()">完成</div>
          </div>
        </div>
      </div>
      <div class="typeBox" v-else>
        <el-result icon="success" title="注册成功" subTitle="请根据提示进行操作">
          <template slot="extra">
            <el-button type="primary" @click="cancelLogin()">去登录</el-button>
          </template>
        </el-result>
      </div>
    </div>
  </div>
</template>

<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    data() {
      return {
        active: 1,
        account: '',
        password: '',
        confirmPass: '',
        userPhone: ''
      };
    },

    methods: {
      registerfun() {
        if (this.account == '') {
          this.$message({
            message: '请填写账号',
            type: 'warning'
          });
          return false;
        }
        if (this.password == '') {
          this.$message({
            message: '请填写密码',
            type: 'warning'
          });
          return false;
        }
        if (this.confirmPass == '') {
          this.$message({
            message: '请确认密码',
            type: 'warning'
          });
          return false;
        }
        if (this.confirmPass != this.password) {
          this.$message({
            message: '两次密码输入不一样',
            type: 'warning'
          });
          return false;
        }
        this.$http.get(URL_CONFIG.UrlConfig.userByAccount + '?account=' + this.account)
          .then(res => {
            if (res.data.code === '200') {
              this.active = 2;
            } else {
              this.$message.error(res.data.msg);
            }
          })
      },
      finish() {
        if (this.userPhone == '') {
          this.$message({
            message: '请填写手机号',
            type: 'warning'
          });
          return false;
        }
        this.$http.post(URL_CONFIG.UrlConfig.register, {
                  account: this.account,
                  password: this.password,
                  userPhone: this.userPhone
                })
                .then(res => {
                  if (res.data.code === '200') {
                    this.active = 3;
                  }
                })
      },
      cancelLogin() {
        this.$router.push({
          path: '/login',
          query: {
            account: this.account,
            password: this.password
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .registerBox {
    width: 100%;
    height: 450px;
    margin-top: 20px;
    padding-top: 20px;
    background: url(../../assets/image/resiBancimage.png) no-repeat center center;
    background-size: 100%;
    font-weight: 500;
    font-size: 22px;
    display: flex;
    align-items: center;
    justify-content: center;

    .typeBox {
      width: 400px;
      height: 400px;
      text-align: center;
      background-color: #fff;
      margin-right: 30px;
      border-radius: 10px;

      // padding-top: 20px;
      // opacity: 0.8;
      .userinfoBox {
        width: 80%;
        margin: 0 auto;

        .rowitem {
          display: flex;
          justify-content: space-between;
          opacity: 1;
          margin-bottom: 30px;

          .textName {
            width: 90px;
            margin-right: 10px;
            font-size: 14px;
            line-height: 35px;
          }
        }

        .phoneitem {
          display: flex;
          justify-content: space-between;
          opacity: 1;
          margin-top: 30px;

          .textName {
            width: 77px;
            margin-right: 10px;
            font-size: 14px;
            line-height: 35px;
          }
        }
      }

      .btnBox {
        width: 70px;
        line-height: 35px;
        background-color: rgb(39, 208, 216);
        text-align: center;
        color: #fff;
        font-size: 16px;
        font-weight: 400;
        margin: 20px auto;
        border-radius: 5px;
      }

      .validCode {
        width: 150px;
        line-height: 35px;
        text-align: center;
        background-color: #f2f6fc;
        color: rgb(39, 208, 216);
        font-size: 16px;
        font-weight: 400;
        border-radius: 5px;
        margin-left: 10px;
      }
    }

    .textHeader {
      opacity: 1;
      line-height: 70px;
    }
  }
</style>
