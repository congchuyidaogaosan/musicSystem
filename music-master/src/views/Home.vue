<template>
  <div class="home">
    <div class="swiperImage">
      <el-carousel :interval="2000" type="card" autoplay>
        <el-carousel-item v-for="(item,index) in backImageList" :key="index">
          <img class="swiperimg" :src="item.slideImg" @click="getAlbumfun(item)">
        </el-carousel-item>
      </el-carousel>
    </div>
    <!-- 歌单 -->
    <div class="listBox">
      <div class="recomBtn" @click="toList()">
        推荐歌单>
      </div>
      <div class="musicListBox">
        <div class="meunBox" v-for="(item,index) in  meunList" :key='index'>
          <div class="meunImage" @click="musicInfoFun(item)">
            <img :src="item.listImg">
            <div class="footer_show">
              <div class="imageAext">
                <img src="../assets/image/erji.png">
                <span class="numberText">{{item.listPlayNum}}</span>
              </div>
              <img src="../assets/image/musicPlay.png">
            </div>
          </div>
          <span>{{item.listTitle}}</span>
        </div>
      </div>
    </div>
    <div class="listBox">
      <!-- 歌手 -->
      <div class="recomBtn" @click="toSinger()">
        推荐歌手>
      </div>
      <div class="musicListBox">
        <div class="meunBox" v-for="(item,index) in  singerList" :key='index'>
          <div class="meunImage" @click="singerInfoFun(item)">
            <img :src="item.singerPhoto">
            <div class="footer_show">
              <img src="../assets/image/musicPlay.png">
            </div>
          </div>
          <span>{{item.singerName}}</span>
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
    name: 'Home',
    components: {},
    computed: {
      ...mapState({
        musicList: state => state.baseInfoStore.musicList
      })
    },
    data() {
      return {
        backImageList: [],
        pageSize: 12,
        pageNum: 1,
        totalSize: 0,
        meunList: [],
        singerList: [],
        timer: ''
      }
    },
    created() {
      this.getSwiperList();
      this.getqueryLists();
      this.getSingerList();
    },
    methods: {
      getqueryLists() {
        this.$http.get(URL_CONFIG.UrlConfig.topLists)
          .then(res => {
            if (res.data.code === '200') {
              this.meunList = res.data.data;
            }
          })
      },
      getSwiperList() {
        this.$http.get(URL_CONFIG.UrlConfig.allSliders)
          .then(res => {
            if (res.data.code === '200') {
              this.backImageList = res.data.data;
            }
          })
      },
      getSingerList() {
        this.$http.get(URL_CONFIG.UrlConfig.topSinger)
          .then(res => {
            if (res.data.code === '200') {
              this.singerList = res.data.data;
            }
          })
      },
      musicInfoFun(item) {
        // 获取歌单下面的歌曲
        this.$router.push({
          path: '/listInfo',
          query: {
            listId: item.listId
          }
        })
      },
      singerInfoFun(item) {
        // 获取歌单下面的歌曲
        this.$router.push({
          path: '/singerInfo',
          query: {
            singerId: item.singerId,
            singerDetails: item.singerDetails,
            singerPhoto: item.singerPhoto,
            singerName: item.singerName
          }
        })
      },
      getAlbumfun(item) {
        console.log(456)
        this.$router.push({
          path: '/albumInfo',
          query: {
            albumId: item.slideAlbumId
          }
        })
      },
      toList() {
        this.$router.push({
          path: '/musicList'
        })
        var number = 2;
        this.$emit('getMessage', number);
      },
      toSinger() {
        this.$router.push({
          path: '/singerList'
        })
        var number = 3;
        this.$emit('getMessage', number);
      }
    }
  }
</script>
<style lang="scss">
  .home {
    .swiperImage {
      z-index: 60;
      .swiperimg {
        width: 90%;
        margin: 30px 30px;
        cursor: pointer;
      }
    }

    .listBox {
      border: 1px solid #FEFEFE;
      background-color: #FEFEFE;
      margin-bottom: 50px;

      .recomBtn {
        width: 120px;
        font-size: 25px;
        font-weight: 700;
        margin: 20px 150px;
        cursor: pointer;
      }

      .musicListBox {
        display: flex;
        flex-wrap: wrap;
        gap: 50px;
        margin: 20px 150px;

        .meunBox {
          margin-right: 20px;
          width: 180px;
          height: 220px;
          cursor: pointer;
          box-shadow: 2px 2px 10px #909090;

          .meunImage {
            width: 180px;
            height: 180px;
            position: relative;
            text-align: center;

            img {
              width: 180px;
              height: 180px;
              border-radius: 5px;
            }
          }

          span {
            font-size: 18px;
            color: #000;
            text-align: center;
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
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel,
  .el-carousel--horizontal,
  .el-carousel--card {
    z-index: 60;
  }
</style>
