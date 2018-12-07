Vue.component('service-info', {
    props: ['service'],
    data() {
        return {};
    },
    template: `
<div class="service" :class="{dead: service.data.dead}">
    {{ service.data.name }} - {{ service.data.host_name }}
</div>
    `
});