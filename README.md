# 工地视频监控交接

## 目录

| 目录 | 用途 |
| --- | --- |
| `frontend-runtime/` | 当前监控单页的 HTML、JS、CSS，以及 Node 网关和登录会话代码 |
| `backend-source/` | Java 后端源码。从 `pm/projectProducer` 和 `pm/projectConsumer` 看监控接口，`zuul` 是 API 网关，`user` 负责登录 |
| `backend-config/` | Eureka、数据库、Redis 等本机配置 |
| `database/` | 四张业务表的结构、7 个项目、6 个点位及公司和部门记录。[查看 Navicat 导入说明](database/README.md) |
| `private/backend-jars/` | 单独交接的 9 个可运行 JAR，合计约 1.21 GB |
| `private/camera.local.sql` | 单独交接的萤石真机配置，由有设备权限的同学导入 |

## 前置条件

| 工具 | 本机交付版本和用途 |
| --- | --- |
| Windows、PowerShell、Git | 克隆仓库，在 PowerShell 启动和停止服务 |
| Node.js **24.22.3** | 运行前端 Node 网关。当前页面是编译包，启动时直接用 Node 即可 |
| Temurin JDK **8u504** | 运行现有 Java JAR。把 Java 8 的 `bin` 加入 PATH，使用 `java -version` 确认 |
| MySQL Community Server **8.4.9** | 本机 TCP **13306**，数据库 `vue_antd_admin`。用 Navicat 或 mysql 客户端导入 |
| Redis **3.0.504** | 原本机运行版本，监听本机 TCP **16379**。交接配置使用本机 Redis，保持仅本机可访问 |
| IDEA、Maven | 查看和构建 Java 源码时用。各模块以自己的 `pom.xml` 导入；全量重编译还需要相应的公共 Maven 依赖、厂商库和开发配置 |
| 浏览器、萤石设备访问权限 | 本机需能访问萤石云，使用的应用凭据须能访问所选设备通道 |

## Windows 本机启动

1. 在仓库根目录运行 `node --version` 和 `java -version`，确认 PATH 命中上表的版本。
2. 把交接的九个 JAR 放入 `private/backend-jars/`。在本机启动 MySQL 和 Redis，确保各自端口为 13306 和 16379。
3. 在 Navicat 创建 `vue_antd_admin`，导入 `database/schema.sql`、`database/seed.sql`，有权限接入设备时再导入真机配置。按 `backend-config/database-local.properties` 配置本机数据库账号，默认使用 `jpm_local`，授予该账号访问这个本机库所需的权限。
4. **整站登录还需要该版本的用户、角色、权限及系统配置初始化数据。** 本仓库的四表 SQL 已通过空库导入验证，启动整套后端之前需要在同一个本机库完成登录初始化。
5. 在根目录运行 `powershell -NoProfile -ExecutionPolicy Bypass -File .\Start-Monitor.ps1`，按提示输入上一步配置的 MySQL 密码。脚本依次启动 Eureka、用户服务、API 工具、项目 Producer、项目 Consumer、文件、流程、定制和 Zuul，再启动 Node 网关。
6. 等待后台注册，在浏览器打开 [本机监控页面](http://127.0.0.1:18080/hbgpc/video/)。后端和网关的输出写入 `logs/`。关闭这次启动的应用进程，运行 `powershell -NoProfile -ExecutionPolicy Bypass -File .\Stop-Monitor.ps1`。

默认端口为 Eureka 8081、用户 8082、Zuul 8085、文件 8087、项目 Producer 8091、项目 Consumer 8092、流程 8093、定制 8116、API 工具 8192、页面 18080。验收按“登录 → 选项目 → 选点位 → 实时画面持续更新”进行。

## 继续开发

后端源码中检出的明文凭据已清空，原部署环境配置和厂商二进制依赖由维护者交接。JAR 是当前运行版本，包含本机取流相关的历史补丁。全量源码重编译和学生机登录、出画有待实测。

已找到的可编辑 Vue 监控模块来自 `pm_web_v5`。该工程声明 Node.js 20.19.0 以上，锁定 pnpm 11.21.0。当前仓库交付的页面仍使用已提供的编译包，独立页面对应的完整 Vue 工程尚未核对。
