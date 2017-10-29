package rangerhealth.com.rangerandroidtest;

import android.app.Application;

import rangerhealth.com.rangerandroidtest.dagger.AppComponent;
import rangerhealth.com.rangerandroidtest.dagger.DaggerAppComponent;
import rangerhealth.com.rangerandroidtest.dagger.modules.AppModule;

/**
 * Created by carlos on 10/28/17.
 */

public class RangerApplication extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return appComponent;
    }

}
