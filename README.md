# Mybatis-Study

本[Mybatis课堂记录](docs/Mybatis课堂记录.md)根据 b站视频公开视频
[【狂神说Java】Mybatis最新完整教程IDEA版通俗易懂](https://www.bilibili.com/video/BV1NE411Q7Nx)
边学边做的笔记，有部分为自己的理解。

如有不同，一切以视频为准


# 项目使用指南

查看笔记[点击此处](docs/Mybatis课堂记录.md)

## 使用 mybatis-01，mybatis-02 说明

缺少`mybatis-config.xml`文件


下载使用需要重命名 `mybatis-config.default.xml` 文件为 `mybatis-config.xml` ， 同时将里面的参数改成自己的数据库连接

设置连接时建议增加 `useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8` 参数


## 使用 mybatis-03，mybatis-04，mybatis-05 说明

缺少`db.properties`文件

需要添加 `db.properties` 文件，内容如下
```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8
username=root
password=123456
```

