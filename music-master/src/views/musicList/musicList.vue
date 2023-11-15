<template>
  <div class="songSigerBox">
    <!-- 搜索框 -->
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick1">
        <el-tab-pane label="全部" name="1" />
        <el-tab-pane label="经典" name="2" />
        <el-tab-pane label="流行" name="3" />
        <el-tab-pane label="民谣" name="4" />
        <el-tab-pane label="摇滚" name="5" />
        <el-tab-pane label="安静" name="6" />
      </el-tabs>
    </div>
    <div class="songsigerList">
      <div class="emptyImage" v-if="listsList.length== 0">
        <el-empty :image-size="250"></el-empty>
      </div>
      <div v-else class="sigerItem" v-for='(item, index) in listsList' :key="index" @click="getListInfo(item)">
        <div class="image">
          <img :src="item.listImg">
        </div>
        <div class="sigername">{{item.listTitle}}</div>
      </div>
    </div>
    <div class="pageBox" v-if="listsList.length != 0">
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
        listsList: [],
        pageNum: 1,
        pageSize: 10,
        totalSize: 0,
        activeName: '1'
      }
    },
    created() {
      this.getAllLists();
    },
    methods: {
      handleClick1(tab, event) {
        if (this.activeName === '1') {
          this.getAllLists();
        } else {
          this.getListsByType();
        }
      },
      handleCurrentChange(num) {
        this.pageNum = num;
        if (this.activeName === '1') {
          this.getAllLists();
        } else {
          this.getListsByType();
        }
      },
      getAllLists() {
        this.$http.get(URL_CONFIG.UrlConfig.allLists + '?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize)
          .then(res => {
            if (res.data.code === '200') {
              this.listsList = res.data.data.list;
              this.totalSize = res.data.data.totalSize;
            }
          })
      },
      getListsByType() {
        this.$http.get(URL_CONFIG.UrlConfig.listByType + '?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize +
            '&listType=' + this.activeName)
          .then(res => {
            if (res.data.code === '200') {
              this.listsList = res.data.data.list;
              this.totalSize = res.data.data.totalSize;
            }
          })
      },
      getListInfo(item) {
        this.$router.push({
          path: '/listInfo',
          query: {
            listId: item.listId
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

    .songsigerList {
      display: flex;
      flex-wrap: wrap;
      gap: 35px;

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

      .emptyImage {
        margin: 50px auto;
      }
    }

    .pageBox {
      position: absolute;
      text-align: center;
      bottom: 20px;
      left: 45%;
    }
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
