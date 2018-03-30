<template>
    <div class="uk-margin">
        <div v-if="isLogin" class="uk-grid uk-grid-small">
            <div class="uk-width-4-5">你好!{{name}}</div>
        </div>
        <div v-else class="uk-grid uk-grid-small">
            <div class="uk-width-4-5"><input type="text" placeholder="请输入登陆码" v-model="loginCode"  class="uk-input uk-width-1-1"></div>
            <div class="uk-width-1-5"><button class="uk-button uk-button-mini uk-button-primary uk-width-1-1" v-on:click="login">Login</button></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "btn-area",
        props:["isLogin"],
        data: function () {
            return {
                "name":"",
                "loginCode":""
            }
        },
        methods :{
            clear:function () {
            },
            login: function () {
                this.$http.post(ENV.apiUrl+"/login",{
                    "loginCode":this.loginCode
                }).then(response=>{
                    if("1" === response.body.code){
                        this.name = response.body.name ;
                        this.$emit('login_success',{
                            "token":response.body.data,
                            "loginCode":response.body.loginCode
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>