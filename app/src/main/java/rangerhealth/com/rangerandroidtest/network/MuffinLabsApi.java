package rangerhealth.com.rangerandroidtest.network;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by carlos on 10/28/17.
 */

public interface MuffinLabsApi {



    @GET("name.json?count=10")
    Observable<List<String>> getNames();
}
