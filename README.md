# sysmon

Linux 系统状态监控，分为服务器(sysmon-manager)和客户端(sysmon-agent)两部分。

## 客户端

客户端没有额外的依赖关系，打包之后使用 `java -jar` 命令运行即可。

客户端要求一个运行参数，即服务端的更新接口地址。

客户端需要向 `~/.host_name` 文件（如果没有就创建）中写入当前主机名称，以便区分。

## 服务器

服务器是一个标准的 Spring Boot 项目。

服务器启动后，就等着客户端发送请求。请求当中包含客户端所在的主机状态信息。

服务器页面效果如图：

![firefox_fx6d8jy06b](https://user-images.githubusercontent.com/900606/48876302-4a7bb280-ee38-11e8-8ded-76f35db7e5b0.png)
