<template>
    <div class="uk-overflow-container uk-scrollable-box" style = "height:540px">
        <table class="uk-table uk-table-hover uk-table-striped uk-table-small" id = "printTable">
            <thead>
            <tr>
                <th>序号</th>
                <th>产品</th>
                <th>选杯</th>
                <th>冰</th>
                <th>糖</th>
                <th>加料</th>
                <th>￥</th>
            </tr>
            </thead>

            <tbody>
                <tr v-for="(item, index) in items">
                    <td>{{ index }} </td><td>{{ item.name }}</td><td>{{ item.size }}</td><td>{{ item.ice }}</td><td>{{ item.sugar }}</td><td>{{ item.add }}</td><td>{{ item.price }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        props: ['items'],
        name: "print-table"
    }
</script>

<style scoped>

</style>