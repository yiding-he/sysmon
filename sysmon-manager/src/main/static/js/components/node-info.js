let __parse_percentage = function(s) {
    return parseFloat(s.substring(0, s.length - 1));
};

let __parse_bytesize = function(s) {
    let s1 = s.substring(0, s.length - 3).replace(/,/g, "");
    return parseInt(s1);
};

Vue.component('node-info', {
    props:["node"],
    data() {
        return {
        };
    },
    methods: {
    },
    computed: {
        isWarn: function() {
            if (__parse_percentage(this.node.data.cpu_usage) > 70) {
                return true;
            }
            if (__parse_percentage(this.node.data.mem_usage) > 85) {
                return true;
            }
            if (__parse_bytesize(this.node.data.min_disk_available) < 1500000) {
                return true;
            }
            return false;
        }
    },
    template:
        `<div class="block" :class="{warn: isWarn}">
            <div class="title">{{ node.title }}</div>
            <div class="field" v-for="field in node.fields">
                <div v-if="field.progress && field.total != '0 KB'">
                    <span class="label">{{ field.name }}</span>
                    <div class="progress">
                        <div class="progress-indicator" :style="field.style"></div>
                    </div>
                </div>
                <div v-else-if="field.value">
                    <span class="label">{{ field.name }}</span>
                    <span class="value">{{ field.value }}</span>
                </div>
            </div>
        </div>`
});
