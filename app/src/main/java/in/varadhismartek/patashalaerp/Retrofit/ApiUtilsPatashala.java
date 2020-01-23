package in.varadhismartek.patashalaerp.Retrofit;

public class ApiUtilsPatashala {

    private ApiUtilsPatashala() {
    }


    public static final String PATASHALA_BASE_URL = "http://13.233.109.165:8000/patashala/";

    public static APIService getService() {

        return ApiClientPataShala.getClient(PATASHALA_BASE_URL).create(APIService.class);
    }


}
