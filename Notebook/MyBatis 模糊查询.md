# MyBatis 模糊查询

## 方法一

```xml
<!-- （模糊查询）返回数据集合，只需设定为每一个元素的数据类型 -->
<select id="queryByBlurredName" parameterType="string" resultMap="studentResultMap">
    <!-- 准确查询：select * from stu_info where stu_name like #{name} -->
    select * from stu_info where stu_name like '%${name}%'
</select>
```

**这种方法简单可行，但是不安全，因为在官方文档中也有说明，使用 $ 直接对用户输入的字符串进行转义不能防止SQL注入。**

## 方法二

```xml
<!-- （模糊查询）返回数据集合，只需设定为每一个元素的数据类型 -->
<select id="queryByBlurredName" parameterType="string" resultMap="studentResultMap">
    <!-- 准确查询：select * from stu_info where stu_name like #{name} -->
    select * from stu_info where stu_name like "%"#{name}"%"
</select>
```

这种方式运行时，在控制台中打印的SQL语句为：

```language
DEBUG [main] - ==>  Preparing: select * from stu_info where stu_name like "%"?"%" 
DEBUG [main] - ==> Parameters: L(String)
```

但是有一点就是，如果我把上面的双引号改为单引号，SQL语句不报错，但是查询不到结果。

## 方式三：使用 bind 标签

bind 元素可以从 OGNL 表达式中创建一个变量并将其绑定到上下文。比如：

```xml
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```

因为我这里直接输入的就是一个字符串，所以无需进行属性导航

```xml
<!-- （模糊查询）返回数据集合，只需设定为每一个元素的数据类型 -->
<select id="queryByBlurredName" parameterType="string" resultMap="studentResultMap">
    <bind name="pattern" value="'%' + name + '%'"></bind>
    select * from stu_info where stu_name like #{pattern}
</select>
```

这种方式下控制台输出的SQL语句为：

```language
DEBUG [main] - ==>  Preparing: select * from stu_info where stu_name like ? 
DEBUG [main] - ==> Parameters: %L%(String)
```

如果我们需要为传入的参数添加一个测试添加，那么我们需要把 bind 标签放在条件语句中，否则参数不可知。

```xml
<!-- 通过名称进行模糊查询，如果传入为空，则查询全部 -->
<select id="queryByBlurredName" parameterType="string" resultMap="studentResultMap">
    select * from stu_info
    <where>
        <if test="name != null and name != ''">
            <bind name="namePattern" value="'%'+name+'%'"/>
            stu_name like #{namePattern}
        </if>
    </where>
</select>
```

接口传入的值在name变量中，然后把传入的值拼接成"'%'+name+'%'"形式，放入namePattern参数中

可以看出，和上面的方式还是有差别的，方式三安全好用。