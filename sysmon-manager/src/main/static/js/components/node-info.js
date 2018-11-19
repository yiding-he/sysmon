Vue.component('node-info', {
    props:["node"],
    data() {
        return {};
    },
    template:
        `<div class="block">
            <div class="title">{{ node.title }}</div>
            <div class="field" v-for="field in node.fields">
                <span class="label">{{ field.name }}</span>
                <span class="value">{{ field.value }}</span>
            </div>
        </div>`
});
