package rangerhealth.com.rangerandroidtest.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import rangerhealth.com.rangerandroidtest.RangerApplication;
import rangerhealth.com.rangerandroidtest.main.dagger.DaggerMainComponent;
import rangerhealth.com.rangerandroidtest.main.dagger.MainModule;
import rangerhealth.com.rangerandroidtest.main.mvp.MainPresenter;
import rangerhealth.com.rangerandroidtest.main.mvp.MainView;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainView view;

    @Inject
    MainPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerMainComponent.builder()
                .appComponent( ((RangerApplication) this.getApplication()).getComponent() )
                .mainModule(new MainModule(this))
                .build()
                .inject(this)
        ;

        setContentView(view);
        presenter.onCreate();

        Log.d("COOL", "ok this works");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
