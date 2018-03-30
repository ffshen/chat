<template>

    <div class="uk-container uk-container-center uk-width-1-1" >
        <hr class="uk-grid-divider">
        <div class = "uk-grid uk-grid-small" >
            <div class="uk-width-1-3">
                <btn-area v-on:login_success="loginSuccess" v-bind:isLogin="isLogin"></btn-area>
                <log-area v-bind:items="logitems"></log-area>
                <user-list-area v-bind:userList="user" v-on:click_user="clickUser"></user-list-area>
                <enter-area v-on:comfirm_send_message="sendMsgToServer"></enter-area>
            </div>
            <div class="uk-width-1-3">

            </div>
        </div>
    </div>
</template>

<script>
    import sockjs from 'sockjs-client'
    import EventBus from 'vertx3-eventbus-client'
    import btnArea from './BtnArea'
    import logArea from './LogArea'
    import enterArea from './EnterArea'
    import userListArea from './UserListArea'
    import _ from 'lodash';

    export default {
        name: "chat",
        components: {
            'user-list-area': userListArea,
            'enter-area'    : enterArea,
            'btn-area'      : btnArea,
            'log-area'      : logArea
        },
        mounted: function () {
            let chat = this ;
            let eb = new EventBus(ENV.websocketUrl);
            eb.onopen = function() {
                eb.registerHandler('chat.to.client', function(error, message) {
                    console.log('received a message: ' + JSON.stringify(message));
                    //如果是USER_LOGIN,或者USER_LOGOUT，那么更新用户列表
                    let eventType = message.body.messageEventType ;
                    if( _.isEqual(eventType,"USER_LOGIN") || _.isEqual(eventType,"USER_LOGOUT")){
                        //拉取用户信息
                        chat.getLoginUserList();
                    }
                });
            };
            this.eb = eb ;
        },
        data(){
            return {
                "isLogin":false ,
                "selectedUsers":[] ,
                "loginCode":"",
                "token":"",
                "message":"",
                "user":[],
                "logitems":"",
                "eb":{}
            }
        },
        methods:{
            sendMsgToServer:function (data) {
                if(_.size(this.selectedUsers) === 0) return ;
                let chat = this ;
                if(_.isEmpty(chat.token)) return ;

                this.$http({
                    url: ENV.apiUrl+"/api/sendMessage",
                    method: 'PUT',
                    body: {
                        "from":chat.loginCode ,
                        "toList":this.selectedUsers ,
                        "message":data.message
                    },
                    // 设置请求头
                    headers: {
                        'Authorization':'Bearer ' + chat.token
                    }
                }).then(function (response) {
                    if("1" === response.body.code){
                    }
                }, function () {
                    // 请求失败回调
                    this.isLogin = false ;
                    this.$ui.confirm('请重新登陆', {
                        "labels":{
                            "cancel":"取消",
                            "ok":"确定"
                        }
                    },() => {
                    })
                });
            },
            clickUser:function (data) {
                this.selectedUsers = data ;
            },
            getLoginUserList:function () {
                let chat = this ;
                if(_.isEmpty(chat.token)) return ;
                this.$http({
                    url: ENV.apiUrl+"/api/getLoginUserList",
                    method: 'GET',
                    data: {
                    },
                    // 设置请求头
                    headers: {
                        'Authorization':'Bearer ' + chat.token
                    },
                    emulateJSON: true
                }).then(function (response) {
                    if("1" === response.body.code){
                        chat.user = response.body.data
                    }
                }, function () {
                    // 请求失败回调
                    this.isLogin = false ;
                    this.$ui.confirm('请重新登陆', {
                        "labels":{
                            "cancel":"取消",
                            "ok":"确定"
                        }
                    },() => {
                    })
                });
            },
            loginSuccess: function (data) {
                let chat = this ;
                chat.isLogin = true ;
                //保存token
                chat.token = data.token ;
                chat.loginCode = data.loginCode ;
                //监听队列消息
                chat.eb.registerHandler('chat.to.client.'+data.loginCode, function(error, message) {
                    console.log('received a private message: ' + JSON.stringify(message));
                    let from = message.body.from ;
                    let msg  = message.body.message ;
                    chat.logitems += from + ":" + msg + '\n';
                });
                chat.getLoginUserList();
            }
        }
    }


</script>