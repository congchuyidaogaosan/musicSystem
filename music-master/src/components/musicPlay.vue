<template>
  <div class="footerCom">
    <audio preload="auto" style="display:none" ref="audio" autoplay="autoplay" :src="musicInfo.songFilePath" controls
      @volumechange="volumechange" @loadedmetadata='onloadedmetadata' @timeupdate="timeUpdate"
      @durationchange='durationchange'></audio>
    <div class="block">
      <div class="controlBox">
        <img src="../assets/image/shangyishou.png" @click="qianyishouFun">
        <img v-if=" showImageFlay == 2" @click="pausefun" class="controlImage" src="../assets/image/audioplaue.png">
        <img v-else-if=" showImageFlay == 1" @click="playfun" class="controlImage" src="../assets/image/audioplay.png">
        <img src="../assets/image/last.png" @click="houyishouFun">
      </div>
      <div class="audioIndo">
        <img @click="toPlaySong()" v-if="musicInfo.album != null" :src="musicInfo.album.albumImg">
        <img v-else src="../assets/image/meun05.png">
        <div class='slider_Info'>
          <div class="musicName">
            <span>{{musicInfo.songName}}</span>
            <span class="musicor_Info" v-if="musicInfo.singer != null">{{musicInfo.singer.singerName}}</span>
          </div>
          <div class="time_slwer">
            <el-slider class="sliderBox" v-model="sliderTime" :format-tooltip="formatTooltip"
              @change="changeCurrentTime"></el-slider>
            <div class="demonstration">{{currentTime}} / {{totalTime}}</div>
          </div>
        </div>
        <div class="controlBox">
          <div class="sliderblock">
            <el-slider v-model="vocalModel" vertical height="100px" v-show='showslider' @input="inputchange"
              :show-tooltip='false'>
            </el-slider>
          </div>
          <!-- @mousemove="mousemovefun" @mouseout="mouseoutfun" -->
          <img class="vocalNumber" @mousemove="mousemovefun" @mouseout="mouseoutfun"
            src="../assets/image/vocalNumber.png">
        </div>
        <div class="controlBox" @click="collection()">
          <img v-if="musicInfo.islove === 2" class="vocalNumber" src="../assets/image/icon_like.png">
          <img v-else-if="musicInfo.islove === 1" class="vocalNumber" src="../assets/image/nolike.png">
        </div>
        <div class="controlBox" v-for="(item,index) in iconList" :key='index'>
          <img class="vocalNumber" :src="item.icon_path" v-if="iconselecr === index" @click="changeIcon(item,index)">
        </div>
        <div class="controlBox" @click="changeDrawer()">
          <img class="vocalNumber" src="../assets/image/musiclisticon.png">
        </div>
      </div>
    </div>
    <div>
      <el-drawer :modal='modaldraw' title="当前播放" :visible.sync="drawer" :direction="direction"
        :before-close="handleClose">
        <span>总{{musicListInfo.length}}首</span>
        <el-table :data="musicListInfo" stripe style="width: 100%" @cell-dblclick='celldblclick'>
          <el-table-column label="序号" width="80">
            <template slot-scope="scope" class="imageBox">
              <span v-if="scope.row.isplay == 1">{{scope.row.index + 1}}</span>
              <img class="playImg" v-else-if="scope.row.isplay == 2" src="../assets/image/paly01.png">
            </template>
          </el-table-column>
          <el-table-column prop="songName" label="歌名">
          </el-table-column>
          <el-table-column prop="singer.singerName" label="歌手">
          </el-table-column>
          <el-table-column prop="address" label="时长">
          </el-table-column>
        </el-table>
      </el-drawer>
    </div>
  </div>
