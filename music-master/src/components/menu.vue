<template>
  <div>
    <HeaderTop :partInfo="infos" :key="headertimer+ '-only'"></HeaderTop>
    <div class='centerBox'>
      <router-view @getMessage=" changeTrmer">
      </router-view>
    </div>
    <div class="bottomBox">
      <span>关于 | 帮助 | 条款 | 反馈</span><br>
      <span>tianleiyu © 2023 emo-01</span>
    </div>
    <MusicPlay :key='timer'></MusicPlay>
  </div>
</template>
<script>
  import HeaderTop from '../components/HeaderTop.vue'
  import MusicPlay from '../components/musicPlay.vue'
  export default {
    components: {
      HeaderTop,
      MusicPlay
    },
    data() {
      return {
        timer: '',
        headertimer: '',
        infos: 0
      }
    },
    methods: {
      changeTrmer(val) {
        if (val === '123') {
          this.timer = new Date().getTime();
        } else if (val === 'user') {
          this.headertimer = new Date().getTime();
        } else {
          this.infos = val;
        }
      }
    }
  }
</script>
<style lang='scss' scoped>
  .centerBox {
    background-color: #e6ecf0;
    margin-top: 60px;
  }

  .bottomBox {
    background-color: #e6ecf0;
    height: 160px;
    text-align: center;

    span {
      margin-top: 10px;
      font-weight: 400;
      font-size: 15px;
    }
  }
</style>
