### Spring Boot HelloWorld
功能描述：
浏览器发送hello请求，服务器接收请求并处理，响应Hello World字符串；
**1.创建一个maven工程**
**2.导入spring boot相关的依赖**
```angular2
 <!-- Inherit defaults from Spring Boot -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.1.RELEASE</version>
    </parent>

    <!-- Add typical dependencies for a web application -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <!-- Package as an executable jar -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```
**3.编写主程序：启动spring boot应用**
主程序类放在groupId包下
```angular2
/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个spring boot应用
 */
@SpringBootApplication
public class HelloworldApplication {
  public static void main(String[] args) {
    //spring应用启动起来
    SpringApplication.run(HelloworldApplication.class, args);
  }
}
```
**4.编写相关的controller，service**
```angular2
@RestController
public class HelloController {
  @RequestMapping("hello")
  public String hello() {
    return "Hello World";
  }
}
```
**5.运行主程序测试**
**6.简化部署**
```angular2
  <!--这个插件，可以将应用打包成一个可执行的jar包-->
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
```
将这个应用打包成jar包，直接使用java -jar命令进行执行