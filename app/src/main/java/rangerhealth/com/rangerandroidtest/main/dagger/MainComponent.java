package rangerhealth.com.rangerandroidtest.main.dagger;

import dagger.Component;
import rangerhealth.com.rangerandroidtest.dagger.AppComponent;
import rangerhealth.com.rangerandroidtest.main.MainActivity;

/**
 * Created by carlos on 10/86/17.
 */

@MainScope
@Component( modules = {MainModule.class},
        dependencies = AppComponent.class)

public interface MainComponent {
    void inject(MainActivity activity);
}
