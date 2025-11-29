# Project（Spring Boot 3 / Java 17）

一个基于 Spring Boot 3 的后端项目。集成了 Redis、MySQL、MyBatis-Flex、Fastjson2、Hutool、MinIO、Apache POI、JWT、AOP、Caffeine 等常用组件，打包为可执行 `jar`。

## 功能概览
- Web API：`spring-boot-starter-web`
- 数据访问：MyBatis-Flex（简化 MyBatis 使用）
- 缓存与分布式锁：Redis（示例工具类如 `RedisUtil`）
- 数据库：MySQL（连接池 HikariCP）
- 序列化与工具：Fastjson2、Hutool、Apache Commons
- 文件与文档：MinIO 客户端、Apache POI（处理 Excel）
- 安全：JWT 支持、参数校验、AOP 拦截
- 本地缓存：Caffeine

## 环境要求
- `JDK 17`
- `Maven 3.8+`
- 运行中的 `MySQL` 与 `Redis` 服务

## 快速开始
1. 克隆并进入项目根目录。
2. 配置应用：编辑 `src/main/resources/application.yml`（或 `application.properties`），至少设置以下内容：
   - 数据源：`spring.datasource.*`
   - Redis：`spring.data.redis.*`
   - JWT：如 `jwt.secret`、`jwt.expire`（若有）
   - MinIO：`minio.endpoint`、`minio.access-key`、`minio.secret-key`（若有）
3. 构建可执行包：
   - `mvn clean package -DskipTests`
4. 运行：
   - `java -jar target/project.jar`
   - 或开发模式：`mvn spring-boot:run`

## 常用命令
- 清理与构建：`mvn clean package`
- 仅测试：`mvn test`
- 跳过测试构建：`mvn clean package -DskipTests`
- 代码格式或检查（如有插件）：参考 `pom.xml` 中的插件配置

## 配置说明（示例）
根据你的环境在 `application.yml` 中设置：
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_db?useSSL=false&useUnicode=true&characterEncoding=utf8
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379

# JWT 示例
jwt:
  secret: your_jwt_secret
  expire: 3600

# MinIO 示例
minio:
  endpoint: http://localhost:9000
  access-key: minioadmin
  secret-key: minioadmin
```

## 代码结构
```
project/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/                # 源码（主类：com.github.project.ProjectApplication）
│   │   └── resources/           # 配置、静态资源、mapper 等
│   └── test/
│       └── java/                # 单元与集成测试
```

## 开发建议
- 业务逻辑分层（controller/service/repository）清晰，避免循环依赖。
- 缓存与分布式锁的使用需谨慎，确保过期与释放逻辑正确（示例：`RedisUtil`）。
- 对外接口返回统一响应结构，异常与参数校验集中处理。

## 许可证
本项目采用 `MIT License`。详见根目录 `LICENSE` 文件。

提示：已提供中文译文 `LICENSE-zh_CN` 供阅读参考；若与英文原文存在不一致或歧义，均以英文原文为准。

## 致谢
- Spring Boot 团队与各开源组件维护者。
