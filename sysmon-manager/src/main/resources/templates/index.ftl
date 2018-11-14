<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        td {
            padding: 0.2em 0.5em;
        }
    </style>
    <title>System Monitoring</title>
</head>
<body>

<table border="1">
    <thead>
    <tr>
        <td>HOST</td>
        <td>CPU Usage</td>
        <td>Total Memory</td>
        <td>Memory Usage</td>
        <td>Total Swap</td>
        <td>Swap Usage</td>
    </tr>
    </thead>
    <tbody>
    <#list statuses as status>
    <tr>
        <td>${status.host}</td>
        <td>${status.cpu_usage}</td>
        <td>${status.mem_total}</td>
        <td>${status.mem_usage}</td>
        <td>${status.swap_total}</td>
        <td>${status.swap_usage}</td>
    </tr>
    </#list>
    </tbody>
</table>

</body>
</html>