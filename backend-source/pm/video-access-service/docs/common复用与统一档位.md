# common 复用与统一档位

当前要求：每台摄像头目标约 1080p，单屏、四屏和不同观看者使用同一档位；首期不做布局触发的高清/低清切换，也不做同一通道主子双路并存。

## 接口为什么改用 DEFAULT

预览请求使用 `{"sscId":"内部摄像头ID","streamType":"DEFAULT"}`；省略 streamType 也按 DEFAULT 处理。它表示使用服务端/设备维护的通道档位，不代表 Java 已经把摄像头配置成 1080p。

旧 MAIN 请求保留兼容，仍按通道默认流处理，不能理解为保证物理主码流。SUB 明确返回 501，不静默换成另一档。单/四屏都发送 DEFAULT，业务请求不调用 WVP 的通道档位更新接口。

返回 `streamSelection:"CHANNEL_DEFAULT"`、`actualStreamType:"UNKNOWN"`。没有真实设备确认时不把 SDP 参数或播放成功当作实际编码档位已确认。1080p 是分辨率；实际码率、帧率、延迟和规模容量另行测量。真实设备的编码参数仍由安装/平台配置落实。

## 本轮真正复用了什么

Maven 依赖 `com.jpmsoft.jpm:common:0.0.1`。现有 [ApiResponse](../src/main/java/com/jpmsoft/videoaccess/api/ApiResponse.java) 继承公司的 BaseResponse，复用 statusCode/success/message/serverErrorMsg/data/total，仅增加视频模块的 errorCode/mode 和“已受理”语义。

本机已有 common 源码及 Maven 缓存，不需要重新克隆一份库。当前没有拉取远端最新代码；公司若指定版本/分支，应再对齐，不能用本地 master 的名称证明它是远端最新。

为了复用返回契约而不引入额外运行能力，本模块排除 common 的传递依赖，继续由自身 Boot 基线管理 Spring/Jackson 等版本。打包检查确认没有因此加入 RabbitMQ、Groovy、OSHI/JNA；没有修改 common 工程本身或把工具类复制到本模块。以后使用其他工具时，要再核对其依赖。

| 已有内容 | 当前取舍 |
| --- | --- |
| BaseResponse | 已复用；保留当前 HTTP 4xx/5xx 与模块错误码，不把错误一律返回 HTTP 200 |
| SmartSiteCameraModel | 后续公司目录/字段对接参考；含 key/secret 等字段，不直接作为视频前端响应 |
| GenerateUUID | 32 位业务 ID 工具；当前观看/回放/租约票保持原有 36 位 UUID 契约 |
| JacksonUtils | 含忽略未知字段等宽松设置；不整体替换视频接口/上游响应的严格校验 |
| DateUtils 等业务工具 | 有明确转换需求时再适配；录像协议时间继续使用带时区的 java.time |

新环境构建时，需要公司对应版本的 common 已在 Maven 仓库中：可由公司私服提供，或先在现有 common 工程用 JDK8 执行 Maven install。当前机器已缓存，不需要用户再去 GitLab 补拉其他仓库，也不使用 systemPath 硬绑一个本机 JAR。

## 在 IDEA 中看哪里

1. 看 pom.xml 中 common 依赖及 exclusions。
2. 从 ApiResponse 的 `extends BaseResponse` 跳到现有 common 源码，比较字段。
3. 看 [CommonResponseContractTest](../src/test/java/com/jpmsoft/videoaccess/CommonResponseContractTest.java)，理解复用后为什么要验证 JSON 字段、HTTP 错误和内部错误信息不外泄。
4. 看 VideoRequests.Preview、WvpVideoGateway.preview，理解 DEFAULT 和旧 MAIN 兼容的边界。

本地模拟器已能通过真实 SIP 标识输出不同分辨率；它验证信令和媒体处理，不代表海康具体型号接受这些参数。实验只能在隔离环境无人观看时顺序执行，结束恢复原配置；这不是生产中的在线切流功能。
