package agency.tango.databindingrxjava;


import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

import static android.databinding.Observable.OnPropertyChangedCallback;

public class RxUtils {

    private RxUtils() {
    }

    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> observableField) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                subscriber.onNext(observableField.get());

                final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(android.databinding.Observable dataBindingObservable, int propertyId) {
                        if (dataBindingObservable == observableField) {
                            subscriber.onNext(observableField.get());
                        }
                    }
                };

                observableField.addOnPropertyChangedCallback(callback);

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        observableField.removeOnPropertyChangedCallback(callback);
                    }
                }));
            }
        });
    }
}
