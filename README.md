# What is Viewpager View Updater
## Init FragmentActivity
This function enables you to load the next page only when you move to viewpager.

```java
private ActivityMainBinding binding;

override fun onCreate(savedInstanceState: Bundle?) {  
    super.onCreate(savedInstanceState)  
  
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)  
    val pages = FragmentPagerItems(this)  
    pages.add(FragmentPagerItem.of("페이지 1", DummyFragment1::class.java))  
    pages.add(FragmentPagerItem.of("페이지 2", DummyFragment2::class.java))  
    pages.add(FragmentPagerItem.of("페이지 3", DummyFragment3::class.java))  
    pages.add(FragmentPagerItem.of("페이지 4", DummyFragment4::class.java))  
    val adapter = FragmentPagerItemAdapter(supportFragmentManager, pages)  
    binding!!.viewPager.adapter = adapter  
  
    //update 발생시 event를 전달하는 update listener 등록  
	binding!!.viewPager.addOnPageChangeListener(ViewPagerUpdateListener(this::class))
}
```
FragmentPagerItems is library of "com.ogaclejapan.smarttablayout:utils-v4"


<br><br>


# Setup
Setting is simple. You can set Viewpager on ViewPagerUpdater

## viewpager setting
```java
putViewPagers(pages)
```


## Setting up a Fragment class
The Fragment to use must extends ViewPagerViewUpdaterFragmentBase and implement the onUpdate() method.
```java
class DummyFragment : ViewPagerUpdateFragmentBase() {
  ...
	override fun onUpdate() {  
	  Log.i("suein", "DummyFragment view updated")  
	}
}
```

## Using
The update flag setting ensures that the next page is updated when it is displayed. Pages are grouped by registered Key Class name. The screen update is updated based on the key class name, and is used as the name of the current class in which the method is called or when this parameter is updated.


Set all registered all pages update flags
```kotlin
setOnUpdateViewAll(MainActivity::class) // MainActivity::class is key class
setOnUpdateViewAll()
```
Set the update flag for one page
```kotlin
setOnUpdateView(DummyFragment1::class)  
setOnUpdateView(MainActivity::class, DummyFragment1::class) //MainActivity::class is key class
```

Execute an update of a specific page(fragment). Only available if the status is greater than onResume
```kotlin
forceUpdateView(1) // 1 is fragment index
forceUpdateView(MainActivity::class, 2) //MainActivity::class is key class, 2 is fragment index
```


<br><br>
## Gradle setting
### root build.gradle
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
### dependency
```java
dependencies {
  compile 'com.github.Suein1209:ViewpagerViewUpdater:0.0.8'
}
```
