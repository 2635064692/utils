#JsonBeans

将对应json格式的字符串 转换成相应的java文件
再通过javac -cp命令行将java文件编译成class文件
最后将其打成jar包
   
命令打包生成 jar 文件 到 target/JsonBeans; 在 jsonbeans.txt 中配置 json 数据
 
jsonbeans.txt 配置说明:
    
第一行 配置包名和类名
    
    jerry.test AppInfo
    
第二行 开始配置 json 具体数据
        
通过 
       
    java -jar JsonBeans.jar              

执行生成 Java Bean 类    
