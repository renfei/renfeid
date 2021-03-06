# Leaf

> There are no two identical leaves in the world.
>
> 世界上没有两片完全相同的树叶。
>
> ​								— 莱布尼茨

## Introduction

Leaf 最早期需求是各个业务线的订单ID生成需求。在美团早期，有的业务直接通过DB自增的方式生成ID，有的业务通过redis缓存来生成ID，也有的业务直接用UUID这种方式来生成ID。以上的方式各自有各自的问题，因此我们决定实现一套分布式ID生成服务来满足需求。具体Leaf 设计文档见：[ leaf 美团分布式ID生成服务 ](https://tech.meituan.com/MT_Leaf.html )

目前Leaf覆盖了美团点评公司内部金融、餐饮、外卖、酒店旅游、猫眼电影等众多业务线。在4C8G VM基础上，通过公司RPC方式调用，QPS压测结果近5w/s，TP999 1ms。

## Quick Start

#### 配置介绍

Leaf 提供两种生成的ID的方式（号段模式和snowflake模式），你可以同时开启两种方式，也可以指定开启某种方式（默认两种方式为关闭状态）。

我采用的是 Snowflake模式。

#### Snowflake模式

算法取自twitter开源的snowflake算法。

如果不想使用该模式配置leaf.snowflake.enable=false即可。

##### 配置zookeeper地址

在leaf.properties中配置leaf.snowflake.zk.address，配置leaf 服务监听的端口leaf.snowflake.port。

### 注意事项
注意现在leaf使用snowflake模式的情况下 其获取ip的逻辑直接取首个网卡ip【特别对于会更换ip的服务要注意】避免浪费workId
