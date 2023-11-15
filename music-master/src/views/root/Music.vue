<template>
    <div>
        <div class="top">
            <div class="leftTiyle" id="flTitle">音乐管理</div>
            <div class="add">
                <input type="button" class="but" @click="add" value="新增" />
            </div>
        </div>
        <div class="content">
            <table v-show="!this.fom">
                <thead>
                    <tr class="head">
                        <th>序号</th>
                        <th>歌曲</th>
                        <th>歌手</th>
                        <th>地址</th>
                        <th>专辑</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody label-width="120px">
                    <tr v-for="song in list" :key="song.id">
                        <td>{{ song.songId }}</td>
                        <td>{{ song.songName }}</td>
                        <td>{{ song.singer.singerName }}</td>
                        <td>{{ song.songFilePath }}</td>
                        <td>{{ song.album.albumName }}</td>
                        <td style="width: 100px">
                            <input type="button" class="but" @click="del(song.songId)" value="删除" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="smart-green" v-show="this.fom">
                <h1>添加音乐</h1>
                <label>
                    <span>歌曲名称 :</span>
                    <input type="text" v-model="songName" placeholder="请输入歌曲名称" />
                </label>

                <label>
                    <span>歌手名称 :</span>
                    <input type="text" v-model="singerName" placeholder="请输入歌手名称" />
                </label>

                <label>
                    <span>请上传音乐 :</span> <br />
                    <el-upload action="#" :on-preview="handlePreview" accept=".mp3" :http-request="httpRequest">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传mp3文件，且不超过15MB</div>
                    </el-upload>
                    <!-- http://localhost:8081/song/upload -->
                </label>
                <label>
                    <span>添加到专辑 :</span>
                    <input type="text" v-model="albumName" placeholder="请输入专辑名称" />
                </label>
                <label>
                    <input type="button" class="button" value="提交" @click="onUpload" />
                </label>
            </div>
        </div>
    </div>
</template>
<script>
import URL_CONFIG from '@/assets/js/UrlConfig.js';
export default {
    data() {
        return {
            list: [],
            fom: false,
            songName: '',
            singerName: '',
            albumName: '',
            file: []
        };
    },
    mounted() {
        this.getList();
    },
    methods: {
        getList() {
            this.$http.get(URL_CONFIG.UrlConfig.allSong).then((res) => {
                console.log(res);
                if (res.data.code === '200') {
                    this.list = res.data.data.list;
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        del(id) {
            this.$http.delete(URL_CONFIG.UrlConfig.delSong + '/' + id).then((res) => {
                console.log(res);
                if (res.data.code === '200') {
                    alert('删除成功!');
                    this.list = [];
                    this.getList();
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        add() {
            this.fom = true;
        },
        httpRequest(option) {
            this.file.push(option)
        },
        onUpload(file) {
            if (this.songName == null) {
                return this.$message.error('歌曲名字不能为空!');
            }
            if (this.singerName == null) {
                return this.$message.error('歌手名字不能为空!');
            }
            if (this.albumName == null) {
                return this.$message.error('专辑名字不能为空!');
            }
            let formData = new FormData()
            formData.append('file', this.file[0].file)
            formData.append('songName', this.songName)
            formData.append('singerName', this.singerName)
            formData.append('albumName', this.albumName)
            this.$http.post(URL_CONFIG.UrlConfig.songUpload, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then((res) => {
                if (res.code == 200) {
                    this.$message.success('上传成功！')
                } else {
                    this.$message.error(res.msg)
                }
            }).catch((e) => {
                this.$message.error(e.msg)
            })
            this.list = []
            this.getList()
            this.fom = false
        },
        handlePreview(file) {
            window.open(file.response.url)
        }
    }
};
</script>

<style lang="scss" scoped>
tr,
td {
    border: 1px solid #999;
    text-align: center;
    padding: 10px 0;
}

div {
    .top {
        position: relative;
        width: 80%;
        display: flex;
        height: 100px;
        align-items: center;
        font-size: 25px;
        padding-left: 30px;
        font-weight: 600;
        color: #545454;
        float: left;
        justify-content: space-between;

        .add {
            width: 23%;
            float: right;
            margin-right: -22%;

            .but {
                background-color: #545a6c;
                border: none;
                line-height: 30px;
                color: #fff;
                width: 60%;
                margin-bottom: 5px;
            }
        }
    }

    .content {
        width: 100%;

        table {
            width: 100%;
            border-collapse: collapse;

            thead {
                tr {
                    background-color: #545a6c;
                    color: #fff;

                    th {
                        border: 1px solid #999;
                        text-align: center;
                        padding: 10px 0;
                    }
                }
            }

            tbody {
                tr {
                    &:nth-child(odd) {
                        background-color: #eee;
                    }

                    &:hover {
                        background-color: #ccc;
                    }

                    td {
                        text-align: center;

                        &:first-child {
                            color: #3d598a;
                        }
                    }

                    td {
                        .but {
                            background-color: #545a6c;
                            border: none;
                            line-height: 30px;
                            color: #fff;
                            width: 60%;
                            margin-bottom: 5px;
                        }
                    }
                }
            }
        }

        .smart-green {
            position: absolute;
            margin-left: auto;
            margin-right: auto;
            width: 70%;
            top: 10%;
            background: #F8F8F8;
            padding: 30px 30px 20px 30px;
            font: 12px Arial, Helvetica, sans-serif;
            color: #666;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;

            h1 {
                font: 24px "Trebuchet MS", Arial, Helvetica, sans-serif;
                padding: 20px 0px 20px 40px;
                display: block;
                margin: -30px -30px 10px -30px;
                color: #FFF;
                background: #545a6c;
                text-shadow: 1px 1px 1px #949494;
                border-radius: 5px 5px 0px 0px;
                -webkit-border-radius: 5px 5px 0px 0px;
                -moz-border-radius: 5px 5px 0px 0px;
                border-bottom: 1px solid #545a6c;
            }

            label {
                display: block;
                margin: 0px 0px 5px;

                span {
                    float: left;
                    margin-top: 10px;
                    color: #5E5E5E;
                }

                input {
                    color: #555;
                    height: 30px;
                    line-height: 15px;
                    width: 100%;
                    padding: 0px 0px 0px 10px;
                    margin-top: 2px;
                    border: 1px solid #E5E5E5;
                    background: #FBFBFB;
                    outline: 0;
                    -webkit-box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
                    box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
                    font: normal 14px/14px Arial, Helvetica, sans-serif;
                }

                select {
                    color: #555;
                    height: 30px;
                    line-height: 15px;
                    width: 100%;
                    padding: 0px 0px 0px 10px;
                    margin-top: 2px;
                    border: 1px solid #E5E5E5;
                    background: #FBFBFB;
                    outline: 0;
                    -webkit-box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
                    box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
                    font: normal 14px/14px Arial, Helvetica, sans-serif;
                }

                .button {
                    background-color: #545a6c;
                    border-radius: 5px;
                    -webkit-border-radius: 5px;
                    -moz-border-border-radius: 5px;
                    border: none;
                    padding: 10px 25px 10px 25px;
                    color: #FFF;
                    text-shadow: 1px 1px 1px #949494;

                    &:hover {
                        background-color: #545a6c;
                    }
                }
            }
        }
    }
}
</style>
