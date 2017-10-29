package rangerhealth.com.rangerandroidtest.dagger;

/**
 * Created by carlos on 10/28/17.
 */

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dagger.Component;
import rangerhealth.com.rangerandroidtest.dagger.modules.AppModule;
import rangerhealth.com.rangerandroidtest.dagger.modules.GsonModule;
import rangerhealth.com.rangerandroidtest.dagger.modules.NetworkModule;
import rangerhealth.com.rangerandroidtest.network.MuffinLabsApi;

@AppScope
@Component(modules = { AppModule.class , NetworkModule.class, GsonModule.class})
public interface  AppComponent {

    Picasso picasso();
    MuffinLabsApi api();

}
