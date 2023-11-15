<template>
  <div id="parent" v-infinite-scroll class="song-lyric">
    <transition-group name="lyric-fade">
      <!--有歌词-->
      <ul id="child" class="has-lyric" v-if="lyricsObjArr.length" key="has-lyric">
        <li v-for="(item, index) in lyricsObjArr" v-bind:key="index">
          {{item.lyric}}
        </li>
      </ul>
      <!--没歌词-->
      <div v-else class="no-lyric" key="no-lyric">
        <span>暂无歌词</span>
      </div>
    </transition-group>
  </div>
</template>

<script>
  import {
    mapState
  } from 'vuex';
  export default {
    name: 'Lyric',
    data() {
      return {
        musicInfo: {},
        lyr: '',
        lyricsObjArr: [],
        lyricIndex: 0
      }
    },
    computed: {
      ...mapState({
        musicInfostr: state => state.baseInfoStore.musicInfo,
        currentTimes: state => state.baseInfoStore.currentTime
      })
    },
    watch: {
      musicInfostr() {
        this.lyricsObjArr = [];
        this.lyr = this.musicInfostr.songLyc;
        this.loadLyc()
      },
      currentTimes() {
        if (this.lyricsObjArr.length !== 0) {
          for (let i = 0; i < this.lyricsObjArr.length; i++) {
            if (this.currentTimes >= this.lyricsObjArr[i].time) {
              for (let j = 0; j < this.lyricsObjArr.length; j++) {
                document.getElementById('parent').scrollTop = 30;
                document.querySelectorAll('.has-lyric li')[j].style.color = '#000'
                document.querySelectorAll('.has-lyric li')[j].style.fontSize = '15px'
              }
              if (i >= 0) {
                document.getElementById('parent').scrollTop = (document.getElementById('child').scrollHeight / this
                  .lyricsObjArr.length) * (i - 1);
                document.querySelectorAll('.has-lyric li')[i].style.color = '#95d2f6'
                document.querySelectorAll('.has-lyric li')[i].style.fontSize = '25px'
              }
            }
          }
        }
      }
    },
    created() {
      this.lyr = this.musicInfostr.songLyc;
      this.loadLyc();
    },
    methods: {
      loadLyc() {
        const regNewLine = /\n/;
        const regTime = /\[\d{2}:\d{2}.\d{2,3}\]/
        const lineArr = (this.lyr || '').split(regNewLine) // 每行歌词的数组
        lineArr.forEach(item => {
          if (item === '') return
          const obj = {}
          const time = item.match(regTime)

          obj.lyric = item.split(']')[1].trim() === '' ? '' : item.split(']')[1].trim()
          obj.time = time ? this.formatLyricTime(time[0].slice(1, time[0].length - 1)) : 0
          obj.uid = Math.random().toString().slice(-6)
          if (obj.lyric === '') {
            console.log('这一行没有歌词')
          } else {
            this.lyricsObjArr.push(obj)
          }
        });
      },
      formatLyricTime(time) { // 格式化歌词的时间 转换成 sss:ms
        const regMin = /.*:/
        const regSec = /:.*\./
        const regMs = /\./

        const min = parseInt(time.match(regMin)[0].slice(0, 2))
        let sec = parseInt(time.match(regSec)[0].slice(1, 3))
        const ms = time.slice(time.match(regMs).index + 1, time.match(regMs).index + 3)
        if (min !== 0) {
          sec += min * 60
        }
        return Number(sec + '.' + ms)
      }
    }
  }
</script>

<style lang="scss">
  .song-lyric {
    margin: auto;
    width: 550px;
    border-radius: 12px;
    padding: 0 20px 50px 20px;
    height: 500px;
    overflow: auto;

    .lyric-title {
      text-align: center;
      height: 60px;
      line-height: 60px;
      border-bottom: 2px solid #ccc;
    }

    .has-lyric {
      font-size: 18px;
      padding: 30px 0;
      width: 100%;
      min-height: 170px;
      text-align: center;

      li {
        width: 100%;
        height: 40px;
        line-height: 40px;
        list-style: none;
      }
    }

    .no-lyric {
      margin: 100px 0;
      width: 100%;
      text-align: center;

      span {
        font-size: 18px;
        text-align: center;
      }
    }
  }

  .lyric-fade-enter,
  .lyric-fade-leave-to {
    transform: translateX(30px);
    opacity: 0;
  }

  .lyric-fade-enter-active,
  .lyric-fade-leave-active {
    transition: all 0.3s ease;
  }
</style>
