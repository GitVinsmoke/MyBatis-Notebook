# MyBatis与Spring整合（非Web工程）

## 整合思路

1. SqlSessionFactory对象应该放到Spring容器中作为单例存在（Spring Bean默认也是单例模式）。

2. 传统DAO的开发方式中，应该从Spring容器中获得SqlSession对象。

3. Mappper 代理形式中，应该从Spring容器中直接获得mapper的代理对象。

4. 数据库的连接以及数据库连接池事务管理统一交给Spring容器来完成。

## 整合需要的JAR包

1. Spring的JAR包

2. MyBatis的JAR包

3. spring-mybatis的整合包

4. 数据库驱动包

5. 数据库连接池的JAR包

6. 其他

## 配置文件

配置文件一般放在类路径下面，如果是maven工程，则配置文件放在 src/main/resource 目录下，否则我们也可以在项目根路径下创建一个 config 源文件夹（source folder），将配置文件放在此文件夹下。

####  MyBatis的配置文件 SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">

<!-- MyBatis核心配置文件 -->

<configuration>
	<!-- 配置别名 -->
	<typeAliases>
    	<!-- 指定扫描包，会把包内所有的类都设置别名，别名的名称就是类名，大小写不敏感 -->
		<package name="com.li.domain"></package>
	</typeAliases>
</configuration>

```

#### Spring的配置文件 applicationContext.xml

实际上，为了分清楚功能，我们一般还可以把Spring的配置文件分为三类：

- applicationContext-dao.xml：与数据访问层有关的配置

- applicationContext-service.xml：与服务层有关的配置

- applicationContext-transaction.xml：与事务有关的配置

applicationContext-dao.xml配置：

```language
<?xml version="1.0" encoding="UTF-8"?>

<!-- 引用Spring的多个Schema空间的格式定义文件 -->

<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
	 http://www.springframework.org/schema/beans/spring-beans.xsd
	 http://www.springframework.org/schema/aop
	 http://www.springframework.org/schema/aop/spring-aop.xsd
	 http://www.springframework.org/schema/context
	 http://www.springframework.org/schema/context/spring-context.xsd">
	 
	 <!-- 先引入数据库的配置文件 -->
	<context:property-placeholder location="classpath:database.properties"/>
	
	<!-- 数据源 -->               
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driver}"></property>
		<property name="jdbcUrl" value="${jdbc.url}"></property>
		<property name="user" value="${jdbc.username}"></property>
		<property name="password" value="${jdbc.password}"></property>
	</bean>
	
	<!-- 配置SqlSessionFactory -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 设置MyBatis核心配置文件 -->
		<property name="configLocation" value="classpath:mybatis/SqlMapConfig.xml"></property>
		<!-- 设置数据源 -->
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	
	<!-- 配置Mapper扫描 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 设置Mapper扫描包 -->
		<property name="basePackage" value="com.li.mapper" />
	</bean>
	
</beans>
```

在上面的配置中，我们配置了数据库访问的基本信息以及数据源（我使用的是c3p0数据源），另外我们需要针对MyBatis配置SqlSessionFactory，这里，我们需要将位于mybatis-spring整合的JAR包（对于Spring来说，MyBatis是另外一个架构，需要整合）中的SqlSessionFactoryBean类作为Bean配置进来。

我们在上面吧包的别名扫描配置在MyBatis的核心配置文件中，如果不配置在MyBatis的核心配置文件中我们也可以配置在这里面，在 sqlSessionFactory Bean下，其类中有一个 typeAliasesPackage 属性，可以配置别名包扫描：

```xml
<property name="typeAliasesPackage" value="com.li.domain"/>
```

其中 database.properties 的配置内容为：

```language
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/learning?useSSL=true
jdbc.username=root
jdbc.password=123456
```

还有Spring对于服务层的配置，我们选择的是自动扫描注册为Bean，当然其中需要在服务类（服务实现类）上添加@Service注解才行。applicationContext-service.xml的配置为：

```language
<?xml version="1.0" encoding="UTF-8"?>

<!-- 引用Spring的多个Schema空间的格式定义文件 -->

<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
	 http://www.springframework.org/schema/beans/spring-beans.xsd
	 http://www.springframework.org/schema/aop
	 http://www.springframework.org/schema/aop/spring-aop.xsd
	 http://www.springframework.org/schema/context
	 http://www.springframework.org/schema/context/spring-context.xsd">
	 
	<!-- 配置service扫描 -->
	<context:component-scan base-package="com.li.service"></context:component-scan>
	
