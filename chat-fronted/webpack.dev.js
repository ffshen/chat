 const merge = require('webpack-merge');
 const common = require('./webpack.common.js');
 const webpack = require('webpack');
 const path = require('path');//路径

 module.exports = merge(common, {
	devtool: 'inline-source-map',
	devServer: {
	   contentBase: './dist'
	}   ,
     plugins: [
      new webpack.DefinePlugin({
           'process.env.NODE_ENV': JSON.stringify('development')
      }),
         new webpack.ProvidePlugin({
             ENV: path.resolve(__dirname, "src/env/development")
         })
    ]
 })