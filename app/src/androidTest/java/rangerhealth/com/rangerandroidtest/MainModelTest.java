package rangerhealth.com.rangerandroidtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Observable;

import rangerhealth.com.rangerandroidtest.dagger.AppComponent;
import rangerhealth.com.rangerandroidtest.dagger.DaggerAppComponent;
import rangerhealth.com.rangerandroidtest.dagger.modules.AppModule;
import rangerhealth.com.rangerandroidtest.main.mvp.MainModel;
import rangerhealth.com.rangerandroidtest.network.User;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainModelTest {

    private MainModel model;

    @Before
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext))
                .build();

        model =  new MainModel(appComponent.api());
    }

    @Test
    public void modelnotNull() throws Exception {
        assertNotNull(model);
    }

    @Test
    public void checkModelCache() throws Exception {

        List<User> userList1 =  model.getUsers().toBlocking().first();
        List<User> userList2 =  model.getUsers().toBlocking().first();
        
        assertEquals(userList1, userList2);
    }

}
