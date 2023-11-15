<template>
  <div class="resultBox">
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="歌曲" name="1" />
        <el-tab-pane label="歌单" name="2" />
        <el-tab-pane label="歌手" name="3" />
        <el-tab-pane label="专辑" name="4" />
        <el-tab-pane label="用户" name="5" />
      </el-tabs>
    </div>
    <div>
      <div v-if="activeName === '1'">
        <el-table :data="songInfo" stripe style="width: 100%" @cell-dblclick='celldblclick1'>
          <el-table-column label="序号" width="100">
            <template slot-scope="scope" class="imageBox">
              <span v-if="scope.row.isplay == 1">{{scope.row.index + 1}}</span>
              <img v-else-if="scope.row.isplay == 2" src="../../assets/image/paly01.png">
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120">
            <template slot-scope="scope" class="imageBox">
              <img v-if="scope.row.islove == 1" @click="islovefun(scope.row)" src="../../assets/image/love.png">
              <img v-else-if="scope.row.islove == 2" @click="islovefun(scope.row)" src="../../assets/image/redlove.png">
            </template>
          </el-table-column>
          <el-table-column label="歌名">
            <template slot-scope="scope">
              <el-popover trigger="click" placement="top">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.songName }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="singer.singerName" label="歌手">
          </el-table-column>
          <el-table-column prop="album.albumName" label="专辑">
          </el-table-column>
        </el-table>
      </div>
      <div v-if="activeName === '2'">
        <el-table :data="listInfo" stripe style="width: 100%" @cell-dblclick='celldblclick2'>
          <el-table-column label="序号" width="100">
            <template slot-scope="scope" class="imageBox">
              <span>{{scope.row.index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="封面" width="300">
            <template slot-scope="scope" class="imageBox">
              <img :src="scope.row.listImg">
            </template>
          </el-table-column>
          <el-table-column label="名字">
            <template slot-scope="scope">
              <el-popover trigger="click" placement="top">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.listTitle }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="user.userName" label="创建者">
          </el-table-column>
        </el-table>
      </div>
      <div v-if="activeName === '3'">
        <el-table :data="singerInfo" stripe style="width: 100%" @cell-dblclick='celldblclick3'>
          <el-table-column label="序号" width="100">
            <template slot-scope="scope" class="imageBox">
              <span>{{scope.row.index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="图片" width="300">
            <template slot-scope="scope" class="imageBox">
              <img :src="scope.row.singerPhoto">
            </template>
          </el-table-column>
          <el-table-column label="名字">
            <template slot-scope="scope">
              <el-popover trigger="click" placement="top">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.singerName }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="activeName === '4'">
        <el-table :data="albumInfo" stripe style="width: 100%" @cell-dblclick='celldblclick4'>
          <el-table-column label="序号" width="100">
            <template slot-scope="scope" class="imageBox">
              <span>{{scope.row.index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="图片" width="200">
            <template slot-scope="scope" class="imageBox">
              <img :src="scope.row.albumImg">
            </template>
          </el-table-column>
          <el-table-column label="名字">
            <template slot-scope="scope">
              <el-popover trigger="click" placement="top">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.albumName }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="singer.singerName" label="歌手">
          </el-table-column>
        </el-table>
      </div>
      <div v-if="activeName === '5'">
        <el-table :data="userInfo" stripe style="width: 100%" @cell-dblclick='celldblclick5'>
          <el-table-column label="序号" width="100">
            <template slot-scope="scope" class="imageBox">
              <span>{{scope.row.index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="图片" width="300">
            <template slot-scope="scope" class="imageBox">
              <img :src="scope.row.userPhoto">
            </template>
          </el-table-column>
          <el-table-column label="名字">
            <template slot-scope="scope">
              <el-popover trigger="click" placement="top">
                <div slot="reference" class="name-wrapper">
                  {{ scope.row.userName }}
                </div>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
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
    computed: {
      ...mapState({
        searchStr: state => state.baseInfoStore.searchWord
      })
    },
    data() {
      return {
        searchWord: '',
        activeName: '1',
        songInfo: [],
        listInfo: [],
        singerInfo: [],
        albumInfo: [],
        userInfo: []
      }
    },
    watch: {
      searchStr(newVal, oldVal) {
        console.log(newVal, oldVal);
        this.searchWord = newVal;
        this.activeName = '1';
        this.searchSong();
      }
    },
    created() {
      this.searchWord = this.$route.query.searchWord;
      this.searchSong();
    },
    methods: {
      handleClick(tab, event) {
        if (this.activeName === '1') {
          this.searchSong();
        } else if (this.activeName === '2') {
          this.searchList();
        } else if (this.activeName === '3') {
          this.searchSinger();
        } else if (this.activeName === '4') {
          this.searchAlbum();
        } else {
          this.searchUser();
        }
      },
      celldblclick1(data) {
        this.$store.commit('setMusicInfo', data);
        this.$store.commit('setMusicList', this.songInfo);
        this.$emit('getMessage', '123');
        this.songInfo.forEach(item => {
          this.$set(item, 'isplay', 1);
        });
        data.isplay = 2;
      },
      celldblclick2(data) {
        this.$router.push({
          path: '/listInfo',
          query: {
            listId: data.listId
          }
        })
      },
      celldblclick3(data) {
        this.$router.push({
          path: '/singerInfo',
          query: {
            singerId: data.singerId,
            singerPhoto: data.singerPhoto,
            singerName: data.singerName
          }
        })
      },
      celldblclick4(data) {
        this.$router.push({
          path: '/albumInfo',
          query: {
            albumId: data.albumId
          }
        })
      },
      celldblclick5(data) {
        this.$router.push({
          path: '/userInfo',
          query: {
            userId: data.userId
          }
        })
      },
      searchSong() {
        console.log(1212);
        this.$http.get(URL_CONFIG.UrlConfig.songByName + '?songName=' + this.searchWord)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'islove', 1);
                this.$set(item, 'index', i);
                this.$set(item, 'isplay', 1);
              });
              this.songInfo = res.data.data;
            }
          })
      },
      searchList() {
        this.$http.get(URL_CONFIG.UrlConfig.listByName + '?listName=' + this.searchWord)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'index', i);
              });
              this.listInfo = res.data.data;
            }
          })
      },
      searchSinger() {
        this.$http.get(URL_CONFIG.UrlConfig.singerByName + '?singerName=' + this.searchWord)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'index', i);
              });
              this.singerInfo = res.data.data;
            }
          })
      },
      searchAlbum() {
        this.$http.get(URL_CONFIG.UrlConfig.albumByName + '?albumName=' + this.searchWord)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'index', i);
              });
              this.albumInfo = res.data.data;
            }
          })
      },
      searchUser() {
        this.$http.get(URL_CONFIG.UrlConfig.userByName + '?userName=' + this.searchWord)
          .then(res => {
            if (res.data.code === '200') {
              res.data.data.forEach((item, i) => {
                this.$set(item, 'index', i);
              });
              this.userInfo = res.data.data;
            }
          })
      }
    }
  }
</script>

<style scoped>
  .resultBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;
  }

  .el-table__row {
    td {
      .cell {
        img {
          width: 30px;
          height: 30px;
          margin-right: 10px;
          cursor: pointer;
        }
      }
    }
  }
</style>
