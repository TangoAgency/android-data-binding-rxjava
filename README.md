# android-data-binding-rxjava

[![Android Arsenal android-data-binding-rxjava](https://img.shields.io/badge/Android%20Arsenal-android--data--binding--rxjava-green.svg?style=true)](https://android-arsenal.com/details/3/4369)
[![Build Status](https://travis-ci.org/TangoAgency/android-data-binding-rxjava.svg?branch=master)](https://travis-ci.org/TangoAgency/android-data-binding-rxjava)

Simple demo developed with love at [Tango](http://tango.agency) which demonstrates how to wrap Android DataBinding ObservableField into RxJava's Observable.

With this solution it is possible to register for ObservableField's value changes and use it with RxJava operators.

You can read Medium story which explains this concept - [RxJava meets Android Data Binding](https://medium.com/tangoagency/rxjava-meets-android-data-binding-4ca5e1144107#.wv63halu1).

### Example code

```java
public static class MainViewModel {

        public ObservableField<String> firstName = new ObservableField<>();
        public ObservableField<String> lastName = new ObservableField<>();
        public ObservableField<String> helloText = new ObservableField<>();
        public ObservableBoolean helloButtonEnabled = new ObservableBoolean(false);

        public MainViewModel() {

            Observable.combineLatest(toObservable(firstName), toObservable(lastName), (firstName, lastName) -> StringUtils.isNotNullOrEmpty(firstName) && StringUtils.isNotNullOrEmpty(lastName))
                    .subscribe(result -> {
                        helloButtonEnabled.set(result);
                        if (!result) {
                            helloText.set(StringUtils.EMPTY);
                        }
                    }, Throwable::printStackTrace);
        }

        public void buttonClicked() {
            helloText.set(String.format("Hello %s %s !", firstName.get(), lastName.get()));
        }
}
```

![rxjava-databinding](https://cloud.githubusercontent.com/assets/469111/18312397/db8996fc-7509-11e6-9bcd-0cee0bac0754.gif)

### How it works

You can find ```toObservable``` method implementation in [RxUtils.java](https://github.com/TangoAgency/android-data-binding-rxjava/blob/master/app/src/main/java/agency/tango/databindingrxjava/RxUtils.java) class.

It uses ObservableField's ```OnPropertyChangedCallback``` and expose property change events to the "RxWorld".
