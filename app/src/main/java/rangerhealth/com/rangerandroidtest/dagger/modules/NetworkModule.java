package rangerhealth.com.rangerandroidtest.dagger.modules;

/**
 * Created by carlos on 10/28/17.
 */

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rangerhealth.com.rangerandroidtest.dagger.AppScope;
import rangerhealth.com.rangerandroidtest.network.MuffinLabsApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "http://namey.muffinlabs.com/";

    @AppScope
    @Provides
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }


    @AppScope
    @Provides
    public MuffinLabsApi providesMuffinLabsApi(Retrofit retrofit) {
        return retrofit.create(MuffinLabsApi.class);
    }

    @AppScope
    @Provides
    public Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    @AppScope
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @AppScope
    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.i("HttpLoggingInterceptor", message))
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
    }


    @AppScope
    @Provides
    public Cache cache(Context context, @Named("OkHttpCacheDir") String cacheDir, @Named("OkHttpCacheSize") int cacheSize) {
        return new Cache(new File(context.getFilesDir(), cacheDir), cacheSize);
    }

    @AppScope
    @Provides
    @Named("OkHttpCacheDir")
    public String cacheDir() {
        return "OkHttpCache";
    }

    @AppScope
    @Provides
    @Named("OkHttpCacheSize")
    public int cacheSize() {
        return 10 * 1024 * 1024; //10MB cache
    }

}
