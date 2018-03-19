<template>
    <div class="uk-grid uk-grid-small">
        <div class="uk-width-2-5"><input type="text" placeholder="请输入单号" v-model="order_id"  class="uk-input uk-width-1-1"></div>
        <div class="uk-width-1-5"><button class="uk-button uk-button-mini uk-button-primary uk-width-1-1" v-on:click="sendMsgToMain">OK</button></div>
        <div class="uk-width-1-5"><button class="uk-button uk-button-mini uk-button-danger  uk-width-1-1" v-on:click="clear">清空</button></div>
        <div class="uk-width-1-5"><button class="uk-button uk-button-mini uk-button-primary uk-width-1-1" id = "resetButton">重置</button></div>
    </div>
</template>

<script>
    export default {
        name: "btn-area",
        data: function () {
            return {
                "order_id":88
            }
        },
        methods :{
            clear:function () {
                this.$emit('clear')
            },
            sendMsgToMain: function () {
                console.info("btnarea call.")
                this.$emit('btn_message',"hello")
            }
        }
    }
</script>

<style scoped>

</style>