### Mybatis Plus 入门
####一、创建并初始化数据库
**1、创建数据库**
mybatis_plus

**2、创建 User 表** 
其表结构如下：
id	name	age	email
1	Jone	18	test1@baomidou.com
2	Jack	20	test2@baomidou.com
3	Tom	28	test3@baomidou.com
4	Sandy	21	test4@baomidou.com
5	Billie	24	test5@baomidou.com
其对应的数据库 Schema 脚本如下：
```angular2
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```
其对应的数据库 Data 脚本如下：
```angular2
DELETE FROM user;
INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```
####二、初始化工程

####三、添加依赖
**1、引入依赖**
spring-boot-starter、spring-boot-starter-test
添加：mybatis-plus-boot-starter、MySQL、lombok、
在项目中使用Lombok可以减少很多重复代码的书写。比如说getter/setter/toString等方法的编写
```angular2
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>

        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!--lombok用来简化实体类-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
```
注意：引入 MyBatis-Plus 之后请不要再次引入 MyBatis 以及 MyBatis-Spring，以避免因版本差异导致的问题。
**2.idea中安装lombok插件**
####四、配置
在 application.properties 配置文件中添加 MySQL 数据库的相关配置
mysql5
```
#mysql数据库连接
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_plus
spring.datasource.username=root
spring.datasource.password=123456
```
mysql8以上（spring boot 2.1）
注意：driver和url的变化
```angular2
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=123456
```
注意：
1、这里的 url 使用了 ?serverTimezone=GMT%2B8 后缀，因为Spring Boot 2.1 集成了 8.0版本的jdbc驱动，这个版本的 jdbc 驱动需要添加这个后缀，否则运行测试用例报告如下错误：
java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized or represents more 
2、这里的 driver-class-name 使用了  com.mysql.cj.jdbc.Driver ，在 jdbc 8 中 建议使用这个驱动，之前的 com.mysql.jdbc.Driver 已经被废弃，否则运行测试用例的时候会有 WARN 信息
####五、编写代码
**1、主类**
在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹
@SpringBootApplication
@MapperScan("com.atguigu.mybatisplus.mapper")
public class MybatisPlusApplication {
    ......
}
**2、实体**
创建包 entity 编写实体类 User.java（此处使用了 Lombok 简化代码）
**3.mapper**
创建包 mapper 编写Mapper 接口： UserMapper.java
####六、开始使用
添加测试类，进行功能测试：
```
@SpringBootTest
public class MpTest {

  @Autowired
  private UserMapper userMapper;

  //查询user表所有数据
  @Test
  public void findAll() {
    List<User> users = userMapper.selectList(null);
    System.out.println(users);
  }
}
```
注意：
IDEA在 userMapper 处报错，因为找不到注入的对象，因为类是动态创建的，但是程序可以正确的执行。
为了避免报错，可以在 dao 层 的接口上添加 @Repository 注
控制台输出：
```angular2
User(id=1, name=Jone, age=18, email=test1@baomidou.com), 
User(id=2, name=Jack, age=20, email=test2@baomidou.com), 
User(id=3, name=Tom, age=28, email=test3@baomidou.com), 
User(id=4, name=Sandy, age=21, email=test4@baomidou.com), 
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
```
通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！
####七、配置日志
查看sql输出日志
```
#mybatis日志
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```
###CRUD操作
####一、insert
**1.插入操作**
```
  @Test
  public void addUser() {
    User user = new User();
    user.setName("xxx");
    user.setAge(70);
    user.setEmail("xx@qq.com");
    int insert = userMapper.insert(user);
  }
```
**2.主键策略**
（1）ID_WORKER
MyBatis-Plus默认的主键策略是：ID_WORKER  全局唯一ID
参考资料：分布式系统唯一ID生成方案汇总：https://www.cnblogs.com/haoxinyue/p/5208136.html

