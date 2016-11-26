# Spring-MVC-Source-Code
Spring MVC Source Code and Practice
## 入门体验
1. [环境搭建](#hjdj)  
2. [简单的配置](#jddpz)
3. [部署运行](#bsyx)

### <span id = "hjdj">环境搭建</span>
新建maven项目，只需添加Spring MVC和Servlet的依赖就可以。  
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>${servlet.version}</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>${spring.version}</version>
</dependency>
```
非maven项目，把下面包加入WEB-INF/lib目录下即可：
* commons-logging:1.2
* javax.servlet-api:3.1.0
* spring-aop:4.3.4.RELEASE
* spring-beans:4.3.4.RELEASE
* spring-context:4.3.4.RELEASE
* spring-core:4.3.4.RELEASE
* spring-expression:4.3.4.RELEASE
* spring-web:4.3.4.RELEASE
* spring-webmvc:4.3.4.RELEASE

### <span id = "jddpz">简单的配置</span>
配置Spring MVC只需要三部：  
1. 在web.xml中配置Servlet；
2. 创建Spring MVC的xml配置文件；
3. 创建Controller和view

#### 在web.xml中配置Servlet
```xml
<!--spring mvc begin-->
<servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
<!--spring mvc end-->
```
这里配置的DispatcherServlet就是Spring MVC的入口， Spring MVC本质就是一个servlet。通过contextConfigLocation可以设置Spring MVC配置文件的位置，如果不指定，默认使用WEB-INF/[servlet-name]-servlet.xml文件。
#### 创建Spring MVC的xml配置文件
在resource目录下新建springmvc-servlet.xml文件，然后使用最简单的配置方式来进行配置。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
             http://www.springframework.org/schema/beans/spring-beans.xsd
             http://www.springframework.org/schema/context
             http://www.springframework.org/schema/context/spring-context.xsd
             http://www.springframework.org/schema/mvc
             http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <mvc:annotation-driven/>
    <context:component-scan base-package="org.bedoing" />
</beans>
```
<mvc:annotation-driven/>是Spirng MVC提供的一键式的配置方法，它会帮我们做一些注册组件的事情。context:component-scan 用来扫描通过注解配置的类，这里配置了扫描org.bedoing包下的所有类。
#### 创建Controller和view
目前为止Spring MVC的环境已经搭建完成，接下来添加Controller完成业务逻辑和添加View展示数据。
* 在org.bedoing.controller包下新建类UserConroller
```java
package org.bedoing.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Ken
 */
@Controller
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    // 处理HEAD类型的"/"请求
    @RequestMapping(value = "/", method = HEAD)
    public String head() {
        return "user.jsp";
    }

    @RequestMapping(value = {"/user", "/"}, method = GET)
    public String user(Model model) {
        logger.info("enter user: " + model);
        model.addAttribute("msg", "for user test");
        return "user.jsp";
    }
}
```
这里的HEAD请求方法，可以用来检测服务器的状态。因为它不返回body所以比GET请求节省网络资源。单独写一个处理方法而不和GET请求使用同一个方法，是因为GET请求处理过程可能会处理一些别的内容，如初始化数据、连接数据库等，而这些动作比较浪费资源，并且HEAD请求也不需要。  

* 根目录下新建user.jsp
Spring MVC默认使用org.springframework.web.servlet.view.InternalResourceViewResolver作为ViewResolver，并且prefix和suffix都为空。所以这里"user.jsp"返回值对应的就是根目录下的user.jsp文件。
### <span id = "bsyx">部署运行</span>
部署到tomcat运行就可以看到也么输出字符串"for user test"。
