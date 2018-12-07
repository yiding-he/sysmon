Vue.component('service-info', {
    props: ['service'],
    data() {
        return {};
    },
    template: `
<div class="service" :class="{dead: service.dead}">
    {{ service.name }} - {{ service.host }}
</div>
    `
});