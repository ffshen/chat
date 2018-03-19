
<template>

    <div class="uk-container uk-container-center uk-width-1-1" >
        <div class = "uk-grid uk-grid-small" >
            <div class="uk-width-2-5">
                <btn-area v-on:btn_message="messageFormBtnArea" v-on:clear="clearPrintData"></btn-area>
                <print-table v-bind:items="printItems"></print-table>
            </div>
            <div class="uk-width-3-5">
            </div>
        </div>
    </div>
</template>

<script>

    import btnArea from './BtnArea'
    import printTable from './PrintTable'

    export default {
        name: "settle",
        components: {
            // 'hello': hello,
            'btn-area': btnArea ,
            'print-table':printTable
            // 'settle': settle
        },
        data () {
            return {
                "foo":"bar",
                "weightestparam":{
                    "totalWeight":1342,
                    "error":10,
                    "goodsList":[
                        {
                            "name":"可乐",
                            "weight":431
                        }	,
                        {
                            "name":"红酒",
                            "weight":983
                        }	,
                        {
                            "name":"怡宝",
                            "weight":214
                        },
                        {
                            "name":"喜茶",
                            "weight":87
                        }
                    ]
                },
                "printItems" : [
                    {
                        "name":"奶茶",
                        "size":"大杯",
                        "ice":"加冰",
                        "sugar":"加奶",
                        "add":"珍珠",
                        "price":"1.00",
                    },
                    {
                        "name":"普洱",
                        "size":"小杯",
                        "ice":"小冰",
                        "sugar":"飞奶",
                        "add":"啫喱",
                        "price":"4.00",
                    }
                ]
            }
        },
        mounted: function () {
            this.$http.get("https://api.ffshen.xyz/settle/items/")
                .then((data) => {
                    console.log(data)
                    this.printItems = data.body;
                })

            this.$http.post("https://api.ffshen.xyz/weighing/calculate",this.weightestparam)
                .then(data=>{
                    console.info(data)
                })

        },
        methods : {
            clearPrintData:function () {
                this.printItems = [] ;
            },
            messageFormBtnArea:function (str) {
                console.log("get message."+str)
            },
            confirm : function () {
                this.$ui.confirm('您确认要删除以下的数据吗？', {
                    "labels":{
                        "cancel":"取消",
                        "ok":"确定"
                    }
                },() => {
                    // 这里编写对数据进行删除的代码
                    console.info("callback" + this.foo)
                })
            }

        }
    }
</script>

<style scoped>

</style>