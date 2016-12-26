# IPManagerUtils


## Description：

    Setting your IP dynamically in the project.

## To get a Git project into your build:

- Step 1. Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

- Step 2. Add the dependency：
```
  dependencies {
	   compile 'com.github.Rickwan:IPManagerUtils:V1.0.0'
	}
```
------
> 分割线----装逼失败  

## 使用方法：
 - 设置IP：  
 
 ``` 
Intent intent = new Intent(this, MBIPActivity.class);
startActivityForResult(intent, MBIPContant.REQUEST_CODE);
```

- 获取IP:(返回示例：192.168.1.33:8080)

  - 方法1：获取IP  
    
    ```
    MBIPUtils.getInstance(context).getIPPort();
    ```
  - 方法2：当未设置IP时，可传入默认IP  
    
    ```
     MBIPUtils.getInstance(context).getIPPort(defeaultIP,defeaultPort);
    ```
