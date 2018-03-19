const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    entry: {
      app:   './src/index.js'
   },
   output: {
		filename: '[name].[hash].bundle.js',
		path: path.resolve(__dirname, 'dist')
    }	 ,  
	plugins: [
      new CleanWebpackPlugin(['dist']),
      new HtmlWebpackPlugin({
        title: 'Output Management',
          template: './admin.html',
      })
    ],
    resolve: {
        alias: {
            'uikit$': 'uikit/dist/js/uikit.js',
            'uikit-rtl-css$': 'uikit/dist/css/uikit-rtl.css',
            'uikit-css$':'uikit/dist/css/uikit.css',
            'vue$': 'vue/dist/vue.common.js'
        },
        extensions: [ '.js', '.vue'], // 引用js和vue文件可以省略后缀名
    },
    //4.0配置
    optimization: {
        runtimeChunk: {
            name: "manifest"
        },
        splitChunks: {
            cacheGroups: {
                commons: {
                    test: /[\\/]node_modules[\\/]/,
                    name: "vendor",
                    chunks: "all"
                }
            }
        }
    },
	module: {
     rules: [
       {
         test: /\.css$/,
         use: [
           'style-loader',
           'css-loader'
         ]
       }  ,
       {
         test: /\.(png|svg|jpg|gif)$/,
         use: [
           'file-loader'
         ]
       },
       {
             test: /\.vue$/,
             loader: 'vue-loader'
       },
       { test: /\.(eot|woff|woff2|svg|ttf)([\?]?.*)$/, loader: "file-loader" }
     ]
   }
};