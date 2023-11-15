<template>
  <div class="userInfoBox">
    <div class="userBox">
      <div class="imgaBox">
        <img :src="userInfo.userPhoto">
      </div>
      <div class="useInfo">
        <div class="genderBox">
          <div class="userName">{{userInfo.userName}}</div>
          <div>
            <img v-if="userInfo.userGender === 1" src="../../assets/image/sex1.png" />
            <img v-else src="../../assets/image/sex2.png" />
          </div>
          <div class="userBtn">
            <el-button v-if="userInfo.userId == myInfo.userId" round @click="putUserInfo()">编辑个人信息</el-button>
            <el-button v-else-if="this.isFocus == 1 && this.isFans == 1" round @click="cancelFocus()">相互关注</el-button>
            <el-button v-else-if="this.isFocus == 1 && this.isFans == 0" round @click="cancelFocus()">已关注</el-button>
            <el-button v-else round @click="addFocus()">关注</el-button>
          </div>
        </div>
        <div class="userFans">
          <div @click="getMyFans(1)">关注({{this.focusCount}})</div>
          <div>|</div>
          <div @click="getMyFans(2)">粉丝({{this.fansCount}})</div>
        </div>
        <div class=" userArea">
          <div>所在区域：{{userInfo.userArea}}</div>
          <div>个人介绍：{{userInfo.userDetails}}</div>
        </div>
      </div>
    </div>
    <div class="listInfo">
      <div class="listAddInfoBox">
        <el-collapse v-model="activeName1">
          <el-collapse-item title="歌单" name="1">
            <div class="musicListBox">
              <div class="meunBox" v-for="(item,index) in  listInfo" :key='index'>
                <div class="meunImage" @click="musicInfoFun(item)">
                  <img :src="item.listImg">
                  <div class="footer_show">
                    <div class="imageAext">
                      <img src="../../assets/image/erji.png">
                      <span class="numberText">{{item.listPlayNum}}</span>
                    </div>
                    <img src="../../assets/image/musicPlay.png">
                  </div>
                </div>
                <span>{{item.listTitle}}</span>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <div class="addSheetBox" v-if="userInfo.userId == myInfo.userId">
        <el-tooltip class="item" effect="dark" content="添加歌单" placement="top-start">
          <div><img @click="dialogVisible = true" src="../../assets/image/add.png" /></div>
        </el-tooltip>
      </div>
    </div>
    <div class="listInfo">
      <div class="listAddInfoBox">
        <el-collapse v-model="activeName2">
          <el-collapse-item title="收藏" name="1">
            <div class="musicListBox">
              <div class="meunBox" v-for="(item,index) in  colListInfo" :key='index'>
                <div class="meunImage" @click="musicInfoFun(item)">
                  <img :src="item.listImg">
                  <div class="footer_show">
                    <div class="imageAext">
                      <img src="../../assets/image/erji.png">
                      <span class="numberText">{{item.listPlayNum}}</span>
                    </div>
                    <img src="../../assets/image/musicPlay.png">
                  </div>
                </div>
                <span>{{item.listTitle}}</span>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <!--新增歌单-->
    <div>
      <el-dialog title="新增歌单" :visible.sync="dialogVisible" width="30%">
        <el-input placeholder="请输入歌单名" v-model="listTitle"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addList()">确 定</el-button>
        </span>
      </el-dialog>
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
        activeName1: ['1'],
        activeName2: ['1'],
        size: '',
        userInfo: {},
        myInfo: {},
        listInfo: {},
        colListInfo: {},
        userId: '',
        dialogVisible: false,
        listTitle: '',
        fansCount: 0,
        focusCount: 0,
        isFocus: 0,
        isFans: 0
      }
    },
    computed: {
      ...mapState({
        userInfos: state => state.baseInfoStore.userInfo,
        userType: state => state.baseInfoStore.userType
      })
    },
    watch: {
      userType: {
        handler(newName) {
          if (newName === 1) {
            this.userId = this.userInfos.userId;
            this.getUserInfo();
            this.getMyListInfo();
            this.getMyColListInfo();
            this.getFansCount();
            this.myInfo = this.userInfos;
          }
        },
        immediate: true
      }
    },
    created() {
      this.userId = this.$route.query.userId;
      this.getUserInfo();
      this.getMyListInfo();
      this.getMyColListInfo();
      this.getFansCount();
      if (this.userInfos.userId) {
        this.isMyFans();
      }
      this.myInfo = this.userInfos;
    },
    methods: {
      getUserInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.userById + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.userInfo = res.data.data;
              }
            }
          })
      },
      getMyListInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.listByUserId + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.listInfo = res.data.data;
              }
            }
          })
      },
      getMyColListInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.colListByUserId + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.colListInfo = res.data.data;
              }
            }
          })
      },
      musicInfoFun(item) {
        this.$router.push({
          path: '/listInfo',
          query: {
            listId: item.listId
          }
        })
      },
      putUserInfo() {
        this.$router.push({
          path: '/putUser',
          query: {
            userId: this.userInfo.userId
          }
        })
      },
      addList() {
        this.$http.post(URL_CONFIG.UrlConfig.addLists, {
            listTitle: this.listTitle,
            listUserId: this.userId
          })
          .then(res => {
            if (res.data.code === '200') {
              this.dialogVisible = false;
              this.getMyListInfo();
            }
          })
      },
      getMyFans(type) {
        this.$router.push({
          path: '/fans',
          query: {
            type: type,
            userId: this.userInfo.userId,
            userName: this.userInfo.userName
          }
        })
      },
      getFansCount() {
        this.$http.get(URL_CONFIG.UrlConfig.focusCount + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.focusCount = res.data.data;
            }
          })
        this.$http.get(URL_CONFIG.UrlConfig.fansCount + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.fansCount = res.data.data;
            }
          })
      },
      isMyFans() {
        this.$http.get(URL_CONFIG.UrlConfig.userIsFocus + '?userId=' + this.userId + '&myId=' + this.userInfos.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.isFocus = res.data.data;
            }
          })
        this.$http.get(URL_CONFIG.UrlConfig.userIsFans + '?userId=' + this.userId + '&myId=' + this.userInfos.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.isFans = res.data.data;
            }
          })
      },
      cancelFocus() {
        if (this.userInfos.userId) {
          this.$http.delete(URL_CONFIG.UrlConfig.delFocus + '?userId=' + this.userId + '&myId=' + this.userInfos
              .userId +
              '&type=1')
            .then(res => {
              if (res.data.code === '200') {
                this.isFocus = 0;
              }
            })
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning'
          });
        }
      },
      addFocus() {
        if (this.userInfos.userId) {
          this.$http.post(URL_CONFIG.UrlConfig.addFocus + '?userId=' + this.userId + '&myId=' + this.userInfos.userId +
              '&type=1')
            .then(res => {
              if (res.data.code === '200') {
                this.isFocus = 1;
              }
            })
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning'
          });
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .userInfoBox {
    margin: 0 160px;
    padding: 20px 70px;
    background-color: #FFFFFF;

    .userBox {
      display: flex;
      width: 100%;
      height: 250px;

      .imgaBox {
        width: 200px;
        height: 200px;

        img {
          width: 200px;
          height: 200px;
        }
      }

      .useInfo {
        margin-left: 50px;
        width: 600px;

        .genderBox {
          display: flex;
          margin-top: 20px;
          margin-bottom: 20px;
          font-size: 25px;
          font-weight: 700;

          img {
            margin-left: 10px;
            width: 25px;
            height: 25px;
          }

          .userBtn {
            margin-left: auto;
          }
        }

        .userFans {
          display: flex;
          cursor: pointer;

          div {
            margin-right: 20px;
          }
        }

        .userArea {
          div {
            margin-top: 20px;
          }
        }
      }

    }

    .listInfo {
      display: flex;

      .listAddInfoBox {
        width: 90%;

        .musicListBox {
          display: flex;
          flex-wrap: wrap;
          gap: 50px;

          .meunBox {
            width: 180px;
            height: 234px;
            cursor: pointer;

            .meunImage {
              width: 180px;
              height: 180px;
              position: relative;

              img {
                width: 180px;
                height: 180px;
              }
            }

            span {
              font-size: 14px;
              color: #000;
              text-align: left;
              line-height: 30px;
            }

            .footer_show {
              width: 180px;
              height: 27px;
              position: absolute;
              bottom: 0px;
              background-color: #000;
              opacity: 0.8;
              display: flex;
              align-items: center;
              justify-content: space-between;

              img {
                width: 16px;
                height: 17px;
                margin-right: 10px;
              }

              .imageAext {
                display: flex;
                align-items: center;
                justify-content: space-between;

                img {
                  width: 16px;
                  height: 17px;
                  margin-left: 10px;
                }

                .numberText {
                  color: #fff;
                }
              }
            }
          }
        }
      }

      .addSheetBox {
        width: 10%;
        margin-left: 20px;

        img {
          cursor: pointer;
          width: 30px;
          height: 30px;
        }
      }
    }
  }
</style>
