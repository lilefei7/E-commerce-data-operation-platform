# 目录


<a href="https://github.com/ailefun/E-commerce-data-operation-platform#%E5%8A%9F%E8%83%BD%E6%A8%A1%E5%9D%97%E5%88%92%E5%88%86">功能模块划分</a></br>
<a href="https://github.com/ailefun/E-commerce-data-operation-platform/blob/main/README.md#%E6%80%BB%E4%BD%93%E8%AE%BE%E8%AE%A1">总体设计</a></br>
<a href="https://github.com/ailefun/E-commerce-data-operation-platform/blob/main/README.md#%E6%A6%82%E8%A6%81%E8%AE%BE%E8%AE%A1">概要设计</a></br>
<a href="https://github.com/ailefun/E-commerce-data-operation-platform/blob/main/README.md#%E6%8A%80%E6%9C%AF%E6%96%B9%E6%A1%88">技术方案</a></br>
<a href="https://github.com/ailefun/E-commerce-data-operation-platform/blob/main/README.md#%E6%95%B0%E6%8D%AE%E5%BA%93%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1">数据库详细设计</a></br>
<a href="https://github.com/ailefun/E-commerce-data-operation-platform/blob/main/README.md#%E5%BF%83%E5%BE%97%E4%BD%93%E4%BC%9A">心得体会</a></br>

# 功能模块划分

   权限控制、异常处理、日志记录、用户管理、产品管理。



# 总体设计

   后端用springboot等，用aop实现日志记录等，javabean做模型，thymeleaf做视图，springmvc做控制，junit做测试。

# 概要设计

​      项目基于springboot，用aop实现日志记录、权限控制、异常捕获，用springmvc接收前端的请求、实现数据类型转换，用mybatis的注解开发持久层，Product.java、User.java作为和产品用户相关的模型，当前端有与文件相关的操作时用ProductIncludeFile.java、UserIncludeFile.java做模型，UserController、ProductController负责和用户产品有关的路由控制，CaptchaController负责接收和验证码有关的请求，UserService、ProductService、CaptcahService负责和服务器端有关的逻辑处理，AbnormalAccessException、PermissionDeniedException会在用户非正常操作和越权操作时抛出，GlobalExceptionAdvice负责捕获它们以及其它的异常。

​      前端用thymeleaf做模板引擎、用bootstrap和jQuery简化前端设计，用eCharts设计统计图，index.html作为网站的登录页，也是触发各种异常时重定向的页面，listProduct.html作为用户登录成功后跳转的页面，也是用户在进行完各种添加、删除、更新操作之后重定向的界面，include.html写菜单和网站顶部的logo、导航、用户名、头像，listUser.html显示账户维护界面，提供用户信息的分页查看、添加、导出、删除、编辑操作，listProduct.html显示商品维护界面，提供商品信息的分页查看、添加、导出、批量删除、删除单个、上架下架、编辑、查看详情操作，editProduc.html、editUser.html提供修改商品和用户图片等各项信息的功能，analysisData.html显示热卖商品表、急需补货商品表、急需补货的商品和其访问量的条形统计图，热卖商品访问量的扇形统计图。



# 技术方案

jdk11.0.9

springBoot2.4.1

mybatis3.4.5

thymeleaf3.0.11

maven2.0.7
 tomcat.9.0.41

bootstrap4.5.3

jQuery3.5.1

popper.js1.16.0

echarts4.9.0

junit5.7.0

pagehelper1.2.3

mysql8.0.22

logback1.2.3

hutool5.5.4

fastjson1.2.75

kaptcha2.3.2

swagger2.6.1

servlet4.0.1 



# 数据库详细设计：

### 数据字典：

**数据存储**

名称：用户表      

组成：用户id、用户名、密码、年龄、手机、邮箱、地址、是否为管理员、头像地址

主键：用户名   

说明：存储用户信息

 

名称：产品表      

组成：产品id、产品名称、价格、库存、访问量、上架状态、上架日期、描述、图片地址

主键：产品名称

外键：说明：存储垃圾信息

 

### 关系模式：

用户表（用户id、用户名、密码、年龄、手机、邮箱、地址、是否为管理员、头像地址

