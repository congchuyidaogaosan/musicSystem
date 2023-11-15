<template>
  <div class="albumInfoBox">
    <div class="sheetBox">
      <!-- 顶部歌单信息 -->
      <img :src="musicInfo.albumImg" />
      <div class="leftBox">
        <div class="titleBox">
          <div class="showType">专辑</div>
          <div class="sheetname">{{musicInfo.albumName}}</div>
        </div>
        <div class="userInfo">
          <span class="userInfoName" v-if="musicInfo.singer != undefined">{{musicInfo.singer.singerName}}</span>
          <span>{{changeTiime(musicInfo.albumTime)}}</span>
        </div>
        <div class="userInfo">
          <span>歌曲:{{musicInfoList.length}}</span>
        </div>
        <div class="userInfo">
          <div class="userInfotext">简介:
            <span>{{musicInfo.albumDetails}}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="selectTop">
      <div v-for="(item, index) in listNav" :key="index" :class="{selectNav: item.navId === selectIndex}"
        @click="selectNavFun(item)">{{item.title}}</div>
    </div>
    <!-- 歌曲列表 -->
    <div v-if="selectIndex === 1">
      <el-table :data="musicInfoList" stripe style="width: 100%" @cell-dblclick='celldblclick' @row-click='rowclick'>
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
        <el-table-column prop="songName" label="歌名">
          <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                {{ scope.row.songName}}
              </div>
          </template>
        </el-table-column>
        <el-table-column prop="singer.singerName" label="歌手">
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
    <!-- 评论区 -->
    <div v-else-if="selectIndex === 2">
      <div class="addText_box">
        <el-input type="textarea" placeholder="请输入内容" maxlength="30" show-word-limit v-model="commentText">
        </el-input>
        <div class="addCommentBtn" @click="addComment()">提交评论</div>
      </div>
      <div class="text_box">
        <div class="text_total">最新评论({{commentsList.length}})</div>
        <div class="Text_content" v-for="(item, index) in  commentsList" :key="index">
          <img :src="item.user.userPhoto" />
          <div class="righrtBox">
            <div @click="toUserInfo(item)" class="userName_box">{{item.user.userName}}:
              <span>{{item.commDetails}}</span>
            </div>
            <div class="replay_con">
              <div>{{changeTiime(item.commTime)}}</div>
              <div class="noimage">
                <img v-if="!item.nozanFlag" @click="nozanFlagfun(item)" src="../../assets/image/nozan.png">
                <img v-else @click="nozanFlagfun(item)" src="../../assets/image/zan.png">
                <div>8099</div>
              </div>
            </div>
          </div>
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
        musicList: state => state.baseInfoStore.musicList,
        userInfos: state => state.baseInfoStore.userInfo,
        myLikeLists: state => state.baseInfoStore.myLikeList
      })
    },
    data() {
      return {
        albumId: '',
        timer: '',
        musicInfoList: [],
        musicInfo: {},
        selectIndex: 1,
        currentMusicInfo: {},
        listNav: [{
            title: '歌曲列表',
            navId: 1
          },
          {
            title: '评论',
            navId: 2
          }
        ],
        commentsList: [],
        commentText: '',
        cascaderOptions: [{
          value: 'tianjiagedan',
          label: '添加到歌单',
          children: []
        }]
      }
    },
    created() {
      this.albumId = this.$route.query.albumId;
      this.getAlbumDetails();
      this.getMusicList();
      this.getComments();
      this.getMyMusicListInfo();
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
      changeTiime(str) {
        if (str != undefined) {
          return str.substr(0, 10);
        }
      },
      getMusicList() {
        this.$http.get(URL_CONFIG.UrlConfig.songOfAlbum + '?albumId=' + this.albumId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                res.data.data.forEach((item, i) => {
                  this.$set(item, 'islove', 1);
                  this.myLikeLists.forEach(musicItem => {
                    if (item.songId === musicItem.songId) {
                      this.$set(item, 'islove', 2);
                    }
                  })
                  this.$set(item, 'isplay', 1);
                  this.$set(item, 'index', i);
                });
              }
              this.musicInfoList = res.data.data;
              this.timer = new Date().getTime();
            }
          })
      },
      getAlbumDetails() {
        this.$http.get(URL_CONFIG.UrlConfig.albumById + '?albumId=' + this.albumId)
          .then(res => {
            if (res.data.code === '200') {
              this.musicInfo = res.data.data;
            }
          })
      },
      getComments() {
        this.$http.get(URL_CONFIG.UrlConfig.allComments + '?commTargetId=' + this.albumId + '&commType=2')
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach(item => {
                this.$set(item, 'nozanFlag', false);
              });
              this.commentsList = res.data.data;
            }
          })
      },
      islovefun(data) {
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
      },
      celldblclick(data) {
        this.$store.commit('setMusicInfo', data);
        this.$store.commit('setMusicList', this.musicInfoList);
        this.$emit('getMessage', '123');
        this.musicInfoList.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        data.isplay = 2;
      },
      selectNavFun(item) {
        this.selectIndex = item.navId;
      },
      nozanFlagfun(item) {
        if (item.nozanFlag == true) {
          item.nozanFlag = false;
        } else if (item.nozanFlag == false) {
          item.nozanFlag = true;
        }
      },
      // 添加评论
      addComment() {
        this.$http.post(URL_CONFIG.UrlConfig.addComm, {
            commDetails: this.commentText,
            commTargetId: this.albumId,
            commType: 2,
            commUserId: this.userInfos.userId
          })
          .then(res => {
            if (res.data.code === '200') {
              this.getComments();
              this.commentText = '';
            }
          })
      },
      rowclick(row) {
        this.currentMusicInfo = row;
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
      addSheetFun(data) {
        // 调添加到歌单的接口
        this.$http.post(URL_CONFIG.UrlConfig.addSongToList + '/' + this.currentMusicInfo.songId + '/' + data[1])
          .then(res => {
            if (res.data.code === '200') {
              console.log('添加成功了');
            }
          })
      }
    }
  }
