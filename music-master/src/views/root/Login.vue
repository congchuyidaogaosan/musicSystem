<template>
    <div class="back">
        <div class="main">
            <h2>欢迎访问管理系统</h2>
            <div class="form">
                用户名: <input type="text" name="username" id="username" v-model="username" /> <br />
                密&nbsp;&nbsp;&nbsp;码:
                <input type="password" id="userpwd" name="password" v-model="password" /><br />
                <button @click="login">登录</button><br />
            </div>
        </div>
    </div>
</template>
<script>
import URL_CONFIG from '@/assets/js/UrlConfig.js';

export default {
    data() {
        return {
            username: '',
            password: ''

        }
    },
    methods: {
        login() {
            if (this.username == '') {
                this.$message({
                    message: '请填写账号',
                    type: 'warning'
                });
                return false;
            }
            if (this.password == '') {
                this.$message({
                    message: '请填写密码',
                    type: 'warning'
                });
                return false;
            }
            this.$http.get(URL_CONFIG.UrlConfig.rootLogin + '?username=' + this.username + '&password=' + this.password)
                .then(res => {
                    if (res.data.code === '200') {
                        this.$router.push({
                            path: '/root/user',
                            query: {
                                username: this.username
                            }
                        })
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
        }
    }
}
</script>
<style lang="css" scoped>
.back {
    width: 100%;
    height: 100%;
    background: url("../../assets/image/OIP-C.jpeg") no-repeat;
    background-size: 100% 100%;
    background-attachment: fixed;
    position: absolute;
}

.main {

    width: 500px;
    height: 300px;
    box-shadow: 0 5px 20px 0 #e8e8e8;
    background-color: white;
    border-radius: 10px;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    margin: auto;
}

.main h2 {
    color: #545a6c;
    margin-left: 150px;
    margin-top: 20px;
}

.form {
    margin-top: 30px;
    margin-left: 60px;
}

.form input {
    width: 300px;
    height: 25px;
    border: none;
    border-bottom: 1px solid lightgray;
    outline: none;
    /*输入内容时不显示边框*/
    margin-bottom: 30px;
}

.form button {
    width: 150px;
    height: 30px;
    color: white;
    background-color: #545a6c;
    border-style: none;
    border-radius: 5px;
    font-size: 15px;
    margin-top: 15px;
    margin-left: 130px;
    outline: none;
    cursor: pointer;
    /*光标放到上面有小手*/
}
</style>