）

垃圾信息表（产品id、产品名称、价格、库存、访问量、上架状态、上架日期、描述、图片地址

）

用户表（user）

 

| 字段名      | 含义     | 数据类型     | 备注      |
| ----------- | -------- | ------------ | --------- |
| id          | 名字     | varchar(50)  | 非空 唯一 |
| name        | 价格     | number（10） | 非空      |
| price       | 库存     | number（10） | 非空      |
| sum         | 访问量   | varchar(200) | 非空      |
| visitCount  | 状态     | varchar(200) | 非空      |
| status      | 上架时间 | date         | 非空      |
| addDate     | 描述信息 | varchar(200) | 非空      |
| description | 商品图片 | varchar(200) | 非空 唯一 |
| pic_url     | 名字     | varchar(200) | 非空 唯一 |

 

垃圾信息表（garbageInfo）

| 字段     | 含义   | 类型         | 备注      |
| -------- | ------ | ------------ | --------- |
| id       | 主键   | number（10） | 主键      |
| username | 名字   | varchar(200) | 非空 唯一 |
| password | 密码   | varchar(200) | 非空      |
| age      | 年龄   | number（10） | 非空      |
| mobile   | 手机号 | varchar(200) |           |
| email    | 邮箱   | varchar(200) |           |
| address  | 地址   | varchar(200) |           |
| status   | 状态   | varchar(200) |           |
| head_pic | 头像   | varchar(200) |           |

 

### E-R模型图

 

​                   

# 心得体会

### 为什么要分Service层和Controller层

封装Service层的业务逻辑有利于通用的业务逻辑的独立性和重复利用性。

重复利用性：当项目足够大是，才会有一些逻辑会重复利用，比如许多个页面都要查询数据库中的全部商品，这个时候dao层查询全部商品的逻辑就在重复利用，项目再大点，service层的逻辑也会重复利用。

### 在实训中期，我发现了自己的项目可能需要改进的几个点：

接收含有文件的参数的时候，我创建了一个实体类，可以单独接收前端的参数。

定义接收的图片的名字的时候，我直接用的图片的原始名字，可能会造成文件名冲突。

添加service层。

### 获取图片后缀和随机的方法：

String suffix = realname.substring(realname.lastIndexOf("."));
 String uuid = UUID.randomUUID().toString();

### 获得static目录所在位置的方法

我在转存文件的时候，改了下springboot的启动文件（含有@SpringBootApplication的那个文件）有点麻烦，而且写死了。

@Bean
 MultipartConfigElement multipartConfigElement() {
   MultipartConfigFactory factory = new MultipartConfigFactory();
  factory.setLocation("C:\\Users\\le\\Desktop\\SCD\\src\\main\\resources\\static");
   return factory.createMultipartConfig();
 }

用下面这种方便


 String path = ResourceUtils.getFile("classpath:static").getPath();

### @Autowired注解失效的问题

@Autowired
 private Producer producer;

将上面的代码写道控制层可以使用，写到服务层注解就失效，producer的值为空。

服务层里面有@Autowired的时候，使用服务层的实例要用spring注入，如果直接new。得到的实例不会被spring管理，里面写的spring注解就会失效。

### @Service是标记在接口上还是实现类上？

@Service注解是标注在实现类上的，因为@Service是把spring容器中的bean进行实例化，也就是等同于new操作，只有实现类是可以进行new实例化的，而接口则不能。

### 当用户在更改产品信息的时候，没有上传图片的问题

在产品信息页面listProduct原来只把id传到服务器，现在增加一个产品图片路径的参数也传到服务器，传到editProduct，再返回给编辑产品信息的页面admin/editProduct，当提交更改产品信息的请求时，把图片路径一起传到服务器updateProduct，当用户没有传图片的时候就用以前的路径信息。

