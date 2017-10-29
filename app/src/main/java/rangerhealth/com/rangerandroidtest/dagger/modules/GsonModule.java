package rangerhealth.com.rangerandroidtest.dagger.modules;

/**
 * Created by carlos on 10/28/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import rangerhealth.com.rangerandroidtest.dagger.AppScope;

@Module
public class GsonModule {

    @AppScope
    @Provides
    public Gson providesGson (){
        return new GsonBuilder().create();

    }
}
