<template>
  <div class="songSigerBox">
    <!-- 搜索框 -->
    <div class="ListTop">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="全部歌手" name="1" />
        <el-tab-pane label="华语男歌手" name="2" />
        <el-tab-pane label="华语女歌手" name="3" />
        <el-tab-pane label="华语组合" name="4" />
        <el-tab-pane label="外语男歌手" name="5" />
        <el-tab-pane label="外语女歌手" name="6" />
        <el-tab-pane label="外语组合" name="7" />
      </el-tabs>
    </div>
    <div class="songsingerList">
      <div class="emptyImage" v-if="singerList.length== 0">
        <el-empty :image-size="200"></el-empty>
      </div>
      <div class="sigerItem" v-for='(item, index) in singerList' :key="index" @click="getSingerInfo(item)">
        <div class="image">
          <img :src="item.singerPhoto">
        </div>
        <div class="sigername">{{item.singerName}}</div>
      </div>
    </div>
    <div class="pageBox">
      <el-pagination :page-size="pageSize" background :pager-count="7" :current-page='pageNum' layout="total,prev, pager, next"
        @current-change="handleCurrentChange" :total="totalSize">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    data() {
      return {
        singerList: [],
        pageNum: 1,
        pageSize: 10,
        totalSize: 0,
        activeName: '1'
      }
    },
    created() {
      this.getAllSinger();
    },
    methods: {
      handleClick(tab, event) {
        if (this.activeName === '1') {
          this.getAllSinger();
        } else {
          this.getSingerByType();
        }
      },
      handleCurrentChange(num) {
        this.pageNum = num;
        if (this.activeName === '1') {
          this.getAllSinger();
        } else {
          this.getSingerByType();
        }
      },
      getAllSinger() {
        // allSinger
        this.$http.get(URL_CONFIG.UrlConfig.allSinger + '?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize)
          .then(res => {
            if (res.data.code === '200') {
              this.singerList = res.data.data.list;
              this.totalSize = res.data.data.totalSize;
            }
          })
      },
      getSingerByType() {
        // allSinger
        this.$http.get(URL_CONFIG.UrlConfig.singerByType + '?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize +
            '&singerType=' + this.activeName)
          .then(res => {
            if (res.data.code === '200') {
              this.singerList = res.data.data.list;
              this.totalSize = res.data.data.totalSize;
            }
          })
      },
      getSingerInfo(item) {
        this.$router.push({
          path: '/singerInfo',
          query: {
            singerId: item.singerId,
            singerDetails: item.singerDetails,
            singerPhoto: item.singerPhoto,
            singerName: item.singerName
          }
        })
      }
    }
  }
</script>
<style lang='scss' scoped>
  .songSigerBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    height: 600px;
    padding: 5px 70px;
    position: relative;

    .songsingerList {
      display: flex;
      flex-wrap: wrap;
      gap: 30px;

      .sigerItem {
        width: 180px;
        box-shadow: 2px 2px 10px #909090;
        cursor: pointer;

        .image {
          width: 180px;
          height: 180px;

          img {
            width: 100%;
            height: 100%;
          }
        }

        .sigername {
          widows: 180px;
          line-height: 50px;
          text-align: center;
        }
      }
    }

    .pageBox {
      position: absolute;
      text-align: center;
      bottom: 20px;
      left: 45%;
    }
  }

  .emptyImage {
    margin: 50px auto;
  }

  .ListTop {
    font-size: 20px;
    font-weight: 600px;
  }

  .queryInput {
    margin-left: 80px;
  }

  /deep/.el-tabs__item {
    font-size: 18px !important;
    font-weight: 600;
    width: 120px;
    text-align: center;
  }

  /deep/.el-tabs__nav-wrap::after {
    width: 0% !important;
  }
</style>
