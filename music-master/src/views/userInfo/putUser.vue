<template>
  <div class="putUserBox">
    <div class="userTitle">
      编辑个人信息
    </div>
    <div class="userBox" v-if="saveSUCCESS">
      <div class="leftBox">
        <div class="infoBox">
          <div class="leftInfo">昵称：</div>
          <div class="rightInfo">
            <el-input :clearable="clearable" placeholder="请输入昵称" v-model="userName"></el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">个人简介：</div>
          <div class="rightInfo">
            <el-input maxlength="100" show-word-limit type="textarea" :rows="3" placeholder="请输入简介"
              v-model="userInfo.userDetails">
            </el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">性别：</div>
          <div class="rightInfo">
            <el-radio v-model="userInfo.userGender" :label="1">男</el-radio>
            <el-radio v-model="userInfo.userGender" :label="0">女</el-radio>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">手机号：</div>
          <div class="rightInfo">
            <el-input :clearable="clearable" placeholder="请输入地区" v-model="userInfo.userPhone"></el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">地区：</div>
          <div class="rightInfo">
            <el-input :clearable="clearable" placeholder="请输入地区" v-model="userInfo.userArea"></el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">生日：</div>
          <div class="rightInfo">
            <el-date-picker format="yyyy-MM-dd" v-model="userInfo.userBirth" type="date" placeholder="选择日期">
            </el-date-picker>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo"></div>
          <div class="rightInfo">
            <div>
              <el-button type="primary" @click="saveInfo()" plain>保存</el-button>
              <el-button type="info" @click="cancelInfo()" plain>取消</el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="rightBox">
        <div><img :src="headerImage"></div>
        <div>
          <el-upload class="upload-demo" :action="action" :on-success="uploadSuccess">
            <el-button round size="small">修改头像</el-button>
          </el-upload>
        </div>
      </div> -->
    </div>
    <div v-else>
      <el-result icon="success" title="保存成功" subTitle="请根据提示进行操作">
        <template slot="extra">
          <el-button type="primary" size="small" @click="cancelInfo()">返回</el-button>
        </template>
      </el-result>
    </div>
  </div>
</template>

<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  import {
    mapState
  } from 'vuex';
  export default {
    data() {
      return {
        userInfo: {},
        clearable: true,
        saveSUCCESS: true,
        // action: URL_CONFIG.UrlConfig.upload,
        headerImage: '',
        userName: ''
      }
    },
    computed: {
      ...mapState({
        userInfos: state => state.baseInfoStore.userInfo
      })
    },
    created() {
      this.userInfo = this.userInfos;
      this.headerImage = this.userInfo.userPhoto;
      this.userName = this.userInfo.userName;
    },

    methods: {
      saveInfo() {
        this.$http.put(URL_CONFIG.UrlConfig.putUser, {
            userId: this.userInfo.userId,
            userName: this.userName,
            userArea: this.userInfo.userArea,
            userDetails: this.userInfo.userDetails,
            userGender: this.userInfo.userGender,
            userPhone: this.userInfo.userPhone,
            userPhoto: this.headerImage,
            userBirth: this.userInfo.userBirth
          })
          .then(res => {
            if (res.data.code === '200') {
              this.saveSUCCESS = false;
              this.getUserInfo();
            }
          })
      },
      getUserInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.userById + '?userId=' + this.userInfo.userId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.$store.commit('setUserInfo', res.data.data);
                this.$emit('getMessage', 5);
              }
            }
          })
      },
      cancelInfo() {
        this.$router.push({
          path: '/userInfo',
          query: {
            userId: this.userInfo.userId
          }
        })
      }
      // uploadSuccess(res, file) {
      //   console.log(res);
      //   this.headerImage = res.data;
      // }
    }
  }
</script>

<style lang="scss" scoped>
  .putUserBox {
    margin: 0 160px;
    padding: 30px 70px;
    background-color: #FFFFFF;

    .userTitle {
      font-size: 25px;
      font-weight: 600;
    }

    .userBox {
      display: flex;
      width: 100%;

      .leftBox {
        width: 70%;

        .infoBox {
          display: flex;
          width: 100%;
          align-items: center;
          margin-top: 20px;
          line-height: 50px;

          .leftInfo {
            width: 20%;
          }

          .rightInfo {
            width: 70%;

            div {
              text-align: center;
            }
          }
        }
      }

      .rightBox {
        margin-left: 50px;
        width: 20%;
        height: 200px;
        text-align: center;

        div {
          margin-top: 20px;
        }

        img {
          width: 200px;
          height: 200px;
        }
      }
    }
  }
</style>
