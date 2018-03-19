// import _ from 'lodash';
//test.remove
import Vue from 'vue'
import hello from './Hello'
// import btnArea from './component/BtnArea'
// import printTable from './component/PrintTable'
import settle from './component/Settle'
import weighing from './component/Weighing'
import VueResource from 'vue-resource'
import uikit from './uikit'
import 'uikit-css'
import VueRouter from 'vue-router'

Vue.use(uikit)
Vue.use(VueResource)
Vue.use(VueRouter)

const routes = [
    { path: '/settle', component: settle },
    { path: '/weighing', component: weighing }
]

const router = new VueRouter({
    routes // （缩写）相当于 routes: routes
})

new Vue({
    router,
    el: '#app',
    components: {
        // 'hello': hello,
        // 'btn-area': btnArea ,
        // 'print-table':printTable
        'settle': settle,
        'weighing': weighing
    }
})