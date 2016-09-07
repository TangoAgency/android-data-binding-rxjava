package agency.tango.databindingrxjava;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import agency.tango.databindingrxjava.databinding.ActivityMainBinding;
import rx.Observable;

import static agency.tango.databindingrxjava.RxUtils.toObservable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new MainViewModel());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }

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
}