（2）自增策略
要想主键自增需要配置如下主键策略
需要在创建数据表的时候设置主键自增
实体字段中配置 @TableId(type = IdType.AUTO)
```
@TableId(type = IdType.AUTO)
private Long id;
```
要想影响所有实体的配置，可以设置全局主键配置
```
#全局设置主键生成策略
mybatis-plus.global-config.db-config.id-type=auto
```
其它主键策略：分析 IdType 源码可知
```
@Getter
public enum IdType {
    /**
     * 数据库ID自增
     */
    AUTO(0),
    /**
     * 该类型为未设置主键类型
     */
    NONE(1),
    /**
     * 用户输入ID
     * 该类型可以通过自己注册自动填充插件进行填充
     */
    INPUT(2),
    /* 以下3种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 全局唯一ID (idWorker)
     */
    ID_WORKER(3),
    /**
     * 全局唯一ID (UUID)
     */
    UUID(4),
    /**
     * 字符串全局唯一ID (idWorker 的字符串表示)
     */
    ID_WORKER_STR(5);
    private int key;
    IdType(int key) {
        this.key = key;
    }
}
```
####update
**1、根据Id更新操作**
注意：update时生成的sql自动是动态sql：UPDATE user SET age=? WHERE id=?
```
  @Test
  public void updateById() {
    User user = new User();
    user.setId(1L);
    user.setAge(28);
    userMapper.updateById(user);
  }
```
**2、自动填充**
项目中经常会遇到一些数据，每次都使用相同的方式填充，例如记录的创建时间，更新时间等。
我们可以使用MyBatis Plus的自动填充功能，完成这些字段的赋值工作：

（1）数据库表中添加自动填充字段
在User表中添加datetime类型的新的字段 create_time、update_time

（2）实体上添加注解
@Data
public class User {
  ......
    
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
}
（3）实现元对象处理器接口
注意：不要忘记添加 @Component 注解

（4）测试
**3、乐观锁**
主要适用场景：当要更新一条记录的时候，希望这条记录没有被别人更新，也就是说实现线程安全的数据更新
乐观锁实现方式：

取出记录时，获取当前version
更新时，带上这个version
执行更新时， set version = newVersion where version = oldVersion
如果version不对，就更新失败

（1）数据库中添加version字段
```
ALTER TABLE `user` ADD COLUMN `version` INT
```
（2）实体类添加version字段
并添加 @Version 注解
```
@Version
@TableField(fill = FieldFill.INSERT)
private Integer version;
```
（3）元对象处理器接口添加version的insert默认值
```
@Override
public void insertFill(MetaObject metaObject) {
    ......
    this.setFieldValByName("version", 1, metaObject);
}
```
特别说明:

支持的数据类型只有 int,Integer,long,Long,Date,Timestamp,LocalDateTime
整数类型下 newVersion = oldVersion + 1
newVersion 会回写到 entity 中
仅支持 updateById(id) 与 update(entity, wrapper) 方法
在 update(entity, wrapper) 方法下, wrapper 不能复用!!!

（4）在 MybatisPlusConfig 中注册 Bean
创建配置类
```
@Configuration
@MapperScan("com.bai.mapper")
public class MybatisPlusConfig {
  /**
   * 乐观锁插件
   */
  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }
}
```
（5）测试乐观锁可以修改成功
测试后分析打印的sql语句，将version的数值进行了加1操作
```
//测试乐观锁
@Test
public void testOptimisticLocker() {
//根据id查询数据
User user = userMapper.selectById(1L);
//进行修改
user.setAge(200);
user.setEmail("helen@qq.com");
userMapper.updateById(user);
}
```
####三、select
**1、根据id查询记录**
```
  @Test
  public void testSelectById() {
    User user = userMapper.selectById(1L);
  }
```
**2、通过多个id批量查询**
完成了动态sql的foreach的功能
```
  //多个id批量查询
  @Test
  public void testSelectBatchIds() {
    List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
    System.out.println(users);
  }
```
**3、简单的条件查询（几乎用不到）**
通过map封装查询条件
```
  @Test
  public void testSelectByMap(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "Jone");
    map.put("age", 18);
    List<User> users = userMapper.selectByMap(map);
    users.forEach(System.out::println);
  }
```
注意：map中的key对应的是数据库中的列名。例如数据库user_id，实体类是userId，这时map的key需要填写user_id
**4、分页**
MyBatis Plus自带分页插件，只要简单的配置即可实现分页功能
（1）创建配置类
此时可以删除主类中的 @MapperScan 扫描注解
```
  /**
   * 分页插件
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }
```
（2）测试selectPage分页
测试：最终通过page对象获取相关数据
```
  //分页查询
  @Test
  public void testPage() {
    //1 创建page对象
    //传入两个参数：当前页 和 每页显示记录数
    Page<User> page = new Page<>(1,3);
    //调用mp分页查询的方法
    //调用mp分页查询过程中，底层封装
    //把分页所有数据封装到page对象里面
    userMapper.selectPage(page,null);

    //通过page对象获取分页数据
    System.out.println(page.getCurrent());//当前页
    System.out.println(page.getRecords());//每页数据list集合
    System.out.println(page.getSize());//每页显示记录数
    System.out.println(page.getTotal()); //总记录数
    System.out.println(page.getPages()); //总页数

    System.out.println(page.hasNext()); //下一页
    System.out.println(page.hasPrevious()); //上一页
  }
```
控制台sql语句打印：SELECT id,name,age,email,create_time,update_time FROM user LIMIT 0,3 
####四、delete
**1、根据id删除记录**
```
@Test
public void testDeleteById(){
    int result = userMapper.deleteById(8L);
    System.out.println(result);
}
```
**2、批量删除**
```
  @Test
  public void testDeleteBatchIds() {
    int result = userMapper.deleteBatchIds(Arrays.asList(1L,2L));
    System.out.println(result);
  }
```
**3、简单的条件查询删除**
```
@Test
public void testDeleteByMap() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "Helen");
    map.put("age", 18);
    int result = userMapper.deleteByMap(map);
    System.out.println(result);
}
```
**4、逻辑删除**
物理删除：真实删除，将对应数据从数据库中删除，之后查询不到此条被删除数据
逻辑删除：假删除，将对应数据中代表是否被删除字段状态修改为“被删除状态”，之后在数据库中仍旧能看到此条数据记录

