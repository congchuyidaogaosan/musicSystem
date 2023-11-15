<template>
  <div>
    <div class="HeaderTop">
      <div class="logoBox">
        <img src="../assets/image/headerLogo.png">
        Music
      </div>
      <div class="headreTopSel">
        <div v-for="(item, index) in menuList" :key="index" :class="{selectDiv: item.menuId === selectIndex}"
          @click="selectfun(item.menuId)">{{item.title}}</div>
      </div>
      <div class="userInfoBox">
        <div class="inputBox">
          <el-input v-model="searchWord" placeholder="搜索">
          </el-input>
          <i class="el-icon-zoom-in" @click="search()"></i>
        </div>
        <div>
          <el-dropdown v-if="userInfo.userId" @command="handleCommand">
            <span class="el-dropdown-link">
              <img class="userPhoto" :src="userInfo.userPhoto" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="update">修改密码</el-dropdown-item>
              <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div>
          <span @click="tologin()" v-if="!userInfo.userId">登陆/注册</span>
          <span v-else>{{userInfo.userName}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    mapState
  } from 'vuex';
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    name: 'HelloWorld',
    props: {
      partInfo: Number
    },
    computed: {
      ...mapState({
        selIndex: state => state.baseInfoStore.selectIndex,
        userInfos: state => state.baseInfoStore.userInfo,
        otherUser: state => state.baseInfoStore.otherUser
      })
    },
    data() {
      return {
        account: '',
        password: '',
        userInfo: {},
        searchWord: '',
        menuList: [{
            title: '首页',
            menuId: 1
          },
          {
            title: '歌单',
            menuId: 2
          },
          {
            title: '歌手',
            menuId: 3
          },
          {
            title: '个人中心',
            menuId: 4
          }
        ],
        selectIndex: 1,
        flag: true,
        infos: 0
      }
    },
    watch: {
      partInfo() {
        this.infos = this.partInfo;
        this.selectfun(this.infos);
        this.userInfo = this.userInfos;
      }
    },
    created() {
      this.selectIndex = this.selIndex;
      this.userInfo = this.userInfos;
      if (this.userInfo != null && !this.userInfo.userId) {
        this.login();
      }
    },
    methods: {
      selectfun(index) {
        if (index == 1) {
          this.$store.commit('setSelectIndex', index);
          this.selectIndex = index;
          this.$router.push('/');
        } else if (index == 2) {
          this.$store.commit('setSelectIndex', index);
          this.selectIndex = index;
          this.$router.push('/musicList')
        } else if (index == 3) {
          this.$store.commit('setSelectIndex', index);
          this.selectIndex = index;
          this.$router.push('/singerList')
        } else if (index == 4) {
          if (!this.userInfo.userId) {
            this.$message({
              message: '请先登录',
              type: 'warning'
            });
          } else {
            this.$store.commit('setSelectIndex', index);
            this.$store.commit('setUserType', 1);
            this.selectIndex = index;
            this.$router.push({
              path: '/userInfo',
              query: {
                userId: this.userInfo.userId
              }
            })
          }
        }
      },
      handleClose() {
        this.dialogVisible = false;
      },
      register() {
        if (this.account == '') {
          this.$message({
            message: '请输入账号',
            type: 'warning'
          });
          return false;
        }
        if (this.password == '') {
          this.$message({
            message: '请输入密码',
            type: 'warning'
          });
          return false;
        }
        if (this.newpassword == '') {
          this.$message({
            message: '请再次输入密码',
            type: 'warning'
          });
          return false;
        }
        if (this.newpassword != this.password) {
          this.$message({
            message: '两次输入的密码不相同，请核对后再次输入',
            type: 'warning'
          });
          return false;
        }
        this.$http.post(URL_CONFIG.UrlConfig.register, {
            account: this.account,
            password: this.password
          })
          .then(res => {
            if (res.data.code === '200') {
              this.showLoginBtnflag = true;
            }
          })
      },
      login() {
        this.$http.get(URL_CONFIG.UrlConfig.login + '?account=' + this.account + '&password=' + this.password)
          .then(res => {
            if (res.data.code === '200') {
              this.dialogVisible = false;
              this.userInfo = res.data.data.user;
              this.$store.commit('setUserInfo', res.data.data.user);
              this.$store.commit('setMyLikeInfo', res.data.data.list);
            }
          })
      },
      logout() {
        this.$http.get(URL_CONFIG.UrlConfig.logout + '?account=' + this.userInfo.account + '&password=' + this.userInfo
            .password)
          .then(res => {
            if (res.data.code === '200') {
              this.$store.commit('setUserInfo', {});
              this.userInfo = {};
              this.$store.commit('setSelectIndex', 1);
              this.selectIndex = 1;
              this.$router.push('/');
            }
          })
      },
      handleCommand(command) {
        if (command == 'loginOut') {
          this.logout();
        } else {}
      },
      tologin() {
        this.$router.push('/login')
      },
      search() {
        this.$router.push({
          path: '/songResult',
          query: {
            searchWord: this.searchWord
          }
        });
        this.$store.commit('setSearchWord', this.searchWord);
      }
    }
  }
</script>

<style lang="scss" scoped>
  .HeaderTop {
    position: fixed;
    top: 0px;
    left: 0px;
    right: 0px;
    z-index: 100;
    // width: 100%;
    height: 60px;
    padding: 0px 150px;
    background-color: #fff;
    box-shadow: 2px 2px 10px #909090;
    display: flex;

    .logoBox {
      display: flex;
      align-items: center;
      font-size: 20px;
      font-weight: 400;
      color: #000;

      img {
        width: 40px;
        height: 40px;
        margin-right: 20px;
      }
    }

    .userInfoBox {
      display: flex;
      align-items: center;
      gap: 20px;
      font-size: 18px;
      font-weight: 400;
      color: #000;

      div {
        cursor: pointer;
      }
    }

    .headreTopSel {
      display: flex;
      margin-left: 50px;
      margin-right: 150px;

      div {
        width: 100px;
        text-align: center;
        line-height: 60px;
        cursor: pointer;
      }

      .selectDiv {
        border-bottom: 2px solid $themColor;
        color: $themColor;
      }
    }
  }

  .el-dialog {
    width: 400px;

    .dialog-footer {
      margin: 0px auto;
      width: 210px;

      div {
        text-align: center;
        margin: 0 auto;
        width: 100%;
        line-height: 35px;
        cursor: pointer;
        border-radius: 10px;
        color: #fff;
        background-color: #1989fa;
        border: 1px solid #1989fa;
      }

      p {
        width: 100%;
        text-align: center;
      }

      .sureBtn {
        width: 100px;
      }
    }
  }

  .userPhoto {
    width: 50px;
    height: 50px;
    border-radius: 50px;
  }

  .accountInput {
    width: 80%;
    margin: 20px auto;
    display: flex;
    align-items: center;

    .textDes {
      font-weight: 500;
      font-size: 14px;
      width: 110px;
    }

    .el-input {
      width: 230px;
    }

    div {
      font-weight: 500;
    }
  }

  .loginBoxImage {
    margin: 0px auto;
    width: 50px;
    height: 50px;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .inputBox {
    padding: 0px 10px;
    display: flex;
    gap: 10px;
    border: 1px solid #ccc;
    align-items: center;
    border-radius: 10px;
  }

  .el-dropdown-menu,
  .el-popper {
    width: 150px;
    font-size: 16px;
    text-align: center;
    white-space: 2px;
    left: 1100px !important;

    /deep/.popper__arrow {
      left: 80px !important;
    }
  }

  /deep/.el-input__inner {
    border: 0px !important;
  }
</style>
