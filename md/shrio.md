# 1. shrio基本

shrio是一种易用且强大的java安全框架

![](https://img-blog.csdnimg.cn/20181205205704182.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Blbmdqd2h4,size_16,color_FFFFFF,t_70)

* Authentication：身份认证/登录，验证用户是不是又相应的身份
* Authorization：授权，即权限认证，验证某一已认证的用户是否拥有某一个权限。
* Session Manager:会话管理，即用户登录后就是一次会话，在没有退出之前，他所有的信息都在会话中。会话可以是普通的JavaSE环境，可以非常容易的集成到web环境
* Cryptography：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储
* web-suport:web支持
* caching:缓存
* concurrency：shrio支持多线程应用的并发认证
* Tesing：提供测试支持
* RunAs：允许一个用户假装另一个用户的身份进行访问
* RememberMe:保证一次登录后，下次再来的话，不用登录

# 2. shrio实现原理

![](https://img-blog.csdnimg.cn/20181205210949620.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Blbmdqd2h4,size_16,color_FFFFFF,t_70)

* 简单的说：应用代码通过subject来进行认证和授权，而subject又委托给securityManager；我们需要给Shrio的SecurityManager注入Realm，从而让SecurityManager能得到合法的用户及其权限判断。

# 3.shrio的架构理解

![](https://img-blog.csdnimg.cn/20181205211620525.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Blbmdqd2h4,size_16,color_FFFFFF,t_70)

# 4.shrio认证功能(Aurhentication)流程

![](https://img-blog.csdnimg.cn/20181205214042629.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Blbmdqd2h4,size_16,color_FFFFFF,t_70)



```java
@Test 
public void testHelloworld() { 
    //1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager 
    Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini"); 
    //2、得到 SecurityManager 实例 并绑定给 SecurityUtils
    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance(); SecurityUtils.setSecurityManager(securityManager); 
    //3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证） 
    Subject subject = SecurityUtils.getSubject(); 
    // UsernamePasswordToken 继承 
    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123"); try { 
        //4、登录，即身份验证 subject.login(token); 
    } catch (AuthenticationException e) { 
        //5、身份验证失败 
    } Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录 
    //6、退出 
    subject.logout();
```

```
1、UsernamePasswordToken 实现HostAuthenticationToken和RemeberAuthenticationToken，HostAuthenticationToken实现AuthenticationToken
2、首先调用 Subject.login(token)进行登录，其会自动委托给 Security Manager，调用之前必
须通过 SecurityUtils. setSecurityManager()设置；
3、SecurityManager 负责真正的身份验证逻辑；它会委托给 Authenticator 进行身份验证；SecurityManager j接口继承Authenticator、Authrizer、sessionManage接口
4、Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可以自
定义插入自己的实现；
5、Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证，默认
ModularRealmAuthenticator 会调用 AuthenticationStrategy[AtLeastOneSuccessfulStrategy] 进行多 Realm 身份验证；
6、Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，如果没有返
回/抛出异常表示身份验证失败了。此处可以配置多个 Realm，将按照相应的顺序及策略进
行访问。
```

```flow
st=>start: sbuject接受到token

op1=>operation: 调用DelegatingSubject将token传给SecurityManager

op2=>operation: SecurityManager通过DefaultSecurityManager来的login方法完成

op3=>operation: login中调用authenticate()来完成认证，在AuthenticatingSecurityManager

op4=>operation: authenticate()中通过authenticator来完成认证。Authenticator默认实现ModularRealmAuthenticator认证

op5=>operation: ModularRealmAuthenticator通过doAuthenticate来获取Realms信息。

cond=>condition: 是否单realm

op6=>operation: 直接比较判断

sub1=>subroutine: 需要AuthenticationStrategy【默认AtLeastOneSuccessfulStrategy】来认证

e=>end

st->op1->op2->op3->op4->op5->cond

cond(yes)->op6->e

cond(no)->sub1->e


```

# 5.常用的权限注解

```
@RequiresAuthentication
//验证用户是否登录：即Subject.isAuthenticated()返回true
@RequiresUser
//验证用户是否呗记忆，user有两种含义
//1.一种是成功登录的（subject.isAuthenticated()结果为true）
//2. 一种是被记忆的（subject.isRemembered()结果为true）
@RequiresGuest
//验证是否是guest的请求，与@RequiresUser完全相反，此时subject.getPrincipal=null
@RequiresRoles(value={“admin”, “user”}, logical= Logical.AND)
//表示当前 Subject 需要角色 admin 和 user。
@RequiresPermissions (value={“user:a”, “user:b”}, logical= Logical.OR)
//表示当前 Subject 需要权限 user:a 或 user:b
```

