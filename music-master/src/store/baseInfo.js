export default {
  state: {
    musicList: [],
    musicInfo: {},
    selectIndex: 1,
    userInfo: {},
    otherUser: {},
    userType: 0,
    myLikeList: [],
    searchWord: ''
  },
  mutations: {
    setMusicList(state, musicList) {
      state.musicList = musicList;
    },
    setMusicInfo(state, musicInfo) {
      state.musicInfo = musicInfo;
    },
    setSelectIndex(state, selectIndex) {
      state.selectIndex = selectIndex;
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    setUserType(state, userType) {
      state.userType = userType;
    },
    setMyLikeInfo(state, myLikeList) {
      state.myLikeList = myLikeList;
    },
    setSearchWord(state, searchWord) {
      state.searchWord = searchWord;
    },
    setCurrentTime(state, currentTime) {
      state.currentTime = currentTime;
    },
    setPlayState(state, playState) {
      state.playState = playState;
    }
  }
}
