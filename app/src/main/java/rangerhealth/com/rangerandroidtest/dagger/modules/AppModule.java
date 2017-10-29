package rangerhealth.com.rangerandroidtest.dagger.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import rangerhealth.com.rangerandroidtest.dagger.AppScope;

/**
 * Created by carlos on 10/28/17.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public Context context(){
        return context;
    }

}
