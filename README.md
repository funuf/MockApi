## 简介

如果你是前端/移动端开发人员，否和我一样，经常面临下述局面：
1. 尝试实现（或学习）某个框架/控件的时候，没有合适的接口提供数据；
2. 工作中页面已搭完，仅剩数据请求的逻辑要实现，无奈同事的接口还没有整理出来。

此时，要么干等，要么自己动手编码进行实现，再不行了去扒拉一些免费api凑合着用。

如果有一个项目，或者一个api，能够在开发（学习）阶段输出可自由定制结构的数据，岂不是能大大提高咱的效率？

**MockApi**就是为此而诞生的服务端项目。它旨在为前端/移动端开发者提供模拟数据。即使不会进行服务端的API接口编码，也可通过发送http请求获取各式占位数据。

## 安装

1. 【Windows】**MockApi**是一个Spring Boot 工程，如果你有Java开发能力，可以直接clone该项目，在IDE中运行体验；

2. 【Windows】下载[MockApi.jar](https://github.com/he110fun/MockApi/raw/master/MockApi.jar)，然后在jar包所在目录启动命令行（Win+R）,执行```java -jar MockApi.jar```命令在10240端口运行，也可执行```java -jar MockApi.jar --server.port=xxx```自定义端口；

3. 【Windows】方式2的缺点是不能关闭命令行窗口，否则服务也将终止。建议将[MockApi.jar](https://github.com/he110fun/MockApi/raw/master/MockApi.jar)安装为Windows服务，方法如下：
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

命令，就是要让一个系统执行一件事，比如```git clone origin https://github.com/he110fun/MockApi.git```就是一条让git执行克隆仓库的命令。
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

#### 命令结构

**MockApi**的命令由若干部分组成，如 ```mockapi cmd type topic limit count hit file delay```，除了```mockapi```和```cmd```的顺序固定，其他部分无顺序限制。各部分的详细定义及取值见下表：

<table>
    <tr>
        <th>组成</th>
        <th>说明</th>
        <th>支持写法</th>
        <th>备注</th>
    </tr>
    <tr>
        <td>mockapi</td>
        <td>合法命令须以该单词开头</td>
        <td>mockapi</td>
        <td>【必】</td>
    </tr>
    <tr>
        <td>cmd</td>
        <td>代表本次操作的动作，目前支持get/list/json。<br>get表示需要一个元素；list表示需要一个集合；json表示需要自定义json结构。</td>
        <td>get<br>list<br>json</td>
        <td>【必】</td>
    </tr>
    <tr>
        <td>type</td>
        <td>表示元素类型。目前支持integer/float/boolean/time/text/image/video。<br>分别代表 整型/浮点型/布尔值/时间戳/文本/图片url/视频url。<br>其中text/image/video都可认为是string类型，各自代表的含义不同罢了。</td>
        <td>integer int<br>float<br>boolean bool<br>time timestamp<br>text txt<br>image img photo picture<br>video</td>
        <td>cmd=get/list【必】</td>
    </tr>
    <tr>
        <td>topic</td>
        <td>表示一个string类型的元素的具体指向类型，多个主题以<strong>英文逗号</strong>连接。<br>当type=text时，支持name,soup<br>当type=image时，支持animal,banner,boy,car,food,girl,landscape,plant<br>当type=video时，支持tiktok,music</td>
        <td>topic=animal<br>animal<br>topic=animal,boy<br>animal,boy</td>
        <td>type=string【选填】<br>默认所有</td>
    </tr>
    <tr>
        <td>limit</td>
        <td>数值元素的上下区间，当type=integer/float有效。需要以英文圆括号包裹，包含两个端点。</td>
        <td>(min,max)<br>即(0,1024)<br>或(-100,100)</td>
        <td>type=integer/float<br>【选填】<br>默认(0,100)</td>
    </tr>
    <tr>
        <td>count</td>
        <td>当cmd=get/list时，代表返回的元素个数。<br>当cmd=json且不用模板时，将指定的json数据作为元素，返回count值个元素的集合。<br></td>
        <td>-count=n<br>n<br><br>n为非0整数</td>
        <td>【选填】</td>
    </tr>
    <tr>
        <td>hit</td>
        <td>本次请求能够正常返回的几率，以介于0-1之间的浮点数标识，包含0和1。</td>
        <td>ok=n<br>hit=n<br>n<br><br>n为浮点数</td>
        <td>【选填】<br>默认1</td>
    </tr>
    <tr>
        <td>delay</td>
        <td>模拟本次接口的调用耗时，单位为秒</td>
        <td>delay=n</td>
        <td>【选填】<br>默认0：即时返回</td>
    </tr>
    <tr>
        <td>file</td>
        <td>当使用json命令时，需要指定一个json文件，MockApi将该文件的内容返回。<br><a href="http://www.baidu.com">了解更多</a></td>
        <td>file=xxx<br>path=xxx</td>
        <td>cmd=json【必填】</td>
    </tr>
    <tr>
        <td>format</td>
        <td>当type=time时，指定时间的展示样式，需用大括号包裹。参见<a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html" target="_blank">SimpleDateFormat</a>。</td>
        <td>{yyyy年MM月dd日 HH时mm分ss秒}</td>
        <td>【选填】</td>
    </tr>
</table>


#### 命令一览

**MockApi**已支持的命令如下表所示：

<table>
    <tr>
        <th>cmd</th>
        <th>type</th>
        <th>file</th>
        <th>limit</th>
        <th>count</th>
        <th>topic</th>
        <th>format</th>
        <th>hit</th>
        <th>delay</th>
    </tr>
    <tr>
        <td rowspan="7">get/list</td>
        <td>integer</td>
        <td>-</td>
        <td>(min,max)</td>
        <td>count=n</td>
        <td>-</td>
        <td>-</td>
        <td>hit=x</td>
        <td>delay=s</td>
    </tr>
    <tr>
        <td>get</td>
        <td>float</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>get</td>
        <td>boolean</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>get</td>
        <td>time</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>-</td>
        <td>{format}</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>get</td>
        <td>text</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>topic,topic...</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>get</td>
        <td>image</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>get</td>
        <td>video</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td rowspan="7">json</td>
        <td>-</td>
        <td>xxx.txt</td>
        <td>-</td>
        <td>同上</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>json</td>
        <td>-</td>
        <td>xxx.json</td>
        <td>-</td>
        <td>同上</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
    <tr>
        <td>json</td>
        <td>-</td>
        <td>xxx.ftl</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>同上</td>
        <td>同上</td>
    </tr>
</table>