<template>
  <div class="sigerInfoBox">
    <div class="headerInfo">
      <div class="imgaBox">
        <img :src="singerPhoto">
      </div>
      <div class="singerInfo">
        <div class="name_box">
          <div class="name">{{singerName}}</div>
          <div class="userBtn">
            <el-button v-if="isFocus == 1" round @click="cancelFocus()">已关注</el-button>
            <el-button v-else round @click="addFocus()">关注</el-button>
          </div>
        </div>
        <div>简介:{{singerDetails}}</div>
        <div>歌曲数:{{songList.length}} <span style="width:30px"></span> 专辑数:{{albumList.length}}</div>
      </div>
    </div>
    <div class="footerBox">
      <div v-for="(item, index) in menuList" :key="index" :class="{selectDiv: item.menuId === selectIndex}"
        @click="selectfun(item.menuId)">{{item.title}}</div>
    </div>
    <div v-if="selectIndex == 1">
      <el-table :data="songList" stripe style="width: 100%" @cell-dblclick='celldblclick' @row-click='rowclick'>
        <el-table-column label="序号" width="80">
          <template slot-scope="scope" class="imageBox">
            <span v-if="scope.row.isplay == 1">{{scope.row.index + 1}}</span>
            <img v-else-if="scope.row.isplay == 2" src="../../assets/image/paly01.png">
          </template>
        </el-table-column>
        <el-table-column label="收藏" width="120">
          <template slot-scope="scope" class="imageBox">
            <img v-if="scope.row.islove == 1" @click="islovefun(scope.row)" src="../../assets/image/love.png">
            <img v-else-if="scope.row.islove == 2" @click="islovefun(scope.row)" src="../../assets/image/redlove.png">
          </template>
        </el-table-column>
        <el-table-column label="歌名">
          <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                {{ scope.row.songName }}
              </div>
          </template>
        </el-table-column>
        <el-table-column prop="album.albumName" label="专辑">
        </el-table-column>
        <el-table-column prop="address" label="操作">
          <div class="block">
            <el-popover trigger="click" placement="top">
              <el-cascader-panel :options="cascaderOptions" @change='addSheetFun'></el-cascader-panel>
              <div slot="reference" class="name-wrapper"><img src="../../assets/image/more.png" /></div>
            </el-popover>
          </div>
        </el-table-column>
      </el-table>
    </div>
    <!-- 专辑 -->
    <div v-else-if="selectIndex == 2" class="ablumBox">
      <div class="ablumItem" v-for="(item, index) in albumList" :key="index" @click="selsectAblum(item)">
        <img :src="item.albumImg">
        <div>{{item.albumName}}</div>
        <div class="timeBox">{{changeTime(item.albumTime)}}</div>
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
    data() {
      return {
        singerId: '',
        menuList: [{
            title: '歌曲',
            menuId: 1
          },
          {
            title: '专辑',
            menuId: 2
          }
        ],
        selectIndex: 1,
        albumList: [],
        songList: [],
        singerDetails: '',
        singerPhoto: '',
        singerName: '',
        currentMusicInfo: {},
        isFocus: 0,
        cascaderOptions: [{
          value: 'tianjiagedan',
          label: '添加到歌单',
          children: []
        }]
      }
    },
    computed: {
      ...mapState({
        userInfos: state => state.baseInfoStore.userInfo,
        myLikeLists: state => state.baseInfoStore.myLikeList
      })
    },
    created() {
      this.singerId = this.$route.query.singerId;
      this.singerDetails = this.$route.query.singerDetails;
      this.singerPhoto = this.$route.query.singerPhoto;
      this.singerName = this.$route.query.singerName;
      this.getallInfo();
      this.getallsong();
      if (this.userInfos.userId) {
        this.getMyMusicListInfo();
        this.singerIsFocus();
      }
    },
    methods: {
      getMyMusicListInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.listByUserId + '?userId=' + this.userInfos.userId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.myMusiclist = res.data.data;
                this.myMusiclist.forEach(item => {
                  this.cascaderOptions[0].children.push({
                    label: item.listTitle,
                    value: item.listId
                  })
                })
              }
            }
          })
      },
      getallInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.albumOfSinger + '?singerId=' + this.singerId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.albumList = res.data.data;
              }
            }
          })
      },
      getallsong() {
        this.$http.get(URL_CONFIG.UrlConfig.songOfSinger + '?singerId=' + this.singerId)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'islove', 1);
                if (this.userInfos.userId) {
                  this.myLikeLists.forEach(musicItem => {
                    if (item.songId === musicItem.songId) {
                      this.$set(item, 'islove', 2);
                    }
                  })
                }
                this.$set(item, 'isplay', 1);
                this.$set(item, 'index', i);
              });
              this.songList = res.data.data;
            }
          })
      },
      selectfun(index) {
        this.selectIndex = index;
      },
      changeTime(str) {
        if (str != undefined) {
          return str.substr(0, 10);
        }
      },
      celldblclick(data) {
        this.$store.commit('setMusicInfo', data);
        this.$store.commit('setMusicList', this.songList);
        this.$emit('getMessage', '123');
        this.songList.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        data.isplay = 2;
      },
      islovefun(data) {
        if (this.userInfos.userId) {
          if (data.islove == 1) {
            this.$http.post(URL_CONFIG.UrlConfig.addSongOfUser + '?userId=' + this.userInfos.userId + '&songId=' + data
                .songId)
              .then(res => {
                if (res.data.code === '200') {
                  this.$store.commit('setMyLikeInfo', res.data.data);
                  data.islove = 2;
                }
              })
          } else if (data.islove == 2) {
            this.$http.delete(URL_CONFIG.UrlConfig.delSongOfUser + '/' + this.userInfos.userId + '/' + data.songId)
              .then(res => {
                if (res.data.code === '200') {
                  this.$store.commit('setMyLikeInfo', res.data.data);
                  data.islove = 1;
                  this.getMusicList();
                }
              })
          }
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning'
          });
        }
      },
      selsectAblum(item) {
        this.$router.push({
          path: '/albumInfo',
          query: {
            albumId: item.albumId
          }
        })
      },
      rowclick(row) {
        this.currentMusicInfo = row;
      },
      addSheetFun(data) {
        // 调添加到歌单的接口
        this.$http.post(URL_CONFIG.UrlConfig.addSongToList + '/' + this.currentMusicInfo.songId + '/' + data[1])
          .then(res => {
            if (res.data.code === '200') {
              console.log('添加成功了');
            }
          })
      },
      singerIsFocus() {
        this.$http.get(URL_CONFIG.UrlConfig.singerIsFocus + '?userId=' + this.userInfos.userId + '&singerId=' + this
            .singerId)
          .then(res => {
            if (res.data.code === '200') {
              this.isFocus = res.data.data;
            }
          })
      },
      cancelFocus() {
        if (this.userInfos.userId) {
          this.$http.delete(URL_CONFIG.UrlConfig.delFocus + '?userId=' + this.singerId + '&myId=' + this.userInfos
              .userId + '&type=2')
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
          this.$http.post(URL_CONFIG.UrlConfig.addFocus + '?userId=' + this.singerId + '&myId=' + this.userInfos
              .userId +
              '&type=2')
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
<style lang='scss' scoped>
  .sigerInfoBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;

    .headerInfo {
      display: flex;

      .imgaBox {
        width: 250px;
        height: 250px;

        img {
          width: 100%;
          height: 100%;
          z-index: 9;
        }
      }

      .singerInfo {
        background-color: #000;
        padding-left: 30px;
        opacity: 0.7;
        font-size: 20px;
        width: 845px;

        div {
          width: 700px;
          margin-top: 10px;
          color: #fff;
        }

        .name_box {
          display: flex;
          font-size: 30px;
          font-weight: 700;

          .userBtn {
            margin-left: 400px;
          }
        }
      }
    }

    .footerBox {
      display: flex;
      margin-top: 20px;
      margin-bottom: 20px;

      div {
        width: 100px;
        text-align: center;
        line-height: 50px;
        font-size: 20px;
        cursor: pointer;
      }

      .selectDiv {
        font-size: 24px;
        border-bottom: 2px solid $themColor;
        color: $themColor;
      }
    }

    .ablumBox {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;

      .ablumItem {
        width: 200px;
        height: 300px;

        img {
          width: 100%;
          display: block;
          border-radius: 10px;
        }

        div {
          font-size: 18px;
          font-weight: 500;
          color: #2c2c2c;
        }

        .timeBox {
          font-size: 16px;
          color: #ccc;
        }
      }
    }

    .menu {
      position: absolute;
      background-color: #fff;
      width: 150px;
      /*height: 106px;*/
      font-size: 12px;
      color: #444040;
      border-radius: 4px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      border-radius: 3px;
      border: 1px solid rgba(0, 0, 0, 0.15);
      box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
      white-space: nowrap;
      z-index: 1000;

      div {
        text-align: center;
        line-height: 40px;
        font-size: 18px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.15);
      }
    }
  }

  .el-table__row {
    td {
      .cell {
        img {
          width: 20px;
          height: 20px;
          margin-right: 10px;
          cursor: pointer;
        }
      }
    }
  }
</style>
