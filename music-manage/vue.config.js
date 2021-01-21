const env = process.env.VUE_APP_CURRENT_ENV || 'dev'

// 基础配置项缓存
let util = {
  indexPath: 'index.html',
  publicPath: '/',
  outputDir: 'admin' //打包生成的文件目录
}

if (['dev'].includes(env)) {
  util.devServer = {
    // 代理
    port: 8080, // 端口号
    host: '0.0.0.0',
    disableHostCheck: true,
    proxy: {
      '/': {
        target: 'http://localhost:8888',
        changeOrigin: true,
        cookieDomainRewrite: {
          '*': ''
        }
      }
    }
  }
}

if (env === 'production') {
  util.publicPath = ''
}

module.exports = {
  configureWebpack: {
    performance: {
      hints: false
    },
    resolve: {
      alias: {
        assets: '@/assets',
        components: '@/components',
        views: '@/views',
        network: '@/network',
        utils: '@/utils',
        store: '@/store'
      }
    }
  },
  ...util
}
