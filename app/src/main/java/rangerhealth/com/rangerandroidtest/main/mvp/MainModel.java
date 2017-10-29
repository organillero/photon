package rangerhealth.com.rangerandroidtest.main.mvp;

import android.content.SharedPreferences;

import java.util.List;

import rangerhealth.com.rangerandroidtest.network.MuffinLabsApi;
import rangerhealth.com.rangerandroidtest.network.User;
import rx.Observable;

/**
 * Created by carlos on 10/28/17.
 */

public class MainModel {

    private final MuffinLabsApi api;
    private List<User> usersMemoryCache;

    public MainModel(MuffinLabsApi api) {
        this.api = api;
    }

    // Create our sequence for querying best available data
    public Observable<List<User>> getUsers() {
        return Observable.concat(
                memoryData(),
                networkData())
                .first( data -> data != null);
    }


    public Observable<List<User>> networkData() {
        return api.getNames()
                .flatMap(Observable::from)
                .map(User::new)
                .toList()
                .doOnNext( data -> usersMemoryCache = data)

                .compose(logSource("NETWORK"));
    }


    public Observable<List<User>> memoryData() {
        Observable<List<User>> observable = Observable.unsafeCreate(subscriber -> {
            subscriber.onNext(usersMemoryCache);
            subscriber.onCompleted();
        });

        return observable.compose(logSource("MEMORY"));
    }


    // Simple logging to let us know what each source is returning
    Observable.Transformer<List<User>, List<User>> logSource(final String source) {
        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data == null) {
                System.out.println(source + " does not have any data.");
            } else {
                System.out.println(source + " has the data you are looking for!");
            }
        });

    }

}