# Language
 - [한글](https://github.com/Suein1209/ViewpagerViewUpdater/blob/master/README-kr.md)

# What is Viewpager View Updater
This function enables you to load the next page only when you move to viewpager.

```java
private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("페이지 1", DummyFragment.class));
        pages.add(FragmentPagerItem.of("페이지 2", DummyFragment.class));
        pages.add(FragmentPagerItem.of("페이지 3", DummyFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);

        binding.viewPager.setAdapter(adapter);
        ViewPagerUpdater.getInstance().setViewPager(binding.viewPager);
    }
```
FragmentPagerItems is library of "com.ogaclejapan.smarttablayout:utils-v4"
<br><br>

# Setup
Setting is simple. You can set Viewpager on ViewPagerUpdater

## viewpager setting
```java
ViewPagerUpdater.getInstance().setViewPager(binding.viewPager);
```

## Exclude UI updates
ViewpagerViewUpdater to update UI only the page you will see but can also be excluded.
```java
ViewPagerUpdater.getInstance().setWithoutPage(2, 3);
```

## Setting up a Fragment class
The Fragment to use must extends ViewPagerViewUpdaterFragmentBase and implement the onUpdate() method.
```java
public class DummyFragment extends ViewPagerViewUpdaterFragmentBase {
  ...
    @Override
    public void onUpdate() {
        binding.tvCenterText.setText("View Updated");
        Log.e("suein", "DummyFragment = " + getPageIndex());
    }
}
```

## update time setting
The time setting does not update every time you move the page, but you can set it up so that it will not be updated until the set time has elapsed.
```java
ViewPagerUpdater.getInstance().setUpdateTime(10000);
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
  compile 'com.github.Suein1209:ViewpagerViewUpdater:0.0.2'
}
```
