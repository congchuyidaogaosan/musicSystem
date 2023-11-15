<template>
  <div>
    <div class="headerBox"></div>
    <div class="formBox">
      <div>
        <img src="../../assets/image/bg.jpg" width="100%" height="100%" alt="" />
      </div>
      <div class="loginBox">
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="账号登录" name="first">
            <div class="accountInput">
              <el-input v-model="account" placeholder="账号"></el-input>
            </div>
            <div class="accountInput">
              <el-input type="password" v-model="password" placeholder="密码"></el-input>
            </div>
            <div class="accountInput">
              <div class="remeberMe">
                <el-checkbox v-model="checked">自动登录</el-checkbox>
                <p @click="forgetPass()">
                  <el-link style="color:rgb(39, 208, 216);font-size: 10px;">忘记密码</el-link>
                </p>
              </div>
            </div>
            <div class="accountInput">
              <div class="loginBtn">
                <el-button @click="login()"
                  style="width: 200px;background-color:rgb(39, 208, 216);color: #ECF5FF;font-size: 15px;line-height: 35px;">
                  登录
                </el-button>
                <p @click="register()">
                  <el-link style="color:rgb(39, 208, 216);font-size: 10px;">注册</el-link>
                </p>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    data() {
      return {
        account: '',
        password: '',
        checked: '',
        activeName: 'first',
        content: '发送验证码',
        totalTime: 60,
        userPhone: '',
        validCode: ''
      }
    },
    created() {
      this.account = this.$route.query.account;
      this.password = this.$route.query.password;
    },
    methods: {
      login() {
        this.$http.get(URL_CONFIG.UrlConfig.login + '?account=' + this.account + '&password=' + this.password)
          .then(res => {
            console.log(res);
            if (res.data.code === '200') {
              this.$store.commit('setUserInfo', res.data.data.user);
              this.$store.commit('setMyLikeInfo', res.data.data.list);
              this.$router.push('/')
              this.$emit('getMessage', 'user');
            }
          })
      },
      loginPhone() {
        this.$http.post(URL_CONFIG.UrlConfig.validSMS + '?userPhone=' + this.userPhone + '&code=' + this.validCode)
          .then(res => {
            if (res.data.code === '200') {
              this.$http.get(URL_CONFIG.UrlConfig.loginPhone + '?userPhone=' + this.userPhone)
                .then(res => {
                  if (res.data.code === '200') {
                    this.$store.commit('setUserInfo', res.data.data);
                    this.$router.push('/')
                    this.$emit('getMessage', 'user');
                  }
                })
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning'
              });
            }
          })
      },
      forgetPass() {},
      register() {
        this.$router.push('/register')
      }
    }
  }
</script>

<style lang="scss" scoped>
  .headerBox {
    width: 100%;
    height: 100px;
    margin-top: 10px;
    font-size: 20px;
    font-weight: 500;

    img {
      width: 40px;
      height: 40px;
    }
  }

  .formBox {

    font-size: 15px;
    width: 100%;
    height: 350px;
    margin-top: 10px;
    position: relative;

    img {
      width: 100%;
      height: 350px;
      z-index: -1;
    }

    .loginBox {
      width: 400px;
      height: 400px;
      box-shadow: 2px 2px 10px #909090;
      position: absolute;
      right: 100px;
      top: 30px;
      background-color: white;

      .accountInput {
        width: 300px;
        text-align: center;
        margin: 40px auto;

        .remeberMe {
          display: flex;
          flex-wrap: wrap;
          justify-content: space-between;
          gap: 20px;

          .validCode {
            border-radius: 10px;
            line-height: 35px;
            cursor: pointer;
            width: 100px;
            background-color: #f2f6fc;
            color: rgb(39, 208, 216);
            font-size: 15px;
          }
        }

        .loginBtn {
          display: flex;
          flex-wrap: wrap;
          justify-content: space-between;
          gap: 20px;
        }
      }
    }
  }

  .el-tabs__nav-wrap {
    padding: 0px;
    text-align: center;
  }

  .is-scrollable {
    padding: 0px;
    text-align: center;
    border: 1px solid red;
  }

  .el-tabs__nav.is-top {
    width: 100%;
  }

  .el-tabs__item.is-top {
    width: 50%;
  }
</style>
