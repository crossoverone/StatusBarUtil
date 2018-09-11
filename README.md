网上看了很多沉浸式，感觉用起来麻烦，而且有些库非常大，于是自己写了个简单轻量的、高度自定义的并且可以适配4.4以上99%机型的工具。

注：状态栏字体颜色需要6.0以上才有效果，6.0以下默认白色。于是我加了个兼容的方法setUseStatusBarColor（Activity activity, @ColorInt int color, int surfaceColor），解决了头部全白的问题。

主要提供了三个方法：
    
    // 第二个参数是状态栏色值。
    // 第三个参数是兼容5.0到6.0之间的状态栏颜色字体只能是白色，如果沉浸的颜色与状态栏颜色冲突, 设置一层浅色对比能显示出状态栏字体（可以找ui给一个合适颜色值）。
	// 如果您的项目是6.0以上机型或者某些界面不适用沉浸, 推荐使用两个参数的setUseStatusBarColor。
	StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));

    // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
    setSystemStatus（Activity activity, boolean isTransparent, boolean isBlack）;
	
	// 该类是经过本人修改的,需要到请使用statuslib里面的。
	AndroidBug5497Workaround.assistActivity(this);
     如果用到，请在清单文件该activity也加上：android:windowSoftInputMode="adjustResize"
	

废话不多说，开始介绍使用方法。

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
	       compile 'com.github.crossoverone:StatusBarUtil:1.3.3'//会不定时更新，请及时更新
	}


## 使用(细节请参照demo) ##
### 一、activity中简单使用 ###
	public  class  MainActivity extends AppCompatActivity {
		...
		@Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			...
	        setContentView(R.layout.activity_main);

			// 第二个参数是状态栏色值。
    		// 第三个参数是兼容5.0到6.0之间的状态栏颜色字体只能是白色，如果沉浸的颜色与状态栏颜色冲突, 设置一层浅色对比能显示出状态栏字体（可以找ui给一个合适颜色值）。
			// 如果您的项目是6.0以上机型或者某些界面不适用沉浸, 推荐使用两个参数的setUseStatusBarColor。
			StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));

			// 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
			StatusUtil.setSystemStatus(this, false, true);
			...
	    }
		...
	}
    

### 二、BaseActivity(基类)中使用 ###
	public abstract class  BaseActivity extends AppCompatActivity {
		...
		@Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			...
	        setContentView(getLayoutRes());

			//基类设置默认值,这里是非沉浸,状态栏颜色值#878787,字体颜色为黑色。
			setStatusColor();
			setSystemInvadeBlack();
			...
	    }
		...
		protected void setStatusColor() {
			StatusUtil.setUseStatusBarColor(this, Color.parseColor("#878787"));
	    }
		...
		protected void setSystemInvadeBlack() {
			// 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
			StatusUtil.setSystemStatus(this, false, true);
	    }
		...
	}
	
子类需要修改则分别重写该方法(假设需要换个状态栏颜色 & 设置沉侵式 & 字体为白色)。

	public class MainActivity extends BaseActivity {
		...
		@Override
		protected void setStatusColor() {
			StatusUtil.setUseStatusBarColor(this, Color.parseColor("#252525"));
	    }
		...
		@Override
		protected void setSystemInvadeBlack() {
			// 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
			StatusUtil.setSystemStatus(this, true, false);
	    }
		...
	}

### 三、设置沉侵的activity布局预留状态栏高度两种方法（选其中一种即可） 
	
#### 1.xml中设置，灵活性也不是很高，但实现简单（题外话：约束布局确实不错，推荐大家使用）  
加上一句：android:fitsSystemWindows="true" 。

	<?xml version="1.0" encoding="utf-8"?>
    <android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true"
        xmlns:app="http://schemas.android.com/apk/res-auto">

        .........
    
    </android.support.constraint.ConstraintLayout>

#### 2.activity中设置，灵活度高，写的代码就比较多 ####

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		...
	    setContentView(getLayoutRes());

		// 第二个参数是状态栏色值。
    	// 第三个参数是兼容5.0到6.0之间的状态栏颜色字体只能是白色，如果沉浸的颜色与状态栏颜色冲突, 设置一层浅色对比能显示出状态栏字体（可以找ui给一个合适颜色值）。
		// 如果您的项目是6.0以上机型或者某些界面不适用沉浸, 推荐使用两个参数的setUseStatusBarColor。
		StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));

		StatusUtil.setSystemStatus(this, true, true);
		initTitle();
		...
	}

	private void initTitle(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			xxxxxx.LayoutParams layoutParams = new xxxxxx.LayoutParams(xxxxxx.LayoutParams.MATCH_PARENT, xxxxxx.LayoutParams.MATCH_PARENT);
	        layoutParams.setMargins(0, StatusUtil.getStatusBarHeight(this.getActivity()), 0, 0);
			//顶部距离
	        titleView.setLayoutParams(layoutParams);	
		}
	}