</template>
<script>
  import icon01 from '../assets/image/list01.png';
  import icon02 from '../assets/image/list02.png';
  import icon03 from '../assets/image/list03.png';
  import icon04 from '../assets/image/list04.png';
  import icon05 from '../assets/image/list05.png';
  import {
    mapState
  } from 'vuex';
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    components: {},
    name: 'MusicPlay',
    props: {
      msg: String
    },
    computed: {
      ...mapState({
        musicList: state => state.baseInfoStore.musicList,
        musicInfostr: state => state.baseInfoStore.musicInfo,
        userInfos: state => state.baseInfoStore.userInfo
      })
    },
    watch: {
      musicInfostr: {
        handler(newName) {
          this.musicInfo = newName;
          this.musicIndex = this.musicInfo.index;
        },
        immediate: true
      }
    },
    created() {
      this.musicListInfo = this.musicList;
    },
    data() {
      return {
        sliderTime: 0,
        showImageFlay: 1,
        currentTime: '00:00',
        totalTime: '00:00',
        audio: {},
        isLoveFlag: false,
        vocalModel: 38,
        showslider: true,
        musicListInfo: {},
        musicInfo: {},
        iconList: [{
            icon_id: 1,
            icon_path: icon01
          },
          {
            icon_id: 2,
            icon_path: icon02
          },
          {
            icon_id: 3,
            icon_path: icon03
          },
          {
            icon_id: 4,
            icon_path: icon04
          },
          {
            icon_id: 5,
            icon_path: icon05
          }
        ],
        iconselecr: 0,
        musicIndex: 0,
        drawer: false,
        direction: 'rtl',
        modaldraw: false
      }
    },
    methods: {
      qianyishouFun() {
        this.sliderTime = 0;
        if (this.musicIndex > 0) {
          this.musicInfo = this.musicList[this.musicIndex - 1];
          this.musicIndex--;
        } else if (this.musicIndex == 0) {
          this.musicInfo = this.musicList[this.musicList.length - 1];
          this.musicIndex = this.musicList.length - 1;
        }
        this.playfun();
        this.musicListInfo.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        this.musicInfo.isplay = 2;
        this.$store.commit('setMusicInfo', this.musicInfo);
      },
      houyishouFun() {
        this.sliderTime = 0;
        if (this.musicIndex < this.musicList.length - 1) {
          this.musicInfo = this.musicList[this.musicIndex + 1];
          this.musicIndex++;
        } else if (this.musicIndex == this.musicList.length - 1) {
          this.musicIndex = 0;
          this.musicInfo = this.musicList[0];
        }
        this.playfun();
        this.musicListInfo.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        this.$store.commit('setMusicInfo', this.musicInfo);
        this.musicInfo.isplay = 2;
      },
      formatTooltip(val) {
        var str = this.audio.maxTime / 100 * val;
        var secStr = parseInt(str / 60) < 10 ? '0' + parseInt(str / 60) : parseInt(str / 60);
        var decStr = parseInt(str % 60) < 10 ? '0' + parseInt(str % 60) : parseInt(str % 60);
        return secStr + ':' + decStr;
      },
      durationchange(res) {
        this.sliderTime = 0;
        var str = res.path[0].duration;
        this.currentTime = '00:00'
        var secStr = parseInt(str / 60) < 10 ? '0' + parseInt(str / 60) : parseInt(str / 60);
        var decStr = parseInt(str % 60) < 10 ? '0' + parseInt(str % 60) : parseInt(str % 60);
        this.totalTime = secStr + ':' + decStr;
        console.log(this.totalTime);
      },
      onloadedmetadata(res) {
        this.audio.waiting = false;
        this.audio.maxTime = parseInt(res.target.duration);
        this.showslider = false;
        this.playfun()
      },
      // 时间更新  用于进度条的刷新
      timeUpdate(data) {
        this.sliderTime = parseInt(data.target.currentTime / data.target.duration * 100);
        this.changeTimeStr(data.target.currentTime / data.target.duration * 100);
        if (this.sliderTime === 100) {
          this.pausefun();
          this.houyishouFun();
        }
        this.$store.commit('setCurrentTime', data.target.currentTime);
      },
      volumechange(data) {
        this.vocalModel = data.target.volume * 100;
      },
      mousemovefun() {
        this.showslider = true;
      },
      mouseoutfun() {
        setTimeout(() => {
          this.showslider = false;
        }, 1000)
      },
      changeVocal() {
        this.showslider = !this.showslider;
      },
      inputchange(val) {
        this.showslider = true;
        this.$refs.audio.volume = val / 100;
      },
      playfun() {
        this.showImageFlay = 2;
        this.$store.commit('setPlayState', this.showImageFlay);
        this.$refs.audio.play();
      },
      pausefun() {
        this.showImageFlay = 1;
        this.$store.commit('setPlayState', this.showImageFlay);
        this.$refs.audio.pause();
      },
      changeTimeStr(index) {
        var str = this.audio.maxTime / 100 * index;
        var secStr = parseInt(str / 60) < 10 ? '0' + parseInt(str / 60) : parseInt(str / 60);
        var decStr = parseInt(str % 60) < 10 ? '0' + parseInt(str % 60) : parseInt(str % 60);
        this.currentTime = secStr + ':' + decStr;
      },
      changeCurrentTime(index) {
        this.$refs.audio.currentTime = parseInt(this.audio.maxTime / 100 * index);
        this.sliderTime = index;
      },
      collection() {
        if (this.musicInfo.islove == 1) {
          this.$http.post(URL_CONFIG.UrlConfig.addSongOfUser + '?userId=' + this.userInfos.userId + '&songId=' + this
              .musicInfo.songId)
            .then(res => {
              if (res.data.code === '200') {
                this.$store.commit('setMyLikeInfo', res.data.data);
                this.musicInfo.islove = 2;
              }
            })
        } else if (this.musicInfo.islove == 2) {
          this.$http.delete(URL_CONFIG.UrlConfig.delSongOfUser + '/' + this.userInfos.userId + '/' + this.musicInfo
              .songId)
            .then(res => {
              if (res.data.code === '200') {
                this.$store.commit('setMyLikeInfo', res.data.data);
                this.musicInfo.islove = 1;
              }
            })
        }
      },
      changeIcon(item, index) {
        if (index < 5) {
          this.iconselecr = index + 1;
        }
        if (index === 4) {
          this.iconselecr = 1;
        }
      },
      handleClose() {
        this.drawer = false;
      },
      changeDrawer() {
        this.drawer = !this.drawer;
        if (this.drawer) {
          this.musicListInfo.forEach(item => {
            item.isplay = 1;
            if (this.musicInfo.songId === item.songId) {
              item.isplay = 2;
            }
          })
        }
      },
      celldblclick(data) {
        this.$store.commit('setMusicInfo', data);
        this.$emit('getMessage', '123');
        this.musicListInfo.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        data.isplay = 2;
      },
      toPlaySong() {
        this.$router.push({
          path: '/playSong',
          query: {
            searchWord: this.searchWord
          }
        });
      }
    }
  }