（1）数据库中添加 deleted字段
```
ALTER TABLE `user` ADD COLUMN `deleted` boolean
```
（2）实体类添加deleted 字段
并加上 @TableLogic 注解 和 @TableField(fill = FieldFill.INSERT) 注解
```
@TableLogic
@TableField(fill = FieldFill.INSERT)
private Integer deleted;
```
（3）元对象处理器接口添加deleted的insert默认值
```
@Override
public void insertFill(MetaObject metaObject) {
    ......
    this.setFieldValByName("deleted", 0, metaObject);
}
```
（4）application.properties 加入配置
此为默认值，如果你的默认值和mp默认的一样,该配置可无
```
mybatis-plus.global-config.db-config.logic-delete-value=1
mybatis-plus.global-config.db-config.logic-not-delete-value=0
```
（5）在 MybatisPlusConfig 中注册 Bean
```
  @Bean
  public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
  }
```
（6）测试逻辑删除
测试后发现，数据并没有被删除，deleted字段的值由0变成了1
测试后分析打印的sql语句，是一条update
注意：被删除数据的deleted 字段的值必须是 0，才能被选取出来执行逻辑删除的操作
```
  @Test
  public void testLogicDelete(){
    int result = userMapper.deleteById(1L);
    System.out.println(result);
  }
```
**（7）测试逻辑删除后的查询**
MyBatis Plus中查询操作也会自动添加逻辑删除字段的判断
```
/**
 * 测试 逻辑删除后的查询：
 * 不包括被逻辑删除的记录
 */
@Test
public void testLogicDeleteSelect() {
    User user = new User();
    List<User> users = userMapper.selectList(null);
    users.forEach(System.out::println);
}
```
测试后分析打印的sql语句，包含 WHERE deleted=0 
SELECT id,name,age,email,create_time,update_time,deleted FROM user WHERE deleted=0
####五、性能分析
性能分析拦截器，用于输出每条 SQL 语句及其执行时间
SQL 性能执行分析,开发环境使用，超过指定时间，停止运行。有助于发现问题
**1、配置插件**
（1）参数说明
参数：maxTime： SQL 执行最大时长，超过自动停止运行，有助于发现问题。
参数：format： SQL是否格式化，默认false。
（2）在 MybatisPlusConfig 中配置
```
  /**
   * SQL 执行性能分析插件
   * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
   *
   * 三种环境
   *      * dev：开发环境
   *      * test：测试环境
   *      * prod：生产环境
   */
  @Bean
  @Profile({"dev","test"})// 设置 dev test 环境开启
  public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    performanceInterceptor.setMaxTime(500);//ms，超过此处设置的ms则sql不执行
    performanceInterceptor.setFormat(true);
    return performanceInterceptor;
  }
```
（3）Spring Boot 中设置dev环境
```
#环境设置：dev、test、prod
spring.profiles.active=dev
```
可以针对各环境新建不同的配置文件application-dev.properties、application-test.properties、application-prod.properties
也可以自定义环境名称：如test1、test2
**2、测试**
（1）常规测试

####六、其它
如果想进行复杂条件查询，那么需要使用条件构造器 Wapper，涉及到如下方法
1、delete
2、selectOne
3、selectCount
4、selectList
5、selectMaps
6、selectObjs
7、update




