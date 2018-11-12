<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>System Monitoring</title>
</head>
<body>
<#list statuses as status>
    <p>${status.host}, cpu usage: ${status.cpuUsage}</p>
</#list>
</body>
</html>