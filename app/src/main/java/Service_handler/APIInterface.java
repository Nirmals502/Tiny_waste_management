package Service_handler;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by iapp on 12/7/17.
 */

public interface APIInterface {

    @POST("UpdateJobStepMobile")
    Call<String> CallApi(@Body Tidy_feedback loginresult);
}
