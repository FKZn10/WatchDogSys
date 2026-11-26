# 本机导入

在 Navicat 用自己的 MySQL 8.4 管理员账号连接本机，创建空数据库 `vue_antd_admin`。在该数据库下依次运行 `schema.sql`、`seed.sql`、`auth-init.sql`。旧版项目表的列较多，建表脚本会临时调整当前连接的 InnoDB 严格模式，建表后恢复。

业务数据包含 7 个项目、6 个点位、11 条公司记录和 8 条部门记录。`auth-init.sql` 会补建登录、权限和查询项目用的辅助表，并增加一个仅供本机使用的「监控实训组」及账号 `studio_monitor`。如果以前已经导入了四张业务表，从 `auth-init.sql` 开始即可。

在仓库根目录运行以下命令。先输入本机 MySQL 的 `jpm_local` 密码，再设置网页账号 `studio_monitor` 的密码。网页密码至少 10 个字符，设置完成后用它登录网页。

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\Set-StudioMonitorPassword.ps1
```

如果提示找不到 `mysql.exe`，把 MySQL 安装目录的 `bin` 加入 PATH，或给脚本传入 `-MysqlExe` 和客户端的完整路径。脚本默认连接 `127.0.0.1:13306`。

需要接入已授权的萤石设备时，在同一数据库运行单独交接的 `private/camera.local.sql`。它包含真机参数，按私下渠道交接。也可以用 `camera.example.sql` 填入自己有权限的设备参数。

导入并启动后，可用 `studio_monitor` 验收登录和项目列表。该账号可查看本机实训库里的项目，网页登录仍需要验证码和 Redis。
