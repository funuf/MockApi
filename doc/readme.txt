//        new File("/").list() // D://下的直接文件（夹）
//        new File(".") // pom.xml所在文件夹下所有内容

git
https://git-scm.com

idea
https://www.jetbrains.com/idea/documentation/

maven
https://maven.apache.org/

spring
https://spring.io/projects/spring-boot

okio
https://github.com/square/okio

freemarker
https://freemarker.apache.org/
http://freemarker.foofun.cn/toc.html

json
https://www.json.org/json-en.html

fastjson
https://github.com/alibaba/fastjson

java
https://docs.oracle.com/javase/8/docs/api/overview-summary.html
https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html

lombok
https://projectlombok.org/setup/maven

rxjava
https://github.com/ReactiveX/RxJava


// list命令：limit默认=20  非法limit（小于1）时，按20返回    topic默认为没有分类，分类值可单可多
// eg:   mockapi list text --count=n --topic=name,opetry,soup  --ok=0.35
// eg:   mockapi list image --count=n --topic=animal,boy,car,food,girl,landscape,plant  --ok=0.35
// eg:   mockapi list video --count=n --topic=music,tiktok  --ok=0.35
// eg:   mockapi list integer  --count=n --limit=[-123,123]
// eg:   mockapi list float --count=n --limit=[-123.456,123.456]
// eg:   mockapi list boolean  --count=n
// eg:   mockapi list time --count=n   --format={}

// get命令：默认返回一个，count>1时，等同于list命令  topic默认为没有分类，分类值可单可多
// eg:   mockapi get text --topic=name|opetry|xiongpeiyun  --ok=0.35
// eg:   mockapi get image --topic=animal|boy|car|food|girl|landscape|plant  --ok=0.35
// eg:   mockapi get video  --topic=music,tiktok  --ok=0.35
// eg:   mockapi list integer  --limit=[-123,123]
// eg:   mockapi list float --limit=[-123.456,123.456]
// eg:   mockapi list boolean
// eg:   mockapi list time  --format={}

// json命令：返回json文件内容/根据json模板返回填充后的数据   {type}
// eg:   mockapi json  --file=(全文件名/文件名)  --path=D:..123.234.789  --ok=0.35  --count=n（.ftl无效）

// 下一个版本
// 配置项
// eg:   mockapi config --json.path=object和array命令所需文件的父目录 --template.path=json模板所需文件的父目录 --count=30 --ok=0.35
// 下下一个版本
// mockapi append text/image/video --topic=...  --file=存储数据的文件(全文件名/文件名)   --path=存储数据的文件所需的父目录
// mockapi clear text/image/video --topic=...


卸载windows服务：  sc delete serverName