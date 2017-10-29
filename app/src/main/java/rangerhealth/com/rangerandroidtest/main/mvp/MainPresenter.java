package rangerhealth.com.rangerandroidtest.main.mvp;

import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by carlos on 10/28/17.
 */

public class MainPresenter {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    private final MainView view;
    private final MainModel model;

    public MainPresenter(MainModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public void onCreate(){
        compositeSubscription.add(retrieveUsersSubscription());
    }

    public void onDestroy(){
        compositeSubscription.clear();
    }

    private Subscription retrieveUsersSubscription() {
        return model.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.setUsers(users),
                        error -> Log.e("Error", error.getLocalizedMessage())
                );
    }
}
