<template>
  <div class="fansMainBox">
    <div class="fansTitle" v-if="this.type == 1">{{this.userName}}的关注</div>
    <div class="fansTitle" v-else>{{this.userName}}的粉丝</div>
    <div class="fansBox" v-if="this.fansInfo.length>0">
      <div class="fansInfo" v-for="(item,index) in fansInfo" :key="index">
        <div class="infoBox" v-if="item.user != null">
          <div><img :src="item.user.userPhoto" /></div>
          <div class="fansName">{{item.user.userName}}</div>
          <div class="btnInfo">
            <el-button class="fansBtn" type="small" @click="toUserInfo(item)" round>查看主页</el-button>
          </div>
        </div>
        <div class="infoBox" v-else>
          <div><img :src="item.singer.singerPhoto" /></div>
          <div class="fansName">{{item.singer.singerName}}</div>
          <div class="btnInfo">
            <el-button class="fansBtn" type="small" @click="toSingerInfo(item)" round>查看主页</el-button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <el-empty :image-size="200"></el-empty>
    </div>
  </div>
</template>

<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    data() {
      return {
        userId: '',
        userName: '',
        type: 0,
        fansInfo: []
      }
    },
    created() {
      this.userId = this.$route.query.userId;
      this.userName = this.$route.query.userName;
      this.type = this.$route.query.type;
      if (this.type == 1) {
        this.getFocusInfo();
      } else {
        this.getFansInfo();
      }
    },
    methods: {
      getFocusInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.myFocus + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.fansInfo = res.data.data;
            }
          })
      },
      getFansInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.myFans + '?userId=' + this.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.fansInfo = res.data.data;
            }
          })
      },
      toUserInfo(item) {
        this.$router.push({
          path: '/userInfo',
          query: {
            userId: item.user.userId
          }
        })
        this.$store.commit('setUserType', 2);
      },
      toSingerInfo(item) {
        this.$router.push({
          path: '/singerInfo',
          query: {
            singerId: item.singer.singerId,
            singerName: item.singer.singerName,
            singerPhoto: item.singer.singerPhoto,
            singerDetails: item.singer.singerDetails
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .fansMainBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;
  }

  .fansTitle {
    font-size: 25px;
    font-weight: 600;
    height: 30px;
    margin-left: 50px;
  }

  .fansBox {
    display: flex;
    flex-wrap: wrap;

    .fansInfo {
      flex: 1;
      width: 50%;
      padding: 10px 20px;

      .infoBox {
        background-color: #F2F6FC;
        width: 500px;
        height: 110px;
        line-height: 200px;
        display: flex;
        border-radius: 30px;
        position: relative;

        .fansName {
          margin-left: 30px;
          font-size: 18px;
          font-weight: 500;
          line-height: 60px;
        }

        .fansBtn {
          margin-left: 40px;
          position: absolute;
          top: 40px;
          right: 30px;
        }

        img {
          margin-left: 10px;
          width: 100px;
          height: 100px;
          border-radius: 100px;
        }
      }
    }
  }
</style>
