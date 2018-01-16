# android 4.4以上实现沉浸式 #

----------

## 引入项目 ##
### 1.工程的gradle ###

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### 2.module的gradle ###
	dependencies {
	        compile 'com.github.crossoverone:StatusUtil:1.2'
	}


## 使用(细节请参照demo) ##
### activity中简单使用 ###
	public  class  MainActivity extends AppCompatActivity {
		...
		@Override
	    	protected void onCreate(@Nullable Bundle savedInstanceState) {
	        	super.onCreate(savedInstanceState);
			...
	        	setContentView(R.layout.activity_main);
			StatusUtil.setTransparentStatusBar(this);// 沉侵式需要设置透明状态栏
			StatusUtil.setSystemStatus(this, false, true);// 第二个参数是是否沉侵,第三个参数是状态栏字体是否为黑色
			...
	    	}
		...
	}
    

### BaseActivity(基类)中使用 ###
	public abstract class  BaseActivity extends AppCompatActivity {
		...
		@Override
	    	protected void onCreate(@Nullable Bundle savedInstanceState) {
	        	super.onCreate(savedInstanceState);
			...
	        	setContentView(getLayoutRes());
			setStatusTransparent();
			//默认非沉侵,状态栏颜色值#878787,字体颜色为黑色
			setStatusColor();
			setSystemInvadeBlack();
			...
	    	}
	
		...
		protected void setStatusTransparent() {
			StatusUtil.setTransparentStatusBar(this);// 沉侵式需要设置透明状态栏
	    	}
		...
		protected void setStatusColor() {
			StatusUtil.setUseStatusBarColor(this, Color.parseColor("#878787"));
	    	}
		...
		protected void setSystemInvadeBlack() {
			// 第二个参数是是否沉侵,第三个参数是状态栏字体是否为黑色
			StatusUtil.setSystemStatus(this, false, true);
	    	}
		...
	}
	
子类需要修改则分别重写该方法(假设需要换个状态栏颜色 & 设置沉侵式 & 字体为白色)

	public class MainActivity extends BaseActivity {
		...
		@Override
		protected void setStatusColor() {
			StatusUtil.setUseStatusBarColor(this, Color.parseColor("#252525"));
	    	}
		...
		@Override
		protected void setSystemInvadeBlack() {
			// 第二个参数是是否沉侵,第三个参数是状态栏字体是否为黑色
			StatusUtil.setSystemStatus(this, true, false);
	    	}
		...
	}





















