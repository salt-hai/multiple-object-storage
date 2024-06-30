## 简介

**Object-Storage** 提供方便快捷的对象存储操作

**目前提供的功能模块列表**

```
|-- multiple-object-storage-dependencies   -- 项目本身各子模块的依赖管理，以及第三方模块的依赖管理
```

## 使用方法

***依赖说明***

项目相应依赖暂未推送中央仓库,需要自行克隆代码,本地编译

***聚合使用***

聚合使用可以支持多个不同的云服务商进行切换,按照项目导入的依赖或导入多个实现依赖后指定想要的服务商,系统会更具配置文件或依赖项目进行自动配置

首先项目pom文件引入:

引入bom依赖进行管理,spring-boot 3.0 以下使用版本号3.0以下的最新版本即可

``` xml
<dependencyManagement>
     <dependencies>
        <dependency>
            <groupId>salthai.top</groupId>
            <artifactId>multiple-object-storage-dependencies</artifactId>
            <version>0.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

依赖坐标

``` xml
    <!--spring-boot 启动依赖-->
        <dependency>
            <groupId>salthai.top</groupId>
            <artifactId>multiple-object-storage-spring-boot-starter</artifactId>
        </dependency>
        <!--供应商实现-->
        <!--oss-->
        <dependency>
            <groupId>salthai.top</groupId>
            <artifactId>multiple-object-storage-provider-aliyun-sdk</artifactId>
        </dependency>
        <!--obs-->
        <dependency>
            <groupId>salthai.top</groupId>
            <artifactId>multiple-object-storage-provider-huawei-sdk</artifactId>
        </dependency>
        <!--bos-->
        <dependency>
            <groupId>salthai.top</groupId>
            <artifactId>multiple-object-storage-provider-baidu-sdk</artifactId>
        </dependency>
```

配置文件

```yaml
multiple:
  object:
    storage:
      pool:
        # 对象池管理供应商客户端,是否启用,如果 “commons-pool2” 可用，则自动启用。
        enabled: true
        max-idle: 8
        min-idle: 0
        max-active: 8
        max-wait: -1ms
        time-between-eviction-runs: -1ms
      #  该配置是否启用该项目
      enable: true
      # 多个依赖时指定使用的供应商,只有单个供应商实现依赖不需要指定
      provider: baidu
      huawei:
        secret-key: obs-sk
        access-key: obs-ak
        endpoint: obs-接入点
      aliyun:
        secret-key: oss-sk
        access-key: oss-ak
        endpoint: oss-接入点
      baidu:
        secret-key: bos-ak
        access-key: bs-sk
        endpoint: bos-接入点
```

***单独使用某个服务商***

同样引入bom 依赖,以oss为例子

依赖坐标

``` xml
<dependency>
        <groupId>salthai.top</groupId>
        <artifactId>multiple-object-storage-oss-autoconfigure</artifactId>
</dependency>
```

配置文件

```yaml
multiple:
  object:
    aliyun:
      #  该配置是否启用该项目
      enable: true
      secret-key: oss-sk
      access-key: oss-ak
      endpoint: oss-接入点
```