### 四、界面是ViewPager+fragment联合使用 ###
#### 1.使用方式 ####

设置监听,可以根据需求设置状态栏字体颜色为黑色或白色:
	
	mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            ...
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        isBlack = false;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                    case 1:
                        isBlack = true;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                    case 2:
                        isBlack = false;
                        StatusUtil.setSystemStatus(MainActivity.this, true, isBlack);
                        break;
                }
            }
			...
        });

#### 2.注意事项 ####


#### fragmentA 需要沉浸，fragmentB不需要沉浸使用时，要固定第二个参数为true，否则布局会上下移动： ####

	activity中：

	public class MainActivity extends BaseActivity {
	   ...
	    @Override
	    protected void setSystemInvadeBlack() {
	        // 第二个参数保持不变,第三个参数是状态栏字体是否为黑色
	        StatusUtil.setSystemStatus(this, true, true);
	    }
	   ...
	}

##### 不需要沉浸的fragment几种设置方法（选其中一种即可）: #####

~~（1）.xml可以设置layout_marginTop=“25dp”：(属于偏方，虽然可以快速实现，但不是完美的解决方式)~~

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:background="@color/color_f8f8f8"
	    android:orientation="vertical">
	
	    <FrameLayout
            android:id="@+id/titleView"
	        android:layout_width="match_parent"
	        android:layout_height="45dp"
	        android:layout_marginTop="25dp"
	        android:background="@color/color_f8f8f8">

	        <TextView
	            android:id="@+id/tv_title"
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:layout_gravity="center"
	            android:gravity="center"
	            android:text="@string/main_title"
	            android:textSize="16sp"/>

	    </FrameLayout>
	</LinearLayout>

（2）.在fragment初始化view的时候（灵活度比较高，但是写起来比较繁琐）：

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ···
        mInflateview =···;
		···
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                xxxxxx.LayoutParams layoutParams = new  xxxxxx.LayoutParams(xxxxxx.LayoutParams.MATCH_PARENT, xxxxxx.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, StatusUtil.getStatusBarHeight(this.getActivity()), 0, 0);
		        //顶部距离
                titleView.setLayoutParams(layoutParams);
		}
        return mInflateview;

    }

（3）.在fragment初始化view的时候（实现简单，但有一定的局限性）：

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ···
        mInflateview =···;
		···	
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            mInflateview .setPadding(0, StatusUtil.getStatusBarHeight(mContext), 0, 0);
        }
        ···
        return mInflateview;

    }

### 五、类似聊天窗口（底部有输入框）使用方式 ###
#### 先上代码，再说明，分两步：

###### 1.AndroidManifest.xml的指定activity加上一行。例如：
	<activity 
		android:name="crossoverone.activity.FirstActivity"
        android:windowSoftInputMode="adjustResize"//加上这一行
	/>

###### 2.在activity的setContentView之后，使用statusUtil之前。例如：
	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        AndroidBug5497Workaround.assistActivity(this);//加上这一行

		// 第二个参数是状态栏色值。
    	// 第三个参数是兼容5.0到6.0之间的状态栏颜色字体只能是白色，如果沉浸的颜色与状态栏颜色冲突, 设置一层浅色对比能显示出状态栏字体（可以找ui给一个合适颜色值）。
		// 如果您的项目是6.0以上机型或者某些界面不适用沉浸, 推荐使用两个参数的setUseStatusBarColor。
		StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
		StatusUtil.setSystemStatus(this, true, true);
       
	}	

#### 如果在baseActivity 已经封装过了,在子类中重写setStatusColor方法。
	protected void setStatusColor() {
		AndroidBug5497Workaround.assistActivity(this);//加上这一行,一定要在第一行
		StatusUtil.setUseStatusBarColor(this, Color.parseColor("#878787"));
	}

#### 注：当前界面的固定显示内容要小于等于弹出软键盘之后的布局高度，否则软键盘弹出后将遮盖部分布局。
