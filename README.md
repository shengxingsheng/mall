# mall



## 技术选型

| 技术                | 版本       | 说明             |
| ------------------- | ---------- | ---------------- |
| SpringBoot          | 2.7.7      | 容器+MVC框架     |
| SpringCloud         | 2021.0.4   |                  |
| SpringCloudAalibaba | 2021.0.4.0 |                  |
| MyBatis-Plus        | 3.5.3.1    | MyBatis增强工具  |
| Nacos               | 2.0.4      | 数据层代码生成器 |
| Swagger-UI          | 3.0.0      | 文档生产工具     |
| Redis               | 5.0        | 分布式缓存       |
| Docker              | 18.09.0    | 应用容器引擎     |
| Druid               | 1.2.9      | 数据库连接池     |
| Hutool              | 5.8.0      | Java工具类库     |
| JWT                 | 0.9.1      | JWT登录支持      |
| Lombok              | 1.18.24    | 简化对象封装工具 |

## 项目结构

```
mall
├── mall-common -- 工具类及通用代码
├── mall-doc -- swagger接口文档
├── mall-service -- 业务模块
├── mall-service-api -- feien接口,实体类
└── renren-fast -- 后台管理项目
```

## 规范

### nacos配置中心

| namespace | 隔离开发环境           |
| --------- | ---------------------- |
| **group** | **隔离不同服务的配置** |
|           |                        |
|           |                        |

```
namespace: 
```

