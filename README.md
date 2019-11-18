# springboot_tmall

仿天猫商城

API: [swagger-ui](https://shop.bolitao.xyz/swagger-ui.html)

前台 demo：[home](https://shop.bolitao.xyz)

后台 demo：[admin](https://shop.bolitao.xyz/admin)

## 开发

使用 IntelliJ IDEA 或者 Eclipse 的 `new project from existing sources` 相关功能

## bug

在 Unix/Linux 环境开发/部署时请将数据库中 `orderitem` 表名改为 `orderItem`。

Windows 下 MySQL 不区分大小写，可以不修改。

## 部署

1. 新建本项目所需数据库，请参考 `src/main/resources/db/structure.sql` 文件创建表结构

2. 将 `application.properties` 中的配置修改为 `prod`

3. 修改 `application-prod.properties` 中数据库、端口和日志级别等相关配置

4. 程序运行时检测默认 `3306` 和 `6379` 端口，如果您的 `MySQL` 和 `redis` 使用其他端口请注释或修改 `TmallSpringBootApplication` 类中相关检测代码

5. 使用 maven 打包后，使用 `nohup java -jar xxx.jar > xxx.log &` 运行（也可以使用 screen 或者丢到 docker），如果机器内存不够请合理使用 `-XX:MaxHeapSize=abcM` 参数

## TODO

- 自定义 404
- JWT
- 后台登陆验证
- jar 包方式运行时上传数据持久化
- redis 自动刷新
- 后台使用 `element-admin`