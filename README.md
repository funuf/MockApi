## 简介

如果你是前端/移动端开发人员，否和我一样，经常面临下述局面：
1. 尝试实现（或学习）某个框架/控件的时候，没有合适的接口提供数据；
2. 工作中页面已搭完，仅剩数据请求的逻辑要实现，无奈同事的接口还没有整理出来。

此时，要么干等，要么自己动手编码进行实现，再不行了去扒拉一些免费api凑合着用。

如果有一个项目，或者一个api，能够在开发（学习）阶段输出可自由定制结构的数据，岂不是能大大提高咱的效率？

**MockApi**就是为此而诞生的服务端项目。它旨在为前端/移动端开发者提供模拟数据。即使不会进行服务端的API接口编码，也可通过发送http请求获取各式占位数据。

## 安装

1. 【Windows】MockApi是一个Spring Boot 工程，如果你有Java开发能力，可以直接clone该项目，在IDE中运行体验；

2. 【Windows】下载[MockApi.jar](https://github.com/he110fun/MockApi/raw/master/MockApi.jar)，然后在jar包所在目录启动命令行（Win+R）,执行```java -jar MockApi.jar```命令在10240端口运行，也可执行```java -jar MockApi.jar --server.port=xxx```自定义端口；

3. 【Windows】方式2的缺点是不能关闭命令行窗口，否则MockApi也将终止。建议将MockApi.jar安装为Windows服务，方法如下：
    - 下载[MockApi.jar](https://github.com/he110fun/MockApi/raw/master/MockApi.jar)；
    - 下载[winsw](https://github.com/kohsuke/winsw/releases)项目的```WinSw.Net4.exe```文件；
    - 将```MockApi.jar```和```WinSw.Net4.exe```置于同一文件夹，并将```WinSw.Net4.exe```重命名为```MockApi.exe```；
    - 在文件夹下创建```MockApi.xml```,并写入以下内容：
    ```
        <service>
         <id>MockApi</id>
         <name>MockApi</name>
         <description>MockApi is a provide mock data project.</description>
         <!-- java环境变量 -->
         <env name="JAVA_HOME" value="%JAVA_HOME%"/>
         <executable>java</executable>
           <arguments>-jar "C:\Users\Administrator\Desktop\MockApi\MockApi.jar"</arguments>
         <!-- 开机启动 -->
         <startmode>Automatic</startmode>
         <!-- 日志配置 -->
         <logpath>%BASE%\log</logpath>
         <logmode>rotate</logmode>
     </service>
    ```
      - 命令行定位到当前目录，执行```MockApi.exe install``即可；
      - 去Windows服务列表中启动程序。
    
4. 【Linux】下载[MockApi.jar](https://github.com/he110fun/MockApi/raw/master/MockApi.jar)，参考[这里](https://www.cnblogs.com/linnuo/p/9084125.html)运行jar包。

## 使用

#### 命令式接口

所谓命令，比如```git clone origin https://github.com/he110fun/MockApi.git```就是一条克隆仓库的命令。
所谓命令式接口，指的是目标接口的请求路径是命令式的。

比如```http://localhost:10240/mockapi get integer```表示的是要请求一个整形数字，本次请求将返回如下数据：
```
{
    "code": 100, // --------------响应码
    "data": 11,  // --------------随机数字
    "time": 1584697248365 // -----当前时间
}
```
再比如```http://localhost:10240/mockapi get image```表示的是要请求一张图片，本次请求将返回如下数据：
```
{
    "code": 100,
    "data": "https://up.enterdesk.com/edpic/3f/2a/7b/3f2a7bf15fd029ee577391e6e958173b.jpg", //---------随机图片
    "time": 1584697365922
}
```

#### 可用命令

**MockApi**已支持的命令如下表所示：
