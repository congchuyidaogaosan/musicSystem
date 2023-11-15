/* eslint-disable */
var BASE_URL = 'http://localhost:8081';
const UrlConfig = {

  allSliders: BASE_URL + '/slide/showSlide', //轮播图

  topLists: BASE_URL + '/lists/topLists', //推荐歌单

  allLists: BASE_URL + '/lists/allLists', //获取全部歌单

  listByType: BASE_URL + '/lists/listByType', //通过类型查询歌单

  listById: BASE_URL + '/lists/listById', //歌单内容

  addLists: BASE_URL + '/lists/addLists', //新增歌单

  listByUserId: BASE_URL + '/lists/listByUserId', //我的歌单

  colListByUserId: BASE_URL + '/lists/colListByUserId', //我收藏的歌单

  putList: BASE_URL + '/lists/putLists', //修改歌单信息

  isCol: BASE_URL + '/lists/isCol', //进入歌单后判断是否收藏了歌单

  addCol: BASE_URL + '/lists/addCol', //收藏歌单

  delCol: BASE_URL + '/lists/delCol', //取消收藏歌单

  addSongToList: BASE_URL + '/lists/addSongToList', //歌曲添加到歌单

  listByName: BASE_URL + '/lists/listByName', //通过名字查询歌单

  songOfLists: BASE_URL + '/song/songOfLists', //获取歌单的歌曲

  songOfAlbum: BASE_URL + '/song/songOfAlbum', //获取专辑歌曲

  songOfSinger: BASE_URL + '/song/songOfSinger', //根据歌手获取歌曲

  addSongOfUser: BASE_URL + '/song/addSongOfUser', //收藏歌曲

  delSongOfUser: BASE_URL + '/song/delSongOfUser', //取消收藏歌曲

  songByName: BASE_URL + '/song/songByName', //通过名字查询歌曲

  allComments: BASE_URL + '/comments/allComments', //获取评论

  addComm: BASE_URL + '/comments/addComm', //添加评论

  topSinger: BASE_URL + '/singer/topSinger', //推荐歌手

  allSinger: BASE_URL + '/singer/allSinger', //获取歌手

  singerByType: BASE_URL + '/singer/singerByType', //获取歌手

  singerById: BASE_URL + '/singer/singerById', //获取歌手信息

  singerByName: BASE_URL + '/singer/singerByName', //通过名字查询歌手

  albumOfSinger: BASE_URL + '/album/albumOfSinger', //根据歌手获取专辑

  albumById: BASE_URL + '/album/albumById', //查询专辑

  albumByName: BASE_URL + '/album/albumByName', //通过名字查询专辑

  login: BASE_URL + '/user/login', //登录

  logout: BASE_URL + '/user/logout', //登出

  userById: BASE_URL + '/user/userById', //tongguoID查询User

  register: BASE_URL + '/user/register', //注册

  userByAccount: BASE_URL + '/user/userByAccount', //查询此账号是否已经使用

  userByPhone: BASE_URL + '/user/userByPhone', //查询此电话是否已经使用



  putUser: BASE_URL + '/user/putUser', //修改用户信息

  userByName: BASE_URL + '/user/userByName', //通过名字查询用户

  myFocus: BASE_URL + '/fans/myFocus', //获取我的关注

  myFans: BASE_URL + '/fans/myFans', //获取我的粉丝

  focusCount: BASE_URL + '/fans/focusCount', //我的关注数量

  fansCount: BASE_URL + '/fans/fansCount', //我的粉丝数量

  singerIsFocus: BASE_URL + '/fans/singerIsFocus', //判断歌手是否为我的关注

  userIsFocus: BASE_URL + '/fans/userIsFocus', //判断用户是否为我的关注

  userIsFans: BASE_URL + '/fans/userIsFans', //判断用户是否为我的粉丝

  addFocus: BASE_URL + '/fans/addFocus', //添加关注

  delFocus: BASE_URL + '/fans/delFocus', //取消关注

  rootLogin: BASE_URL + '/root/login', //Root登陆

  allUser: BASE_URL + '/user/allUser', //获取全部用户

  delUser: BASE_URL + '/user/delUser', //删除用户

  allSong: BASE_URL + '/song/querySongs', //获取全部用户
  
  delSong: BASE_URL + '/song/delSong', //删除用户

  songUpload: BASE_URL + '/song/upload', //上传

  allComm: BASE_URL + '/comments/allComm', //获取全部评论
  
  delComm: BASE_URL + '/comments/delComm', //获取全部评论
}
export default {
  UrlConfig
};
