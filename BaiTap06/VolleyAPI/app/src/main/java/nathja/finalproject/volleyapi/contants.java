package nathja.finalproject.volleyapi;

public class contants {
    public static String localhost = "app.iotstar.vn:8081";
    //login và register API
    private static final String ROOT_URL = "http://" + localhost +
            "/shoppingapp/registrationapi.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
}