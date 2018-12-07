function parseData(result, blocks, services) {
    if (result.code !== 0) {
        $('#message').html(result.message);
        return;
    }

    if (!result.data) {
        return;
    }

    if (result.data.statuses) {
        parseNodeStatus(result, blocks);
    }

    if (result.data.services) {
        parseServices(result, services);
    }
}

function parseNodeStatus(result, blocks) {
    let statuses = result.data.statuses;
    blocks.nodes.length = 0;

    $.each(statuses, function (index, status) {
        blocks.nodes.push({
            data: status,
            title: status.host_name,
            fields: [
                {name: "CPU", total: status.cpu_count, progress: status.cpu_usage, style: {width: status.cpu_usage}},
                {name: "Mem", total: status.mem_total, progress: status.mem_usage, style: {width: status.mem_usage}},
                {name: "Swp", total: status.swap_total, progress: status.swap_usage, style: {width: status.swap_usage}},
                {name: "FS", value: status.min_disk_path || '?'},
                {name: " ", value: status.min_disk_available || '?'},
            ]
        });
    });
}

function parseServices(result, servicesVue) {
    let serviceList = result.data.services;
    servicesVue.services.length = 0;

    $.each(serviceList, function (index, service) {
        servicesVue.services.push({
            data: service,
            name: service.name,
            host: service.host
        });
    });
}
