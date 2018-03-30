<template>
    <div class="uk-margin">
        <div class="uk-grid uk-grid-small">
            <div class="uk-width-4-5"><textarea class="uk-textarea" rows="2" placeholder="输入" v-model="message"></textarea></div>
            <div class="uk-width-1-5"><button class="uk-button uk-button-primary  uk-width-1-1"  v-on:click="enter">SEND</button></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "enter-area",
        data: function (){
            return {
                "message": ""
            }
        },
        methods:{
            "enter":function(){
                this.$emit('comfirm_send_message',{
                    "message":this.message
                });
                this.message = "" ;
            }
        }
    }
</script>

<style scoped>

</style>