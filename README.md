# idcard-util

轻量级开箱即用身份证解析工具

## 1.加载文件

非pom项目会自动把com.zsunreal.utils.idcard.file目录下的文件编译，pom项目需要用resources标签

```xml
<!--除*.java外都打包-->
<resource>
	    <directory>src/main/java</directory>
	    <excludes>
	      <exclude>**/*.java</exclude>
	    </excludes>
</resource>
```

## 2.身份证解析工具IdcardUtil

```java
//获取省
System.out.println(IdcardUtil.getProvince("53"));
//获取市
System.out.println(IdcardUtil.getCity("5321"));
//获取区/县
System.out.println(IdcardUtil.getArea("532122"));
//获取年龄
System.out.println(getAgeByCertNo("532122xxxxxxxxxx17"));
//获取性别
System.out.println(getSexByCertNo("532122xxxxxxxxxx17"));
//获取地址
System.out.println(IdcardUtil.getAddressByCertNo("532122xxxxxxxxxx17"));
```

## 3.身份证校验工具IdcardValidator

```java
//判断18位身份证的合法性
System.out.println(IdcardValidator.isValidate18Idcard("53"));
```