</beans>
```

Spring对事务的配置，applicationContext-transaction.xml的配置为：

```language
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context 
	http://www.springframework.org/schema/context/spring-context-4.0.xsd
	http://www.springframework.org/schema/aop 
	http://www.springframework.org/schema/aop/spring-aop-4.0.xsd 
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
	http://www.springframework.org/schema/util 
	http://www.springframework.org/schema/util/spring-util-4.0.xsd">

	<!-- 事务管理器，使用JDBC模板类或者iBatis时使用该事务管理器 -->
	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<!-- 注入数据源 -->
		<property name="dataSource" ref="dataSource" />
	</bean>

	<!-- 事务通知 -->
	<tx:advice id="txAdvice"
		transaction-manager="transactionManager">
		<tx:attributes>
			<!-- 传播行为 -->
			<tx:method name="save*" propagation="REQUIRED" />
			<tx:method name="insert*" propagation="REQUIRED" />
			<tx:method name="add*" propagation="REQUIRED" />
			<tx:method name="create*" propagation="REQUIRED" />
			<tx:method name="delete*" propagation="REQUIRED" />
			<tx:method name="update*" propagation="REQUIRED" />
			<tx:method name="find*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="select*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="get*" propagation="SUPPORTS" read-only="true" />
			<tx:method name="query*" propagation="SUPPORTS" read-only="true" />
		</tx:attributes>
	</tx:advice>
	<!-- 事务切面 -->
	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* com.li.web.service.*.*(..))" />
	</aop:config>

</beans>
```

**注意：**使用MyBatis持久化框架时使用的事务管理器是Spring中的DataSourceTransactionManager事务管理器。另外我们如果使用配置来声明事务管理的话还需配置事务通知以及事务切面，如果使用注解来声明事务，则应该如下开启声明式事务：

```xml
<!-- 开始声明式事务注解扫描 -->
<tx:annotation-driven/>
```

在这里还有一个比较容易犯错的地方，那就是切入点的选择，我们使用AspectJ切入点表达式的时候，应该吧切入点选择服务的接口而不是服务的实现类上面，因为我们在使用都是使用接口来进行操作（面向接口编程），执行的是接口中的方法，所以我们要对接口中的方法进行通知。

Log4J日志工具的配置：

```language
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

## DAO 开发

在MyBatis中我们根据版本的推荐不同，有两种DAO开发方式：

1. 原始的DAO开发方式

2. 使用Mapper代理形式开发方式（官方文档推荐）

	- 直接配置Mapper代理

	- 使用扫描包配置Mapper代理

### 传统的DAO开发方式

原始的DAO接口+DAO接口实现类，而DAO接口实现类需要继承Spring提供的SqlSessionDaoSupport这样一个支持类，SqlSessionDaoSupport提供了getSqlSession()方法来获取SqlSession。从而在实现类中，通过XML映射文件来进行数据库的操作。这样也很简单，不多说。

### 使用Mapper代理形式开发方式

使用Mapper代理形式开发方式实际上就取代了DAO接口。

我们可以先实现 Mapper.xml映射文件，并为其编写一个映射器类（例如：StudentMapper接口），接下来我们需要配置Mapper代理，有两种方式：

##### 1. 直接配置Mapper代理

在 applicationContext-dao.xml 文件中添加配置：

```xml
<!-- Mapper代理开发方式之一：配置Mapper代理对象 -->
<bean id="studentMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
	<!-- 配置Mapper接口 -->
    <property name="mapperInterface" value="com.li.mapper.StudentMapper"/>
    <!-- 配置SqlSessionFactory -->
	<property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

##### 2. 扫描包形式配置Mapper代理

使用上述方式配置Mapper未免有些麻烦，因为需要为每个Mapper都配置代理对象。第二种方式就稍微快捷一些，我们在 applicationContext-dao.xml 文件中添加配置：

```xml
<!-- 配置Mapper扫描 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <!-- 设置Mapper扫描包 -->
    <property name="basePackage" value="com.li.mapper" />
