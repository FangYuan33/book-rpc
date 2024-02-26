## [《Java 开发者的RPC实战课》](https://juejin.cn/book/7047357110337667076)

### 1. RPC 框架整体设计

![img.png](images/img.png)

- Proxy: 代理模式，实现调用者和服务提供者（provider）的解耦，无需关注其中对参数的封装、路由的实现
- Router: 定义规则去匹配 provider 服务，由注册中心来辅助
- Protocol: 将请求数据按照协议规则进行封装，序列化完成后发送给 provider
- FilterChain: 可插拔组件
- Tolerant: 容错机制

---

第二章介绍了 RPC 的概念

区分本地调用和RPC调用