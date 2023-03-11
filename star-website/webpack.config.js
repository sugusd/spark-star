const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const CopyPlugin = require('copy-webpack-plugin')
const Dotenv = require('dotenv-webpack');

const config = {
  mode: process.env.NODE_ENV,
  devtool: process.env.DEV_TOOL,
  entry: {
    index: './src/index.tsx'
  },
  output: {
    path: path.resolve(__dirname, "./build"),
    publicPath: process.env.PUBLIC_PATH,
  },
  devServer: {
    compress: false,
    historyApiFallback: true,
    port: process.env.PORT,
  },
  resolve: {
    fallback:{
      'react/jsx-runtime': 'react/jsx-runtime.js',
      'react/jsx-dev-runtime': 'react/jsx-dev-runtime.js'
    },
    extensions: ['.js', '.jsx', '.tsx', '.ts'],
  },
  plugins: [
    // new CopyPlugin({
    //   patterns: [
    //     {from: './public/assert', to: './assert'},
    //   ],
    // }),
    new HtmlWebpackPlugin({
      template: './public/index.html'
    }),
    new CleanWebpackPlugin(),
    new Dotenv({
      path: process.env.DOTENV_CONFIG,
    })
  ],
  module: {
    rules: [
      {
        test: /\.ts(x)?$/,
        loader: 'ts-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.svg$/,
        loader: 'svg-sprite-loader',
        options: {},
      },
      {
        test: /\.css|s[ac]ss$/i,
        use: ['style-loader', 'css-loader', 'sass-loader'],
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: 'file-loader',
        exclude: /node_modules/,
      },
    ],
  }
}

module.exports = config