</bean>
```

**这样配置，我们就可以扫描一个包下面的Mapper，每个Mapper代理对象的id就是类名，首字母小写。**

需要注意的是，我们好像并没有将SQL映射文件配置进入MyBatis的核心配置文件，这是为什么呢？因为上面两种方式会将和映射器类同一个包下找SQL映射器文件，所以，我们将映射器类和SQL映射文件放在同一个包下面。

SQL XML映射文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- namespace：命名空间，用于隔离sql，还有一个很重要的作用，后面解释 -->

<mapper namespace="com.li.mapper.StudentMapper">

	<!-- 结果映射 -->
	<resultMap id="studentResultMap" type="Student">
		<constructor>
			<arg column="stu_no" name="no" javaType="string"/>
			<arg column="stu_name" name="name" javaType="string"/>
			<arg column="stu_age" name="age" javaType="int"/>
  			<arg column="stu_gender" name="gender" javaType="string"/>
		</constructor>
  		<id property="no" column="stu_no"/>
  		<result property="name" column="stu_name"/>
  		<result property="age" column="stu_age"/>
  		<result property="gender" column="stu_gender"/>
	</resultMap>

	<!-- id:statement的id 或者叫做sql的id-->
	<!-- parameterType:声明输入参数的类型 -->
	<!-- resultType:声明输出结果的类型，应该填写pojo的全路径 -->
	<!-- #{}：输入参数的占位符，相当于jdbc的？ -->

	<!-- 根据ID查找 -->
	<select id="queryById" parameterType="string" resultMap="studentResultMap">
		SELECT * FROM stu_info WHERE stu_no  = #{id, javaType=java.lang.String, jdbcType=VARCHAR}
	</select>
	
	<!-- （模糊查询）返回数据集合，只需设定为每一个元素的数据类型 -->
	<select id="queryByBlurredName" parameterType="string" resultMap="studentResultMap">
		<!-- 准确查询：select * from stu_info where stu_name like #{name} -->
		select * from stu_info where stu_name like '%${value}%'
	</select>

	<!-- 插入操作 -->
	<insert id="insertStu" parameterType="student">
		<!-- 有多个参数，如果没有做映射关系的工作，则需要名称一样 -->
		insert into stu_info (stu_no, stu_name, stu_age, stu_gender) values(#{no}, #{name}, #{age}, #{gender});
	</insert>
	
	<!-- 删除操作 -->
	<delete id="deleteStu" parameterType="string">
		delete from stu_info where stu_no=#{id}
	</delete>

	<!-- 更新操作 -->
	<update id="updateName" parameterType="student">
		update stu_info set stu_name=#{name} where stu_no=#{no}
	</update>

</mapper>
```

映射器类：

```java
package com.li.mapper;

import java.util.List;

import com.li.domain.Student;

/*
 * 映射器类：动态代理
 * 1. SQL映射文件的命名空间必须是接口的完全限定名
 * 2. 接口方法必须与SQL id一致（除了使用注解的SQL语句）
 * 3. 接口的入参必须与parameterType类型一致
 * 4. 接口的返回值必须与resultType类型一致
 */

public interface StudentMapper {
	
	/*根据ID查询*/
	Student queryById(String id);
	
	/*根据名字进行模糊查询*/
	List<Student> queryByBlurredName(String name);
	
	/*插入操作*/
	void insertStu(Student s);
	
	/*删除操作*/
	void deleteStu(String id);
	
	/*更新操作*/
	void updateName(Student s);
}

```

服务接口：

```java
package com.li.service;

import com.li.domain.Student;

public interface StudentService {
	
	Student queryById(String id);
	
}

```

服务接口的实现类（注意注解，否则找不该Bean，注解之后的Bean id为首字母小写的类名）：

```java
package com.li.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.li.domain.Student;
import com.li.mapper.StudentMapper;
import com.li.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	@Qualifier(value="studentMapper")
	private StudentMapper studentMapper;
	
	@Override
	public Student queryById(String id) {
		return studentMapper.queryById(id);
	}

}

```

测试：

```java
package com.li.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.li.domain.Student;
import com.li.service.StudentService;

public class Test {

	public static void main(String[] args) {
		String r1="classpath:spring/applicationContext-dao.xml";
		String r2="classpath:spring/applicationContext-service.xml";
		String r3="classpath:spring/applicationContext-transaction.xml";
		ApplicationContext ctx=new ClassPathXmlApplicationContext(r1,r2, r3);
		
		StudentService service = ctx.getBean("studentServiceImpl", StudentService.class);
		Student s = service.queryById("2015");
		System.out.println(s);
	}

}

```