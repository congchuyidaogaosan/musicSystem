<template>
  <div class="listInfoBox">
    <div class="sheetBox">
      <!-- 顶部歌单信息 -->
      <img :src="listInfo.listImg" />
      <div class="leftBox">
        <div class="titleBox">
          <div class="showType">歌单</div>
          <div class="sheetname">{{listInfo.listTitle}}</div>
          <div class="userBtn" v-if="listInfo.user != undefined">
            <img v-if="userInfos.userId == listInfo.user.userId" src="../../assets/image/edit.png"
              @click="putListInfo()" />
            <img v-else-if="userInfos.userId != listInfo.user.userId && isCollect == 0"
              src="../../assets/image/collect0.png" @click="colList()" />
            <img v-else src="../../assets/image/collect1.png" @click="cancelCol()" />
          </div>
        </div>
        <div class="userInfo">
          <img class="userInfoImage" v-if="listInfo.user != null" :src="listInfo.user.userPhoto">
          <span class="userInfoName spanBox" v-if="listInfo.user != undefined">{{listInfo.user.userName}}</span>
          <span class="spanBox">{{changeTiime(listInfo.listTime)}}创建</span>
          <el-tag size="success" v-if="listInfo.listType == 2">经典</el-tag>
          <el-tag size="success" v-else-if="listInfo.listType == 3">流行</el-tag>
          <el-tag size="success" v-else-if="listInfo.listType == 4">民谣</el-tag>
          <el-tag size="success" v-else-if="listInfo.listType == 5">摇滚</el-tag>
          <el-tag size="success" v-else-if="listInfo.listType == 6">安静</el-tag>
        </div>
        <div class="userInfo">
          <span>歌曲:{{musicInfo.length}}</span>
          <span>播放量:{{listInfo.listPlayNum}}</span>
        </div>
        <div class="userInfo">
          <span>简介:{{listInfo.listDetails}}</span>
        </div>
      </div>
    </div>
    <div class="selectTop">
      <div v-for="(item, index) in listNav" :key="index" :class="{selectNav: item.navId === selectIndex}"
        @click="selectNavFun(item)">{{item.title}}</div>
    </div>
    <!-- 歌曲列表 -->
    <div v-if="selectIndex === 1">
      <el-table :data="musicInfo" stripe style="width: 100%" @cell-dblclick='celldblclick' @row-click='rowclick'>
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
            <div>
              {{ scope.row.songName }}
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
        listId: '',
        timer: '',
        musicInfo: [],
        myLikeList: [],
        listInfo: {},
        selectIndex: 1,
        isCollect: 0,
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
        myMusiclist: [],
        currentMusicInfo: {},
        cascaderOptions: [{
          value: 'tianjiagedan',
          label: '添加到歌单',
          children: []
        }]
      }
    },
    created() {
      this.listId = this.$route.query.listId;
      this.getListDetails();
      this.getMusicList();
      this.getComments();
      if (this.userInfos.userId) {
        this.getMyMusicListInfo();
        this.isColfun();
      }
    },
    methods: {
      handleChange(value) {
        console.log(value);
      },
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
        this.$http.get(URL_CONFIG.UrlConfig.songOfLists + '?listId=' + this.listId)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'islove', 1);
                if (this.userInfos.userId) {
                  this.myLikeLists.forEach(musicItem => {
                    if (item.songId === musicItem.songId) {
                      this.$set(item, 'islove', 2);
                      return false;
                    }
                  })
                }
                this.$set(item, 'index', i);
                this.$set(item, 'isplay', 1);
              });
              this.musicInfo = res.data.data;
              this.timer = new Date().getTime();
            }
          })
      },
      getListDetails() {
        this.$http.get(URL_CONFIG.UrlConfig.listById + '?listId=' + this.listId)
          .then(res => {
            if (res.data.code === '200') {
              this.listInfo = res.data.data;
              this.timer = new Date().getTime();
            }
          })
      },
      getComments() {
        this.$http.get(URL_CONFIG.UrlConfig.allComments + '?commTargetId=' + this.listId + '&commType=1')
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach(item => {
                this.$set(item, 'nozanFlag', false);
              });
              this.commentsList = res.data.data;
            }
          })
      },
      isColfun() {
        this.$http.get(URL_CONFIG.UrlConfig.isCol + '?listId=' + this.listId + '&userId=' + this.userInfos.userId)
          .then(res => {
            if (res.data.code === '200') {
              this.isCollect = res.data.data;
            }
          })
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
      celldblclick(data) {
        this.$store.commit('setMusicInfo', data);
        this.$store.commit('setMusicList', this.musicInfo);
        this.$emit('getMessage', '123');
        this.musicInfo.forEach(item => {
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
        if (this.userInfos.userId) {
          this.$http.post(URL_CONFIG.UrlConfig.addComm, {
              commDetails: this.commentText,
              commTargetId: this.listId,
              commType: 1,
              commUserId: this.userInfos.userId
            })
            .then(res => {
              if (res.data.code === '200') {
                this.getComments();
                this.commentText = '';
              }
            })
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning'
          });
        }
      },
      putListInfo() {
        this.$router.push({
          path: '/putList',
          query: {
            listId: this.listId,
            listImg: this.listInfo.listImg
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
      colList() {
        if (this.userInfos.userId) {
          this.$http.post(URL_CONFIG.UrlConfig.addCol + '?listId=' + this.listId + '&userId=' + this.userInfos.userId)
            .then(res => {
              if (res.data.code === '200') {
                this.isCollect = 1;
              }
            })
        } else {
          this.$message({
            message: '请先登录',
            type: 'warning'
          });
        }
      },
      cancelCol() {
        if (this.userInfos.userId) {
          this.$http.delete(URL_CONFIG.UrlConfig.delCol + '/' + this.listId + '/' + this.userInfos.userId)
            .then(res => {
              if (res.data.code === '200') {
                this.isCollect = 0;
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
  .listInfoBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;

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
            font-size: 25px;
            font-weight: 500;
            margin-left: 20px;
          }

          .userBtn {
            width: 30px;
            height: 30px;
            margin-left: 10px;

            img {
              width: 30px;
              height: 30px;
              margin-left: 20px;
              cursor: pointer;
            }
          }
        }

        .userInfo {
          display: flex;
          margin-top: 10px;

          .userInfoImage {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 20px;
          }

          .spanBox {
            font-size: 14px;
            margin-right: 20px;
            line-height: 40px;
          }

          span {
            font-size: 14px;
            margin-right: 20px;
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
      width: 750px;
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
            margin-bottom: 10px;
            color: $themColor;
            line-height: 30px;
            padding: 5px;
            cursor: pointer;

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
            // line-height: 35px;
            gap: 10px;

            .noimage {
              display: flex;
              align-items: center;

              img {
                width: 20px;
                height: 20px;
                margin-right: 5px;
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
  }
</style>
