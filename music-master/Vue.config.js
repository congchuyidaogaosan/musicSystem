module.exports = {
  pwa: {
    iconPaths: {
      favicon32: 'favicon.ico',
      favicon16: 'favicon.ico',
      appleTouchIcon: 'favicon.ico',
      maskIcon: 'favicon.ico',
      msTileImage: 'favicon.ico'
    }
  },
  publicPath: "./",
  outputDir: process.env.outputDir,
  css: {
    loaderOptions: {
      sass: {
        prependData: '@import "~@/assets/scss/common.scss";'
      },
    },
  },
  devServer: {
    overlay: { // 让浏览器 overlay 同时显示警告和错误
      warnings: true,
      errors: true
    },
    host: "0.0.0.0",
    port: 8088, // 端口号
    https: false, // https:{type:Boolean}
    open: true, //配置自动启动浏览器
    hotOnly: true, // 热更新
    proxy: {
      "/api": {
        target: "http://localhost:8081", //李
        changeOrigin: true,
        // ws: true,//websocket支持
        secure: false,
        pathRewrite: {
          "^/api": "/"
        }
      },
    }
  },
  configureWebpack: {
    externals: 'hls.js'
  } // 在这配置webpack的externals这个字段
}
