# Language
 - [한글](https://github.com/Suein1209/ViewpagerViewUpdater/blob/master/README-kr.md)

# What is Viewpager View Updater
viewpager로 스와핑시에만 다음 페이지를 읽도록 하는 기능이다.

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
FragmentPagerItems는 com.ogaclejapan.smarttablayout:utils-v4 라이브러리다
<br><br>

# Setup
viewpager 설정은 단순하다. viewpager를 설정하면 된다.

## viewpager 설정
```java
ViewPagerUpdater.getInstance().setViewPager(binding.viewPager);
```

## viewpager updater 범주에 포함시키지 않도록 설정
viewpager view updater는 스와핑시에만 스와핑해서 보여질 페이지의 update 하는것이 목적이지만<br>
이 사이클에서 제외 할 수 있다.
```java
ViewPagerUpdater.getInstance().setWithoutPage(2, 3);
```

## 사용하게될 Fragment의 설정
사용할 Fragment의 경우에는 ViewPagerViewUpdaterFragmentBase 상속을 받아야 onUpdate() 메소드를 구현해야 한다
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

## update 시간 설정
시간은 매번 스와핑시 화면을 update 하는 것이 아닌 일정 시간이 지나야 update 되도록 설정 할 수 있다.
```java
ViewPagerUpdater.getInstance().setUpdateTime(10000);
```
<br><br>
## Gradle 설정
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
