# IM聊天室
一个基于Netty的聊天工具（开发中），使用Zookeeper+Redis实现服务端分布式部署。

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