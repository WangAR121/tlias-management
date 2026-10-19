# tlias 管理系统（Tlias Web Management）

基于 **Spring Boot 4 + MyBatis + MySQL + Vue 3** 的员工 / 学员管理系统：部门管理、员工管理（含工作经历）、
班级管理、学员管理、报表统计（ECharts）、文件上传（阿里云 OSS）、JWT 登录认证、操作日志与登录日志。

## 技术栈

| 分层 | 技术 |
| --- | --- |
| 后端 | Java 21、Spring Boot 4.1.1、MyBatis 4.0.0、PageHelper 1.4.7、JWT（jjwt 0.12.5）、Spring AOP、Lombok 1.18.48 |
| 数据库 | MySQL 8.0（utf8mb4） |
| 对象存储 | 阿里云 OSS（alibabacloud-oss-v2 0.6.0，可选功能） |
| 前端 | Vue 3、Element Plus、Vue Router、Axios、ECharts、Vite 3 |

## 目录结构

```
tlias-web-management/
├── src/main/java/heima/item/         后端源码
│   ├── anno/          @Log 操作日志注解
│   ├── aspect/        AOP 切面（操作日志）
│   ├── config/        Web 配置（拦截器注册）
│   ├── controller/    接口层
│   ├── exception/     全局异常处理
│   ├── filter/        TokenFilter（备用，当前未启用）
│   ├── interceptor/   tokenInterceptor（JWT 登录校验）
│   ├── mapper/        MyBatis Mapper
│   ├── pojo/          实体类
│   ├── service/       业务层
│   └── utils/         JWT、OSS、字符串截断工具
├── src/main/resources/
│   ├── application.yml      配置（敏感项均为环境变量占位）
│   └── heima/item/mapper/   MyBatis XML
├── db/tlias.sql              建库建表 + 可选演示数据
├── frontend-dist/            编译后的前端静态文件（Vue 源码未包含，见下）
├── deploy/nginx.conf.example nginx 配置示例
└── docs/                     环境准备 / 部署说明 / 操作说明
```

> 说明：`frontend-dist/` 是编译产物（可直接用 nginx 托管）。仓库**不含** Vue 源码工程根目录里的 `src/`
> 

## 快速开始

1. **建库建表**（MySQL 8）：
   ```bash
   mysql -uroot -p < db/tlias.sql
   ```
2. **配置环境变量**（不要写进代码）：
   ```bash
   DB_PASSWORD=你的数据库密码          # 必填
   DB_USERNAME=root                   # 可选，默认 root
   DB_HOST=localhost                  # 可选
   DB_PORT=3306                       # 可选
   DB_NAME=tlias                      # 可选
   ```
   需要"文件上传"功能时再配 OSS：`OSS_BUCKET_NAME`、`OSS_ACCESS_KEY_ID`、`OSS_ACCESS_KEY_SECRET`
   （可选 `OSS_ENDPOINT`、`OSS_REGION`）。
3. **启动后端**：
   ```bash
   mvn clean package -DskipTests
   java -jar target/tlias-web-management-0.0.1-SNAPSHOT.jar --server.port=8080
   ```
4. **启动前端**：把 `frontend-dist/` 放到 nginx 的 `html/`，用 `deploy/nginx.conf.example` 的配置
   （监听 90，`/api/` 反向代理到 8080），访问 <http://localhost:90>。
5. **登录**：演示账号 `admin / 123456`（由 `db/tlias.sql` 的演示数据创建；也可以用自己的 emp 表账号）。

更细的步骤见 [环境准备](docs/环境准备.md)、[部署说明](docs/部署说明.md)、[操作说明](docs/操作说明.md)。

## 主要功能

- **登录认证**：`POST /login` 校验账号密码，签发 JWT（有效期 2 小时）；`tokenInterceptor` 校验请求头
  `token`，未登录/令牌非法返回 401，前端自动跳回登录页。
- **部门管理**：列表 / 新增 / 修改 / 删除（部门下有员工时禁止删除）。
- **员工管理**：分页 + 姓名/性别/入职时间条件查询、新增（含多段工作经历）、修改、批量删除。
- **班级管理**：分页 + 名称/结课时间查询、新增、修改、删除（班级有学员时禁止删除）。
- **学员管理**：分页 + 条件查询、新增、修改、违纪扣分。
- **报表统计**：员工职位分布、员工性别分布、班级人数、学员学历分布（ECharts）。
- **文件上传**：上传头像/图片到阿里云 OSS，返回访问 URL。
- **操作日志**：controller 方法标注 `@Log` 后，AOP 切面记录操作人/时间/类名/方法名/参数/返回值/耗时到
  `operate_log`，分页查询接口 `GET /log/page`。
- **登录日志**：登录成功与失败都会写入 `emp_log`（谁、什么时间、成功还是失败）。

## 接口约定

- 统一响应体：`{ "code": 1, "msg": "success", "data": ... }`，`code=1` 成功、`0` 失败。
- 除 `POST /login` 外，所有接口都需要请求头 `token: <登录返回的 JWT>`。
- 通过 nginx 访问时前端统一走 `/api/` 前缀（nginx 会剥掉 `/api` 再转发到后端）。

- 数据库账号密码 → 环境变量 `DB_USERNAME` / `DB_PASSWORD`
- 阿里云 OSS 的 AccessKey / Bucket → 环境变量 `OSS_BUCKET_NAME` / `OSS_ACCESS_KEY_ID` / `OSS_ACCESS_KEY_SECRET`

请不要把真实密码提交上去；本地私密配置可放在 `application-local.yml。

## 注意：

- `dept.name` 字段是 `varchar(10)`，部门名称超过 10 个字符会报 `Data too long`，限制输入长度。
- 项目依赖 JDK 21+（`pom.xml` 里 `java.version=21`）。
