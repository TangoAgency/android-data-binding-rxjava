# android-data-binding-rxjava

Simple demo which demonstrates how to wrap Android DataBinding ObservableField into RxJava's Observable.

With this solution it is possible to register for ObservableField's value changes and use it with RxJava operators.

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

You find ```toObservable``` method implementation in [RxUtils.java](https://github.com/TangoAgency/android-data-binding-rxjava/blob/master/app/src/main/java/agency/tango/databindingrxjava/RxUtils.java) class.

It uses ObservableField's ```OnPropertyChangedCallback``` and expose property change events to the "RxWorld".
