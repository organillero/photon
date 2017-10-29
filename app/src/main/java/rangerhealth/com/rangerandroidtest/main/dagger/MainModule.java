package rangerhealth.com.rangerandroidtest.main.dagger;

import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import rangerhealth.com.rangerandroidtest.main.mvp.MainAdapter;
import rangerhealth.com.rangerandroidtest.main.mvp.MainModel;
import rangerhealth.com.rangerandroidtest.main.mvp.MainPresenter;
import rangerhealth.com.rangerandroidtest.main.mvp.MainView;
import rangerhealth.com.rangerandroidtest.network.MuffinLabsApi;

/**
 * Created by carlos on 10/28/17.
 */

@Module
public class MainModule {

    private final AppCompatActivity activity;

    public MainModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @MainScope
    public MainAdapter providesMapAdapter (Picasso picasso){
        return new MainAdapter(picasso);
    }

    @Provides
    @MainScope
    public MainModel providesMapModel (MuffinLabsApi api){
        return new MainModel(api);
    }

    @Provides
    @MainScope
    public MainPresenter providesMapPresenter (MainModel model, MainView view){
        return new MainPresenter(model, view);
    }

    @Provides
    @MainScope
    public MainView providesMapView(MainAdapter adapter){
        return new MainView(activity, adapter);
    }

}
