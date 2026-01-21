# 对象存储集成测试指南

本文档说明如何运行对象存储项目的集成测试。

## 概述

本项目使用 Testcontainers 和 JUnit 5 进行集成测试，支持真实的云存储服务测试。

## 测试架构

测试参考了 Spring Data Redis 的测试风格，具有以下特点：

- **Testcontainers 集成**: 使用 Docker 容器模拟云服务
- **隔离性**: 每个测试使用独立的对象前缀，避免冲突
- **自动清理**: 测试完成后自动清理创建的对象
- **可配置性**: 支持通过配置文件或环境变量配置

## 支持的云存储提供商

- ✅ AWS S3 (使用 LocalStack)
- ✅ 阿里云 OSS
- ✅ 华为云 OBS
- ✅ 百度云 BOS

## 前置要求

### 必需

- Java 8 或更高版本
- Maven 3.6+
- Docker (用于 Testcontainers)

### 可选

- 真实的云服务账号（用于生产环境测试）

## 快速开始

### 1. 配置测试环境

编辑 `provider/multiple-object-storage-provider-amazon-sdk/src/test/resources/application-test.properties`:

```properties
# AWS S3 配置 (用于真实服务测试)
s3.bucket.name=your-test-bucket
s3.region=us-east-1
s3.endpoint=https://s3.amazonaws.com
s3.access.key=your-access-key-id
s3.secret.key=your-secret-access-key

# 阿里云 OSS 配置
aliyun.bucket.name=your-test-bucket
aliyun.region=oss-cn-hangzhou
aliyun.endpoint=https://oss-cn-hangzhou.aliyuncs.com
aliyun.access.key=your-access-key-id
aliyun.secret.key=your-secret-access-key

# 华为云 OBS 配置
huawei.bucket.name=your-test-bucket
huawei.endpoint=https://obs.cn-north-4.myhuaweicloud.com
huawei.access.key=your-access-key-id
huawei.secret.key=your-secret-access-key

# 百度云 BOS 配置
baidu.bucket.name=your-test-bucket
baidu.endpoint=https://bj.bcebos.com
baidu.access.key=your-access-key-id
baidu.secret.key=your-secret-access-key
```

### 2. 运行测试

```bash
# 运行所有测试
mvn test

# 仅运行集成测试
mvn test -Dtest=*IntegrationTest

# 运行特定提供商的测试
mvn test -Dtest=S3ObjectOperationsIntegrationTest
```

### 3. 使用环境变量

```bash
# 设置环境变量
export TEST_INTEGRATION_ENABLED=true
export TEST_CLEANUP_ENABLED=true

# 运行测试
mvn test
```

## 测试场景

### 基础对象操作

- ✅ 上传对象 (Put Object)
- ✅ 下载对象 (Get Object)
- ✅ 删除对象 (Delete Object)
- ✅ 检查对象存在 (Does Object Exist)
- ✅ 获取对象元数据 (Get Object Metadata)
- ✅ 列举对象 (List Objects)

### 高级功能

- ✅ 生成预签名 URL (Generate Presigned URL)
- ✅ 创建文件夹 (Create Directory)
- ✅ 上传大文件 (Upload Large File)
- ✅ 中文文件名支持 (Chinese Filename Support)

### 边界情况

- ✅ 空文件
- ✅ 大文件 (5MB+)
- ✅ 特殊字符文件名
- ✅ 并发操作

## 测试模式

### 1. LocalStack 模式 (默认)

使用 Docker 容器运行 LocalStack，无需真实云服务账号：

```bash
mvn test
```

优点：
- 无需云服务账号
- 测试快速
- 完全隔离

### 2. 真实服务模式

使用真实云服务进行测试：

```properties
# application-test.properties
test.integration.enabled=true
```

然后配置你的云服务凭证。

## 测试最佳实践

### 1. 测试隔离

每个测试使用唯一的对象前缀：

```java
String objectName = generateTestObjectName("test-file.txt");
```

### 2. 自动清理

测试完成后自动清理创建的对象：

```java
@AfterEach
void tearDown() {
    if (isIntegrationTestEnabled && cleanupEnabled) {
        cleanupTestObjects();
    }
}
```

### 3. 条件跳过

当集成测试未启用时跳过测试：

```java
@Test
void testSomeOperation() {
    skipIfIntegrationTestDisabled();
    // 测试代码
}
```

## 环境变量

| 变量 | 描述 | 默认值 |
|------|------|--------|
| `test.integration.enabled` | 是否启用集成测试 | `true` |
| `test.cleanup.enabled` | 是否在测试后清理 | `true` |
| `test.bucket.prefix` | 测试桶名前缀 | `integration-test-` |
| `test.object.prefix` | 测试对象前缀 | `test-object-` |

## 故障排查

### Docker 相关问题

```bash
# 检查 Docker 是否运行
docker ps

# 检查 Testcontainers 是否可用
docker run hello-world
```

### 权限问题

确保云服务账号具有以下权限：

- `s3:PutObject`
- `s3:GetObject`
- `s3:DeleteObject`
- `s3:ListBucket`
- `s3:GetObjectMetadata`

### 网络问题

```bash
# 测试连接
curl https://s3.amazonaws.com

# 检查防火墙设置
```

## 持续集成

在 CI/CD 环境中运行测试：

```yaml
# GitHub Actions 示例
- name: Run Integration Tests
  run: mvn test -Dtest=*IntegrationTest
  env:
    TEST_INTEGRATION_ENABLED: true
```

## 贡献指南

添加新的集成测试：

1. 继承 `AbstractIntegrationTest`
2. 实现 `getObjectOperations()` 方法
3. 使用 `@Test` 和 `@DisplayName` 注解
4. 确保测试在完成后清理资源

## 参考资源

- [JUnit 5 文档](https://junit.org/junit5/docs/current/user-guide/)
- [Testcontainers 文档](https://www.testcontainers.org/)
- [Spring Data Redis 测试](https://github.com/spring-projects/spring-data-redis)

## 许可证

Apache License 2.0
