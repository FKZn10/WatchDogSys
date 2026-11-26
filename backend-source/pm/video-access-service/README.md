# 视频接入业务后端

Java 8 / Spring Boot 2.6.7 / Spring Cloud 2021.0.0。目录为 `Spring/pm/video-access-service`，与 projectProducer、projectConsumer 同级，属于 pm 仓库。作为现有 IDEA 工程中的独立 Maven 模块，独立打包，后续注册公司 Eureka；目录归属与进程是否独立是两回事。

本模块管理项目/摄像头绑定、前端接口、云台控制租约、观看票与回放会话。WVP 处理国标 SIP，ZLM 接收 RTP 和分发媒体，业务 Java 不转发视频字节。入口鉴权复用现有平台，不新增登录系统。

**从这里开始阅读：[代码阅读路线](docs/代码阅读路线.md)。** IDEA 的启动入口是 [VideoAccessApplication](src/main/java/com/jpmsoft/videoaccess/VideoAccessApplication.java)，接口从 [VideoController](src/main/java/com/jpmsoft/videoaccess/api/VideoController.java) 进入。

本轮已复用公司 `common:0.0.1` 的 BaseResponse，当前后端 116 项测试通过；说明见 [common 复用与统一档位](docs/common复用与统一档位.md)。每台目标约 1080p，单/四屏和不同用户使用同一档；实际设备编码与规模仍待验收。

## 当前完成范围

- module 服务端绑定、目录分页、单路/四路预览、独立观看票/续约/释放，共享源自动回收。
- 云台独占租约、续约、到期 STOP、旧租约保护与控制记录；受理不等于设备机械动作已经发生。
- 固定录像检索、回放票/revision、暂停/继续、重新拉取目标范围实现定位，以及到期清理。
- Java 8 后端 112 项为上一阶段基线；四个不同合成通道 Chrome 12 项、Edge 4 项检查通过。真实设备/4G/录像存储、公司入口/网关、规模容量和 ARM64 仍待验收，不累计历史测试数作为本轮全量结果。

## IDEA 运行

将本 pom.xml 作为 Maven 工程导入。Project SDK、Maven Runner、模块 SDK 均使用 JDK 8；当前工程已有 `temurin-1.8` SDK。安装后提供两个 Application 配置：

| 配置 | 端口 | 用途 |
| --- | --- | --- |
| VideoAccess - local study | 18091，仅 loopback | local 空目录，学习断点/启动；不连接数据库、Eureka 或视频组件 |
| VideoAccess - module lab | 18090，仅 loopback | module,wvp + 忽略的 `.local/application-video-lab.yml`，接本机 Docker 实验环境 |

同一端口只启动一个实例。若命令行联调进程占用 18090，先确认并停止自己的本模块进程，再点 module lab 的 Debug；不停止其他公司服务。WVP 重启可能使本地服务 key 失效，需通过实验脚本刷新并重新生成本机私有配置。密钥不写进 `.run` 或仓库。

从命令行构建：

```powershell
.\Build.ps1 -Goal package -JdkHome 'C:\Program Files\Eclipse Adoptium\jdk-8.0.504.1-hotspot'
```

脚本只使用已有 Maven/JDK8，临时设置并还原 JAVA_HOME。`package` 会执行测试；端口不占用时可用 Java8 运行 JAR 并传 `--spring.profiles.active=local --server.port=18091`。

## 模式与地址

2026-11-12 新服务器临时 HTTP 测试：`video.wvp.allow-insecure-media-for-test=true` 仅允许配置中的 IPv4 字面地址使用 HTTP，默认关闭，HTTP 域名仍拒绝。完整播放 URL 的来源、端口、路径和参数校验保持启用。仅用于没有证书的 HTTP 测试页面，HTTPS 页面仍需 HTTPS 媒体入口。云端部署和访问范围见工作区 `cloud-deployment/新服务器国标组件发布-20261112.md`。

- 默认：关闭 Eureka，未配置业务适配时明确拒绝业务。
- `local`：演示空目录，pId 为 demo-project；不能和 module/wvp/registry 合用。
- `module`：平台功能模块；绑定由 `video.module.cameras` 配置，当前未接平台操作者时记 PLATFORM_UNRESOLVED。
- `wvp`：固定 WVP HTTP 适配，使用 WVP_BASE_URL/WVP_API_KEY/WVP_MEDIA_BASE_URL 或本机外部配置。预览使用 DEFAULT（省略也按默认档位），旧 MAIN 作为兼容别名，不保证物理主码流；SUB 明确拒绝。单/四屏不修改全局档位，不静默降级。
- `company-directory`：逐次转发当前请求 token 读取公司目录；不会自动启用视频、注册或新的身份系统。
- `registry`：需显式公司 Eureka/监听/实例地址，真实公司注册尚未验收。现有配置默认关闭注册。

Controller 基础路径 `/api/video/v1`，12 个 POST 业务接口和 GET `/health`。health 的 UP 只表示服务存活，businessReady 当前固定 false，不能据此判定设备就绪。

观看票和回放票约 5 秒续约、30 秒失效。实时 release 只释放自己的票，实际无人读取后由 WVP/ZLM 关闭媒体。回放当前单业务实例、同一物理通道一次回放；第二请求者明确收到占用。在线状态为 unknown，待真实字典确认。

`src/main/resources` 只提交模板。`.local/`、target、日志和 IDEA 缓存不提交、不在源码同步范围。现有前端仍使用 9527；源码同步和实验文档位于本机监控开发工作区，IDEA 阅读路线解释了双向同步与冲突处理。