</script>

<style scoped lang="scss">
  .footerCom {
    position: fixed;
    bottom: 0px;
    left: 0px;
    right: 0px;
    height: 60px;
    padding: 0px 150px;
    background-color: #fff;
    box-shadow: 2px 2px 10px #909090;
    display: flex;
    justify-content: space-between;

    .block {
      display: flex;
      color: #000;
      gap: 20px;
      line-height: 70px;

      .controlBox {
        display: flex;
        align-items: center;
        position: relative;

        img {
          width: 20px;
          height: 20px;
          cursor: pointer;
          margin-right: 30px;
        }

        .controlImage {
          width: 25px;
          height: 25px;
        }

        .vocalNumber {
          width: 25px;
          height: 25px;
          margin-right: 30px;
        }

        .sliderblock {
          position: absolute;
          bottom: 40px;
          left: -8px;
        }
      }

      .audioIndo {
        display: flex;
        align-items: center;
        justify-content: space-between;

        img {
          width: 40px;
          height: 40px;
          margin-right: 20px;
          cursor: pointer;
        }

        .slider_Info {
          color: #000;
          margin-right: 20px;

          .musicName {
            line-height: 10px;
            color: #000;
            font-size: 14px;
            margin-top: 10px;

            .musicor_Info {
              font-size: 14px;
              margin-left: 10px;
              color: #909399;
            }
          }

          .time_slwer {
            display: flex;
            color: #000;

            .demonstration {
              width: 144px;
              line-height: 37px;
              font-size: 14px;
            }

            .sliderBox {
              // display: flex;
              // align-items: center;
              width: 500px;
              margin-right: 20px;
            }
          }
        }
      }
    }
  }

  .el-drawer__wrapper {
    margin-top: 61px;
    margin-bottom: 61px;

    .el-drawer__container,
    .el-drawer__open {
      border: 1px solid red;
    }
  }

  .playImg {
    width: 20px;
    height: 20px;
  }
</style>
