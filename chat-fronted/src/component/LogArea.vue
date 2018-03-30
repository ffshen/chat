<template>
    <div class="uk-margin">
        <textarea class="uk-textarea" id = "content" rows="10" readonly  placeholder="聊天记录">{{items}}</textarea>
    </div>
</template>

<script>
    export default {
        props: ['items'],
        name: "log-area"
    }
</script>

<style scoped>

</style>