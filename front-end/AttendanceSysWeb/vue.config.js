module.exports = {
    //...自定义webpack的配置
    devServer: {
      host: 'localhost',
     },

    configureWebpack: {
        externals: {
          'AMap': 'AMap' // 高德地图配置
        }
      }
  }