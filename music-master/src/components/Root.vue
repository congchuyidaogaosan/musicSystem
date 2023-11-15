<template>
    <div>
        <div class="left">
            <div class="bigTitle">音乐后台管理系统</div>
            <div class="lines">
                <div >
                    <router-link to="/root/user"> <img src="../assets/image/icon-1.png" />用户管理</router-link>
                </div>
                <div >
                    <router-link to="/root/music"> <img src="../assets/image/icon-2.png" />音乐管理</router-link>
                </div>
                <div >
                    <router-link to="/root/com"><img src="../assets/image/icon-3.png" />评论管理</router-link>
                </div>
            </div>
        </div>
        <div class="content">
            <router-view />
        </div>
    </div>
</template>
<script>
export default {
    beforeCreate() {
        const username = this.$route.query.username;
        if (username == null) {
            this.$router.push('/rootLogin')
        }
    }
}
</script>
<style lang="scss" scoped>
.left {
    background: #313541;
    width: 20%;
    float: left;
    position: absolute;
    top: 0px;
    bottom: 0px;

    .bigTitle {
        background: #3d598a;
        color: #fff;
        height: 100px;
        line-height: 100px;
        text-align: center;
        font-size: 24px;
        font-family: -webkit-body;
    }

    .lines {
        a {
            color: #fff;
            text-decoration: none;
        }

        div {
            height: 70px;
            line-height: 70px;
            padding-left: 50px;
            color: #707783;

            &:hover {
                cursor: pointer;
                color: #ffffff;
            }

            img {
                width: 23px;
                vertical-align: middle;
                margin-bottom: 4px;
                margin-right: 9px;
            }
        }

        .active {
            background: #272a34;
            color: #fff;
            height: 70px;
            line-height: 70px;
            padding-left: 50px;
            color: #707783;

            &:hover {
                cursor: pointer;
                color: #ffffff;
            }

            img {
                width: 23px;
                vertical-align: middle;
                margin-bottom: 4px;
                margin-right: 9px;
            }
        }
    }
}

.content {
    float: right;
    width: 80%;
    text-align: center;
}
</style>
