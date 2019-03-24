# IM聊天室
一个基于Netty的聊天工具（开发中），使用Zookeeper+Redis实现服务端分布式部署。

## TODO LIST
* [x] 客户端服务端连接
* [x] 客户端处理用户输入消息
* [x] 消息序列化（Protobuf）
* [x] 服务端注册
* [x] 路由端获取可用服务端节点
* [x] 消息群发功能
* [x] 客户端下线（强制下线/主动下线）
* [x] 服务端断线，客户端重连
* [ ] 心跳
* [ ] 消息重发
* [ ] 聊天记录
* [ ] 离线消息

## 运行
0.启动redis与Zookeeper

1.编译、打包
```
mvn -Dmaven.test.skip=true clean package
```
2.启动路由
```
java -jar im-route\target\im-route-0.0.1-SNAPSHOT.jar
```	
3.启动服务端
```
java -jar im-server\target\im-server-0.0.1-SNAPSHOT.jar --server.port=8084 --im.server.port=8090
java -jar im-server\target\im-server-0.0.1-SNAPSHOT.jar --server.port=8085 --im.server.port=8091
```	
4.启动客户端
```
java -jar im-client\target\im-client-0.0.1-SNAPSHOT.jar --server.port=8071 --im.user.id=1001 --im.user.userName=Five
java -jar im-client\target\im-client-0.0.1-SNAPSHOT.jar --server.port=8072 --im.user.id=1002 --im.user.userName=Luffy
```	