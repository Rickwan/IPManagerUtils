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
	   compile 'com.github.Rickwan:IPManagerUtils:V1.0.7'
	}
```
------
> 分割线----装逼失败  

## 使用方法：

- 使用摇一摇触发IP设置功能：
在需要使用摇一摇功能的Activity的onStart()方法中注册、在onStop()方法中取消注册摇一摇功能。

```

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatWindowUtils floatWindowUtils= new FloatWindowUtils();
        floatWindowUtils.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
	MBShakeUtils.getInstance(this).init();
		
    }

    @Override
    protected void onStop() {
        super.onStop();
	MBShakeUtils.getInstance(this).unRegister();
      
    }

```

- 设置IP：  
 
 ``` 
Intent intent = new Intent(this, MBIPActivity.class);
startActivityForResult(intent, MBIPContant.REQUEST_CODE);
```

- 获取IP:(返回示例：192.168.1.33:8080)
 
- 方法1：设置成功后，可通过resultCode == MBIPContant.RESULT_CODE在onActivityResult()方法中获取IP,
 ```
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MBIPContant.RESULT_CODE) {

            MBIPInfo info = (MBIPInfo) data.getSerializableExtra(MBIPContant.IP);
            ipView.setText("新IP地址：" + info.ip + ":" + info.port);
        }
    }
 ```
 
- 方法2：获取已设置的默认IP  
    
    ```
    MBIPUtils.getInstance(context).getIPPort();
    ```
- 方法3：当未设置IP时，可传入默认IP  
    
    ```
     MBIPUtils.getInstance(context).getIPPort(defeaultIP,defeaultPort);
    ```
    