if (productIncludeFile.getPic_url().getOriginalFilename().equals("")) {
 //      用户在更改信息的时候没有选择图片
       saveName = pic_url_spare;
    }else {
 ...

### 当用户在更改用户信息的时候，没有上传图片的问题

这个问题的解决方法可以和上面的一样，但是这样会比较繁琐，用session会更方便

在editUser里面本来要去查数据库，把用户的老信息显示在修改信息的视图admin/editUse中r，所以在editUser可以把user.getHead_pic()存入到session中，在updateUser中取出来。

### 使用hutool导出excel的问题

主要代码

报错：You need to add dependency of 'poi-ooxml' to your project, and version >= 4.1.2] with root cause

应导入
   <groupId>org.apache.poi</groupId>
   <artifactId>poi-ooxml</artifactId>

### header中Content-Disposition

Content-disposition 是 MIME 协议的扩展

举例：

response.setHeader("content-disposition", "attachment;fileName="+ URLEncoder.encode("用户导出表.xlsx", "UTF-8"));

如attachment为以附件方式下载，发送给浏览器的文件默认是使用浏览器打开如：tex，jpg，而attachment参数会让浏览器弹出一个下载框。fileName指定文件名。

[参考](https://www.cnblogs.com/wq-9/articles/12165056.html)

### 用aop写日志

多输出点日志是为了调试方便

@Component加上这个注解spring才能扫到

@Aspect写aop都应该加上这个注解

声明切入点@Pointcut

@Pointcut("execution(* com.lefei.demo1.controller.*.*(..))")
 private void selectAll(){}

第一个*：所有类，

第二个*：所有所有方法，

第（ .. ）：所有参数

使用@Before

@Before("selectAll()")
 public void beforeAdvice(){
   logger.info();
   System.out.println("Going to setup student profile.");
 }

[Spring AOP切点表达式用法总结](https://www.cnblogs.com/zhangxufeng/p/9160869.html)

RequestContextHolder是获取attribute，session，request，response非常简单的工具

当username==null的时候，会抛出异常java.lang.IllegalStateException: getOutputStream() has already been called for this response，

原因是关于验证码的那一块代码有问题

我以前为了让函数的参数少一些，generateCodeAndReturn这个函数并没有HttpResponse这个形参，

HttpResponse是用RequestContextHolder动态获取的。应该是controller在收到前端的请求的时候自己创建了一个HttpResponse，在获取验证码的函数里获取到的response和controller里的不是一个。

[参考](https://blog.csdn.net/a_hxy/article/details/106903681)

### springBoot项目用junit5做测试

TestRestTemplate的使用，

restTemplate.getForEntity(）这个方法有三个重载方法

public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables) throws RestClient Exception {
   return this.restTemplate.getForEntity(url, responseType, urlVariables);
 }

举例

restTemplate.getForEntity(path , String.class, "");第二个参数是返回值的类型，最后一个参数可以传Object、Map<String, ?>，或者不传

发送请求还可以用：restTemplate.exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... urlVariables)

### 测试究竟要做什么？

单元测试就是把每个单元执行一遍，看断言返回的结果是否是期待的。在写完单元测试之后，项目的代码还是可能修改的，这个时候只要运行一下单元测试，就知道项目的某些单元是否被影响了。

有一些逻辑看似简单，比如访问/index返回首页index.html，但是，以后可能会加个权限验证的功能，导致必须登录才能访问登录页面。

#### MockMvc测试

##### MockMvcResultMatchers

MockMvcResultMatchers.status()判断的东西

##### 执行pic_url.transferTo(file);的时候报异常UnsupportedOperationException

要测试带有文件的添加用户功能

测试代码

service层代码

当使用pic_url.transferTo(file);把图片报错到本地的时候，抛出异常UnsupportedOperationException

解决方法：找到transferTo函数的实现方法，发现底层用的 FileCopyUtils.copy(pic_url.getInputStream(), Files.newOutputStream(file.toPath()));用这种方法就不会抛出异常。

.file(mockMultipartFile)不能写成.file("pic_url",mockMultipartFile.byte())否则控制器里的pic_url会变成null

@Transactional可以让数据库回滚，但是在执行代码的过程中如果保存了一个文件，文件不会删除

当方法中指定@RequestParam(value = "")时，如果没有传value中的对象，即使用aop在函数之前有处理，也会报错。比如：

```
public String batchDelete(@RequestParam(value = "ids[]") String[] ids) throws Exception {
```

​                                

 

​     