</script>

<style lang="scss">
  .albumInfoBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;
  }

  .sheetBox {
    margin-bottom: 20px;
    display: flex;
    gap: 20px;

    img {
      width: 170px;
      height: 170px;
    }

    .leftBox {
      .titleBox {
        display: flex;

        .showType {
          color: #DC143C;
          border: 2px solid #DC143C;
          border-radius: 5px;
          padding: 0 10px;
          line-height: 25px;
          font-size: 15px;
        }

        .sheetname {
          font-size: 18px;
          margin-left: 20px;
        }
      }

      .userInfo {
        display: flex;
        margin-top: 10px;

        span {
          font-size: 14px;
          margin-right: 20px;
          line-height: 35px;
        }

        .userInfotext {
          font-size: 14px;
          margin-right: 20px;
          line-height: 35px;

          span {
            font-size: 12px;
          }
        }

        .userInfoName {
          color: #0000CD;
        }
      }
    }
  }

  .selectTop {
    display: flex;

    div {
      font-size: 16px;
      width: 120px;
      text-align: center;
      cursor: pointer;
      line-height: 40px;
    }

    .selectNav {
      font-size: 20px;
      border-bottom: 2px solid red;
    }
  }

  .addText_box {
    margin-top: 20px;
  }

  .text_box {
    .text_total {
      font-size: 18px;
      font-weight: 500;
      line-height: 50px;
    }

    .Text_content {
      display: flex;
      margin-bottom: 40px;

      img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin-right: 20px;
      }

      .righrtBox {
        .userName_box {
          border-radius: 10px;
          width: 700px;
          background-color: #e6e6e6;
          font-size: 14px;
          // margin-bottom: 10px;
          color: $themColor;
          line-height: 30px;
          padding: 5px;

          span {
            font-size: 12px;
            color: #000;
          }
        }

        .replay_con {
          font-size: 14px;
          color: #000;
          display: flex;
          align-items: center;
          gap: 10px;
          margin-top: 10px;

          .noimage {
            img {
              width: 20px;
              height: 20px;
              cursor: pointer;
            }
          }
        }
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
