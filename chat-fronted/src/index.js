// import _ from 'lodash';
import Vue from 'vue'
import VueResource from 'vue-resource'
import VueRouter from 'vue-router'
//

//
import uikit from './uikit'
import 'uikit-css'
//

import chat from './component/Chat'

Vue.use(uikit)
Vue.use(VueResource)
Vue.use(VueRouter)

const routes = [
    // { path: '/settle', component: settle },
     { path: '/chat',name: 'chat', component: chat },
    // 重定向
    {  path: '/',     component: chat}//直接输入/chat是报404的
]

const router = new VueRouter({
    mode:"history",
    routes // （缩写）相当于 routes: routes
})

new Vue({
    router,
    el: '#app',
    components: {
        // 'hello': hello,
        // 'btn-area': btnArea ,
        // 'print-table':printTable
        // 'settle': settle,
        'chat': chat
    }
})


