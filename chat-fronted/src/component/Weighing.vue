<template>
    <div uk-grid class="uk-grid-small">
        <div uk-grid class="uk-width-1-2 uk-grid-small">
            <div class="uk-width-3-4"><input type="text" placeholder="请输入总重量" v-model="weight"  class="uk-input"></div>

            <div class="uk-width-3-4"><input type="text" placeholder="请输入误差" v-model="error"  class="uk-input uk-width-1-1"></div>
            <div class="uk-width-1-1" v-for="good in goods">{{good.name}}  :  <input type="text" placeholder="请输入重量" v-model="good.weight"  class="uk-input uk-width-1-4">
                <input type="text" placeholder="请输入误差" v-model="good.error"  class="uk-input uk-width-1-4"></div>

        </div>

        <div class="uk-width-1-2">
            <button class="uk-button uk-button-mini uk-button-primary" v-on:click=calculate>计算</button>

            <div class="uk-width-1-1">
            <div class="uk-width-1-1" v-for="r in result">{{r}}</div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "weighing",
        methods: {
            calculate: function () {
                this.$http.post("https://api.ffshen.xyz/weighing/calculate",{
                    "totalWeight":this.weight,
                    "error":this.error,
                    "goodsList":this.goods
                }).then(data=>{
                        this.result = data.body
                    })
            }
        },
        data(){
            return {
                "result":"",
                "weight":9567,
                "error": 10 ,
                "goods":[
                    {
                        "name":"可乐",
                        "weight":431 ,
                        "error":0.5
                    }	,
                    {
                        "name":"红酒",
                        "weight":983,
                        "error":0.5
                    }	,
                    {
                        "name":"怡宝",
                        "weight":214,
                        "error":0.5
                    },
                    {
                        "name":"喜茶",
                        "weight":87,
                        "error":0.5
                    },
                    {
                        "name":"奶茶",
                        "weight":567,
                        "error":0.5
                    },
                    {
                        "name":"绿茶",
                        "weight":398,
                        "error":0.5
                    },
                    {
                        "name":"啤酒",
                        "weight":28,
                        "error":0.5
                    },
                    {
                        "name":"黑茶",
                        "weight":76,
                        "error":0.5
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>