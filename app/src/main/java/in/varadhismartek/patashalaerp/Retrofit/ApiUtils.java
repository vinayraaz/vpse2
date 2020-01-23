package in.varadhismartek.patashalaerp.Retrofit;



/**
 * Created by varadhi on 10/3/18.
 */

public class ApiUtils {



    private ApiUtils() {}

    public static final String BASE_URL = "http://13.233.109.165:8000/school/";


    public static APIService getAPIService() {

        return ApiClient.getClient(BASE_URL).create(APIService.class);
    }



}
