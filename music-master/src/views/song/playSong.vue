<template>
  <div class="playSong">
    <div class="titleBox">
      <div style="font-size: 25px;font-weight: 700;">{{musicInfo.songName}}</div>
      <div style="font-size: 15px;">{{musicInfo.singer.singerName}}</div>
    </div>
    <div class="mainBox">
      <div class="headerBox">
        <div class="imgBox">
          <div class="playZhi"><img :class="[addRotate?'go':'aa']" src="../../assets/image/play.png" /></div>
          <div class="playBox">
            <div class="playImg1"><img id="playimg" :src="musicInfo.album.albumImg" /></div>
          </div>
        </div>
        <div class="lycBox">
          <Lyric></Lyric>
        </div>
      </div>
    </div>
    <div class="commentBox">
      <div class="addText_box">
        <div class="text_total">全部评论({{commentsList.length}})</div>
        <el-input type="textarea" placeholder="请输入内容" maxlength="200" show-word-limit v-model="commentText">
        </el-input>
        <div class="addCommentBtn" @click="addComment()">提交评论</div>
      </div>
      <div class="text_box">
        <div class="Text_content" v-for="(item, index) in  commentsList" :key="index">
          <div class="leftBox"><img :src="item.user.userPhoto" /></div>
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
  import Lyric from '../../components/Lyric.vue'
  export default {
    components: {
      Lyric
    },
    data() {
      return {
        musicInfo: {},
        commentsList: [],
        commentText: '',
        addRotate: false
      }
    },
    computed: {
      ...mapState({
        userInfos: state => state.baseInfoStore.userInfo,
        musicInfostr: state => state.baseInfoStore.musicInfo,
        playStates: state => state.baseInfoStore.playState
      })
    },
    watch: {
      musicInfostr() {
        this.musicInfo = this.musicInfostr;
        this.getComments();
      },
      playStates() {
        if (this.playStates == 1) {
          document.getElementById('playimg').style.webkitAnimationPlayState = 'paused';
          this.addRotate = true;
        } else {
          document.getElementById('playimg').style.webkitAnimationPlayState = 'running';
          this.addRotate = false;
        }
      }
    },
    created() {
      this.musicInfo = this.musicInfostr;
      this.getComments();
    },
    methods: {
      getComments() {
        this.$http.get(URL_CONFIG.UrlConfig.allComments + '?commTargetId=' + this.musicInfo.songId + '&commType=3')
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach(item => {
                this.$set(item, 'nozanFlag', false);
              });
              this.commentsList = res.data.data;
            }
          })
      },
      changeTiime(str) {
        if (str != undefined) {
          return str.substr(0, 10);
        }
      },
      addComment() {
        this.$http.post(URL_CONFIG.UrlConfig.addComm, {
            commDetails: this.commentText,
            commTargetId: this.musicInfo.songId,
            commType: 3,
            commUserId: this.userInfos.userId
          })
          .then(res => {
            if (res.data.code === '200') {
              this.getComments();
              this.commentText = '';
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
      }
    }
  }
</script>

<style lang="scss" scoped>
  .playSong {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;

    .titleBox {
      height: 80px;
      text-align: center;

      div {
        margin: 10px 0;
      }
    }

    .mainBox {
      padding: 15px;

      .headerBox {
        display: flex;
        justify-content: space-between;
        margin-left: 50px;

        .imgBox {
          position: relative;
        }

        .playZhi {
          left: 100px;
          text-align: center;
          position: absolute;
          z-index: 1;

          img {
            width: 100px;
            height: 150px;
            z-index: 1;
          }

          .aa {
            transform-origin: left 0px;
            transition: all 0.5s;
          }

          .go {
            transform: rotate(-30deg);
            transform-origin: left 0px;
            transition: all 0.5s;
          }
        }

        .lycBox {
          width: 50%;
        }
      }

      .playBox {
        border: 1px solid #ccc;
        width: 300px;
        height: 300px;
        background-color: #CCC;
        border-radius: 300px;
        position: relative;
        position: absolute;
        top: 100px;

        .playImg1 {
          width: 270px;
          height: 270px;
          background-color: #000000;
          border-radius: 270px;
          position: absolute;
          top: 15px;
          left: 15px;

        }

        img {
          z-index: -1;
          margin-top: 35px;
          margin-left: 35px;
          width: 200px;
          height: 200px;
          border-radius: 200px;
          animation: loading 30s linear infinite forwards;
        }

        .active {
          animation-play-state: running;
        }

        .paused {
          animation-play-state: paused;
        }
      }
    }

    @keyframes loading {
      0% {
        transform: rotate(0deg)
      }

      100% {
        transform: rotate(360deg)
      }
    }

    .commentBox {
      margin: 20px 0px;

      .text_box {
        width: 100%;

        .text_total {
          font-size: 18px;
          font-weight: 500;
          line-height: 50px;
        }

        .Text_content {
          display: flex;
          width: 100%;
          border-bottom: 1px solid #e6e6e6;
          padding: 20px 20px 20px 20px;

          img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 20px;
          }

          .righrtBox {
            width: 100%;

            .userName_box {
              border-radius: 10px;
              background-color: #e6e6e6;
              font-size: 14px;
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
    }
  }
</style>
