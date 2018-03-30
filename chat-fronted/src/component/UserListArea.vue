<template>

    <div class="uk-offcanvas-content">

        <button class="uk-button uk-button-default uk-margin-small-right" type="button" uk-toggle="target: #offcanvas-push">Send to...</button>

        <div id="offcanvas-push" uk-offcanvas="mode: push; overlay: true">
            <div class="uk-offcanvas-bar">

                <button class="uk-offcanvas-close" type="button" uk-close></button>
                <div class="uk-grid-small uk-text-center" uk-grid>
                    <div v-for="(user, index) in userList" >
                        <my-div  :loginCode='user.loginCode' :name='user.name' @childonclick="handleMyDiv"></my-div>
                    </div>
                </div>
            </div>
        </div>

    </div>




</template>

<script>
    import _ from 'lodash';

    var Child = {
        template: `<div v-bind:class="classObject" :loginCode='loginCode' v-on:click="ChildClick($event,loginCode)">{{name}}</div>`,
        props: ['loginCode','name'],
        data:function (){
            return  {
                isClick: false
            }
        },
        watch: {
            isClick: function (newValue, oldValue) {
                this.$emit('childonclick',{
                    "isClick"   : newValue,
                    "loginCode" : this.loginCode
                });
            }
        },
        computed: {
            classObject: function () {
                return {
                    'uk-card uk-card-default uk-card-body': !this.isClick  ,
                    'uk-background-primary uk-card-body': this.isClick
                }
            }
        },
        model: {
            prop: 'loginCode',
            event: 'balabala'
        },
        methods:{
            "ChildClick":function (event,loginCode) {
                var el = event.currentTarget;
                this.isClick = !this.isClick;
            }
        }
    }

    export default {
        components: {
            'my-div': Child
        },
        props: ['userList'],
        name: "user-list-area",
        methods:{
            handleMyDiv:function (data) {
                if(data.isClick){
                    this.checkedUserList.push(data.loginCode)
                }
                else{
                    _.remove(this.checkedUserList , function(n) {
                        return data.loginCode == n ;
                    });
                }
                this.$emit('click_user',this.checkedUserList);
            }
        }    ,
        watch: {
            // 数组为空不行.触发之后，new和old都是一样的.
            // checkedUserList: function (newList, oldList) {
            //     console.info("newList:"+newList)
            //     console.info("oldList:"+oldList)
            //     this.$emit('click_user',this.checkedUserList);
            // }
        },
        data: function (){
            return {
                checkedUserList: [ ]
            }
        }
    }
</script>

<style scoped>

</style>