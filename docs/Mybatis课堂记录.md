# Mybatis-9.28.2019

[B站视频](https://www.bilibili.com/video/BV1NE411Q7Nx)

环境：

- JDK1.8
- Mysql 5.7
- maven 3.6.1
- IDEA

回顾：

- JDBC
- Mysql
- Java基础
- Maven
- Junit

框架：配置文件的。最好的方式：看官网文档；

# 1、简介

### 1.1、什么是 MyBatis？

![image-20211106125303600](Mybatis课堂记录.assets/image-20211106125303600.png)

- MyBatis 是一款优秀的**持久层框架**
- 它支持自定义 SQL、存储过程以及高级映射。
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
- MyBatis 本是apache的一个[开源项目](https://baike.baidu.com/item/开源项目/3406069)iBatis,
  2010年这个[项目](https://baike.baidu.com/item/项目/477803)由apache software foundation
  迁移到了[google code](https://baike.baidu.com/item/google code/2346604)，并且改名为MyBatis 。
- 2013年11月迁移到[Github](https://baike.baidu.com/item/Github/10145341)。

如何获得Mybatis？

- maven仓库

  ```java
  <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.2</version>
  </dependency>
  ```


- GitHub：https://github.com/mybatis/mybatis-3/releases

- 中文文档： https://mybatis.org/mybatis-3/zh/index.html

### 1.2、持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：**断电即失**
- 数据库(jdbc)，io文件持久化。
- 生活：冷藏.罐头。

**为什么需要需要持久化？**

- 有一些对象，不能让他丢掉。
- 内存太贵了

### 1.3、持久层

Dao层，Service层，Controller层.…

* 完成持久化工作的代码块
* 层界限十分明显。

### 1.4 为什么需要Mybatis？

- 帮助程序猿将数据存入到数据库中。
- 方便
- 传统JDBC代码太复杂了。简化。框架。自动化。
- 不用Mybatis也可以。更容易上手。技术没有高低之分
- 优点：
    - 简单易学
    - 灵活
    - sql和代码的分离，提高了可维护性。
    - 提供映射标签，支持对象与数据库的orm字段关系映射
    - 提供对象关系映射标签，支持对象关系组建维护
    - 提供xml标签，支持编写动态sql。

**最重要的一点：使用的人多！**

# 2、第一个Mybatis程序

### 2.1、搭建环境

```mysql
CREATE DATABASE `mybatis`;

USE `mybatis`;

CREATE TABLE `user`
(
    `id`   INT(20) NOT NULL PRIMARY KEY,
    `name` VARCHAR(30) DEFAULT NULL,
    `pwd`  VARCHAR(30) DEFAULT NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

INSERT INTO `user` (`id`, `name`, `pwd`)
VALUES (1, '狂神', '123456'),
       (2, '张三', '123456'),
       (3, '李四', '123486')
;
```

### 2.2、创建一个模块

- 编写mybatis的核心配置文件，mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```

- 编写mybatis工具类

```java
//工具类 sqlSessionFactory --> sqlSession
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //使用Mybatis第一步：获取sqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
    // SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。例如：
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
```

### 2.3、编写代码

- 实体类

  ```java
  public class User {
      private int id;
      private String name;
      private String pwd;
      
      public User(){
      }
      public User(int id, String name, String pwd) {
          this.id = id;
          this.name = name;
          this.pwd = pwd;
      }
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public String getPwd() {
          return pwd;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  
      @Override
      public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", pwd='" + pwd + '\'' +
                  '}';
      }
  }
  ```

- Dao接口

  ```java
  public interface UserDao {
      List<User> getUserList();
  }
  ```


- 接口实现类由原来的UserDaoImpl转变为一个Mapper配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!--namespace=绑定一个对应的Dao/Mapper接口-->
  <mapper namespace="com.kuang.dao.UserDao">
      <!--select查询语句，resultType写全名-->
      <select id="getUserList" resultType="com.kuang.pojo.User">
          select * from mybatis.user
      </select>
  </mapper>
  ```

### 2.4、测试

注意点：

org.apache.ibatis.binding.BindingException: Type interface com.kuang.dao.UserDao is not known to the MapperRegistry.

MapperRegistry未注册

Could not find resource com/kaung/dao/UserMapper.xml

在build中配置resources，来防止我们资源导出失败的问题

```xml

<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```

**MapperRegistry是什么？**

核心配置文件中注册mappers

- Junit测试

  ```java
  @Test
  public void test() {
      //第一步：获取SqlSession对象
      SqlSession sqlSession = MybatisUtils.getSqlSession();
      //执行SQL
      UserDao userDao = sqlSession.getMapper(UserDao.class);
      List<User> userList = userDao.getUserList();
  
      for (User user : userList) {
          System.out.println(user);
      }
      //关闭SqlSession
      sqlSession.close();
  }
  ```

你们可以能会遇到的问题：

1. 配置文件没有注册
2. 绑定接口错误。
3. 方法名不对
4. 返回类型不对
5. Maven导出资源问题

# 3、CURD

### 1、namespace

命名空间

namespace中的包名要和 Dao/mapper 接口的包名一致！

### 2、select

选择，查询语句；

- id：就是对应的namespace中的方法名；
- resultType：Sql语句执行的返回值！
- parameterType：参数类型！



1. 编写接口

   ```java
   //根据ID查询用户
   User getUserById(int id);
   ```

2. 编写对应的mapper中的sql语句

   ```xml
   <select id="getUserById" parameterType="int" resultType="com.kuang.pojo.User">
           select * from mybatis.user where id = #{id}
   </select>
   ```

3. 测试

   ```java
   @Test
   public void getUserById() {
       SqlSession sqlSession = MybatisUtils.getSqlSession();
       UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
       User userById = userMapper.getUserById(1);
       System.out.println(userById);
       sqlSession.close();
   }
   ```

   

### 3、Insert

```xml
<!--对象中的属性，可以直接取出来-->
<insert id="addUser" parameterType="com.kuang.pojo.User">
    insert into mybatis.user (id, name, pwd)
    values (#{id}, #{name}, #{pwd})
</insert>
```

### 4、Update

```xml
<update id="updateUser" parameterType="com.kuang.pojo.User">
    update mybatis.user
    set name=#{name },
    pwd=#{pwd}
    where id = #{id};
</update>
```

### 5、Delete

```xml
<delete id="deleteUser" parameterType="int">
    delete
    from mybatis.user
    where id = #{id};
</delete>
```

注意点：

- 增删改查需要提交事务

### 6、分析错误

- 标签不要匹配错
- resource绑定mapper，需要使用路径
- 程序配置文件必须符合规范！
- NullPointerException，没有注册到资源
- 输出的xml文件中存在中文乱码问题！
- maven资源没有导出问题

### 7、万能Map

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们应当考虑使用Map！

```java
//万能的Map
int addUser2(Map<String, Object> map);
```

```xml
<!--对象中的属性，可以直接取出来  传递map的key-->
<insert id="addUser2" parameterType="map">
    insert into mybatis.user (id, name, pwd)
    values (#{userId}, #{userName}, #{passWord});
</insert>
```

```java
@Test
public void addUser2() {
    SqlSession sqlSession = MybatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    HashMap<String, Object> map = new HashMap<>();
    map.put("userId", 6);
    map.put("userName", "Hello");
    map.put("passWord", "222333");

    int res = mapper.addUser2(map);
    if (res > 0) {
        System.out.println("插入成功！");
    }

    sqlSession.commit();
    sqlSession.close();
}
```

Map传递参数，直接在sql中取出key即可！【parameterType="map"】

对象传递参数，直接在sql中取对象的属性即可！【parameterType="Object"】

只有一个基本类型参数的情况下，可以直接在sql中取到！【直接省略】

```xml
<select id="getUserById" resultType="com.kuang.pojo.User">
    select * from mybatis.user where id = #{id};
</select>
```



多个参数用Map，**或者注解！**

### 8、思考题

模糊查询怎么写？



1. Java代码执行的时候，传递通配符%%

   ```java
   List<User> updateUserLike = userMapper.getUserLike("%李%");
   ```

2. 在sql拼接中使用通配符！

    ```sql
    select * from mybatis.user where name like "%"#{value}"%";
    ```



# 4、配置解析

### 1、核心配置文件

mybatis-config.xml

MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。

```xml
configuration（配置）
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）
```





















