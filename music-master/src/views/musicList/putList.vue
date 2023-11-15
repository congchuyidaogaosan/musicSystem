<template>
  <div class="putListBox">
    <div class="listTitle">
      编辑歌单
    </div>
    <div class="listBox" v-if="saveSUCCESS">
      <div class="leftBox">
        <div class="infoBox">
          <div class="leftInfo">歌单名：</div>
          <div class="rightInfo">
            <el-input :clearable="clearable" placeholder="请输入歌单名" v-model="listInfo.listTitle"></el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">类型：</div>
          <div class="rightInfo">
            <el-select v-model="listInfo.listType" placeholder="请选择类型">
              <el-option v-for="item in optionType" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo">简介：</div>
          <div class="rightInfo">
            <el-input maxlength="100" show-word-limit type="textarea" :rows="3" placeholder="请输入简介"
              v-model="listInfo.listDetails">
            </el-input>
          </div>
        </div>
        <div class="infoBox">
          <div class="leftInfo"></div>
          <div class="rightInfo">
            <div>
              <el-button type="primary" @click="saveInfo()" plain>保存</el-button>
              <el-button type="info" @click="cancelInfo()" plain>取消</el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="rightBox">
        <div><img :src="headerImage"></div>
        <div>
          <el-upload class="upload-demo" :action="action" :on-success="uploadSuccess">
            <el-button round size="small">修改封面</el-button>
          </el-upload>
        </div>
      </div>
    </div>
    <div v-else>
      <el-result icon="success" title="保存成功" subTitle="请根据提示进行操作">
        <template slot="extra">
          <el-button type="primary" size="medium" @click="cancelInfo()">返回</el-button>
        </template>
      </el-result>
    </div>
  </div>
</template>

<script>
  import URL_CONFIG from '@/assets/js/UrlConfig.js';
  export default {
    data() {
      return {
        listInfo: {},
        clearable: true,
        saveSUCCESS: true,
        action: URL_CONFIG.UrlConfig.upload,
        headerImage: '',
        listId: '',
        optionType: [{
            value: 2,
            label: '经典'
          },
          {
            value: 3,
            label: '流行'
          },
          {
            value: 4,
            label: '民谣'
          },
          {
            value: 5,
            label: '摇滚'
          },
          {
            value: 6,
            label: '安静'
          }
        ]
      }
    },
    created() {
      this.listId = this.$route.query.listId;
      this.getListInfo();
      this.headerImage = this.$route.query.listImg;
    },
    methods: {
      saveInfo() {
        this.$http.put(URL_CONFIG.UrlConfig.putList, {
            listId: this.listId,
            listTitle: this.listInfo.listTitle,
            listDetails: this.listInfo.listDetails,
            listImg: this.headerImage,
            listType: this.listInfo.listType
          })
          .then(res => {
            if (res.data.code === '200') {
              this.saveSUCCESS = false;
            }
          })
      },
      getListInfo() {
        this.$http.get(URL_CONFIG.UrlConfig.listById + '?listId=' + this.listId)
          .then(res => {
            if (res.data.code === '200') {
              if (res.data.data != null) {
                this.listInfo = res.data.data;
              }
            }
          })
      },
      cancelInfo() {
        this.$router.push({
          path: '/listInfo',
          query: {
            listId: this.listInfo.listId
          }
        })
      },
      uploadSuccess(res, file) {
        this.headerImage = res.data;
      },
      styleChange() {}
    }
  }
</script>

<style lang="scss" scoped>
  .putListBox {
    margin: 0 160px;
    background-color: #FFFFFF;
    padding: 20px 50px;
  }

  .listTitle {
    font-size: 20px;
    font-weight: 600;
  }

  .listBox {
    display: flex;
    width: 100%;
    font-size: 15px;

    .leftBox {
      width: 70%;

      .infoBox {
        display: flex;
        width: 100%;
        align-items: center;
        margin-top: 50px;
        line-height: 50px;

        .leftInfo {
          width: 20%;
        }

        .rightInfo {
          width: 70%;

          div {
            text-align: center;
          }
        }
      }
    }

    .rightBox {
      margin-left: 50px;
      width: 20%;
      height: 200px;
      text-align: center;

      div {
        margin-top: 50px;
      }

      img {
        width: 200px;
        height: 200px;
      }
    }
  }
</style>
