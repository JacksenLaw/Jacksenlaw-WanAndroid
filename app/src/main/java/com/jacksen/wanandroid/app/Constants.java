package com.jacksen.wanandroid.app;

import android.graphics.Color;

import com.jacksen.wanandroid.R;

import java.io.File;

/**
 * @author Luo
 * @date 2018/11/9 0009
 */
public class Constants {

    public static final String MY_SHARED_PREFERENCE = "my_shared_preference";

    /**
     * url
     */
    public static final String COOKIE = "Cookie";

    /**
     * Path
     */
    public static final String PATH_DATA = WanAndroidApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";


    public static String ARG_PARAM1 = "param1";
    public static String ARG_PARAM2 = "param2";

    /**
     * Tag fragment classify
     */
    public static final int TYPE_MAIN_PAGER = 0;

    public static final int TYPE_KNOWLEDGE_PAGER = 1;

    public static final int TYPE_WX_ARTICLE_PAGER = 2;

    public static final int TYPE_NAVIGATION_PAGER = 3;

    public static final int TYPE_PROJECT_PAGER = 4;

    public static final int TYPE_COLLECT_PAGER = 5;

    public static final int TYPE_TODO_PAGER = 6;

    /**
     * Bottom Navigation tab classify
     */
    public static final int TAB_ONE = 0;

    /**
     * 网络 广播
     */
    public static String NETBROADCAST = "CONNECTIVITY_CHANGE";

    /**
     * ssl key
     */
    public static final String SSL_KEY = "-----BEGIN CERTIFICATE-----\n" + "MIIFjzCCBHegAwIBAgIQNILLl/rXAokv4nR6PLLyzjANBgkqhkiG9w0BAQsFADCB\n" + "lDELMAkGA1UEBhMCVVMxHTAbBgNVBAoTFFN5bWFudGVjIENvcnBvcmF0aW9uMR8w\n" + "HQYDVQQLExZTeW1hbnRlYyBUcnVzdCBOZXR3b3JrMR0wGwYDVQQLExREb21haW4g\n" + "VmFsaWRhdGVkIFNTTDEmMCQGA1UEAxMdU3ltYW50ZWMgQmFzaWMgRFYgU1NMIENB\n" + "IC0gRzIwHhcNMTcwOTEzMDAwMDAwWhcNMTgwOTEzMjM1OTU5WjAaMRgwFgYDVQQD\n" + "DA9jYW5nc2hlbmc3Ny5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n" + "AQCEHBJzUVQb3UYhmnFZGtPLhfI5y4qa6Fa2MdHItCYTefagp5PDC1H1A3j4sCl4\n" + "8/GqaIp0TTVRo1z7gXXtYPzXFJl1225Gt9Pcf2F3AMiHiDR3rfKztfCmvxQMW7jx\n" + "owLvrzZzQ+haSB2EgGCYjKgrWJv/5MoqIGZ5GwtTMFKe5WHjErKtL+Vua4pKr1xn\n" + "cxzKVMYiSz3IfJu/z3170JIXH+JjS80r7v2fCgb7/L5/nrXOPVt64IlQUBfSZGvm\n" + "AnNiJVrOlOkcgWZ7HCNtIqtxTO36HovD1FFPBSVO3LVO5+wH7ch6TmpO6Cn9/w2p\n" + "qmaIjhiPGMQU1Y9UdwepkIMHAgMBAAGjggJUMIICUDAvBgNVHREEKDAmgg9jYW5n\n" + "c2hlbmc3Ny5jb22CE3d3dy5jYW5nc2hlbmc3Ny5jb20wCQYDVR0TBAIwADBhBgNV\n" + "HSAEWjBYMFYGBmeBDAECATBMMCMGCCsGAQUFBwIBFhdodHRwczovL2Quc3ltY2Iu\n" + "Y29tL2NwczAlBggrBgEFBQcCAjAZDBdodHRwczovL2Quc3ltY2IuY29tL3JwYTAf\n" + "BgNVHSMEGDAWgBTKrF3hkC/x74zUnzUB4QE7oM7BdzAOBgNVHQ8BAf8EBAMCBaAw\n" + "HQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMFcGCCsGAQUFBwEBBEswSTAf\n" + "BggrBgEFBQcwAYYTaHR0cDovL2hkLnN5bWNkLmNvbTAmBggrBgEFBQcwAoYaaHR0\n" + "cDovL2hkLnN5bWNiLmNvbS9oZC5jcnQwggEEBgorBgEEAdZ5AgQCBIH1BIHyAPAA\n" + "dQDd6x0reg1PpiCLga2BaHB+Lo6dAdVciI09EcTNtuy+zAAAAV57VCbzAAAEAwBG\n" + "MEQCICUq+Io57se7PnccYREOe19i4WKMk1WUqCelHo+anA0XAiBg18Lc1N0R/wl2\n" + "787hUZe9eGM1ScDIxf9vO1Yt/eYMpAB3AKS5CZC0GFgUh7sTosxncAo8NZgE+Rvf\n" + "uON3zQ7IDdwQAAABXntUJyoAAAQDAEgwRgIhAJk9I0AUpwUsYqtLJdXZw6iXCN7x\n" + "w+bX3o0wgt/IJkWXAiEA3YYUZtwye7Hv0zFRnjbZcabqcxBkbVEG584Y4htwFY8w\n" + "DQYJKoZIhvcNAQELBQADggEBAHw5nxEXXnKLuzkNyQjSo2To6uhfxl/JKPsEGeDD\n" + "MyVMAu0MvdBi9+cvm8pRA0rU1Q0iBKE9aUhBP9haUjKeq0oJEY9VX9/Jdze2qTe2\n" + "2plQCwVMVii5ehwxOvG3x/0R+I/Rq/YCasdZbA0n1rGYh7KNoNjWH9y1c0nyK2Kk\n" + "fvA2g7llDoXJXI4CepjUGfLlm4OwLVPm9dhdNj9ZhfqYvgOU/PPm85YvCdPjqmkL\n" + "hho2yjXMnFWNnfPaVhe9hSDoesNG1OhmEj+7yjSoQRw99gUYyD1+pJk8vhBk/HVM\n" + "fFj7cE7nnvBXd9n2uRaZ3/Bvpo5YTcp8lQ/sJzqk0s/R9i8=\n" + "-----END CERTIFICATE-----";
    /**
     * MIDDLE_KEY
     */
    public static final String MIDDLE_KEY = "-----BEGIN CERTIFICATE-----\n" + "MIIFXDCCBESgAwIBAgIQGHf2ZWingrup+0La5E4z1zANBgkqhkiG9w0BAQsFADCB\n" + "vTELMAkGA1UEBhMCVVMxFzAVBgNVBAoTDlZlcmlTaWduLCBJbmMuMR8wHQYDVQQL\n" + "ExZWZXJpU2lnbiBUcnVzdCBOZXR3b3JrMTowOAYDVQQLEzEoYykgMjAwOCBWZXJp\n" + "U2lnbiwgSW5jLiAtIEZvciBhdXRob3JpemVkIHVzZSBvbmx5MTgwNgYDVQQDEy9W\n" + "ZXJpU2lnbiBVbml2ZXJzYWwgUm9vdCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTAe\n" + "Fw0xNjA2MDcwMDAwMDBaFw0yNjA2MDYyMzU5NTlaMIGUMQswCQYDVQQGEwJVUzEd\n" + "MBsGA1UEChMUU3ltYW50ZWMgQ29ycG9yYXRpb24xHzAdBgNVBAsTFlN5bWFudGVj\n" + "IFRydXN0IE5ldHdvcmsxHTAbBgNVBAsTFERvbWFpbiBWYWxpZGF0ZWQgU1NMMSYw\n" + "JAYDVQQDEx1TeW1hbnRlYyBCYXNpYyBEViBTU0wgQ0EgLSBHMjCCASIwDQYJKoZI\n" + "hvcNAQEBBQADggEPADCCAQoCggEBALBoKGatE+oJe9ZT5t7YXUWn15vacpx+XFcf\n" + "xIhQRX75qaio8fgcsmQkY2zNyWRickmM5laehuW7rE2uZzeaKEeYEiCsNGhtTk8X\n" + "d3subeM1AINYEregPSuFlDpn+h7Cyw8au/pH6z/lGRRAA7LCsgDjMMreDrq0WKqt\n" + "xtoZ6Q9DExdztytlCMFhEWOS/AixMmI/4Gu7S/wGD0dg86i8J0DRjdPq+NDbzk/+\n" + "vQsBEh0hGbsTKYi0owy23REgytHWMsZi+s6JXAW3eI0fgZ2bynaALE4GVu0tj8hk\n" + "wEqD0KWQ1Ay4M2z/3Jv17lZumTtv+qEH2aq8ayPIpqJ0qcdctrECAwEAAaOCAX0w\n" + "ggF5MBIGA1UdEwEB/wQIMAYBAf8CAQAwNgYDVR0fBC8wLTAroCmgJ4YlaHR0cDov\n" + "L3Muc3ltY2IuY29tL3VuaXZlcnNhbC1yb290LmNybDAdBgNVHSUEFjAUBggrBgEF\n" + "BQcDAQYIKwYBBQUHAwIwDgYDVR0PAQH/BAQDAgEGMC4GCCsGAQUFBwEBBCIwIDAe\n" + "BggrBgEFBQcwAYYSaHR0cDovL3Muc3ltY2QuY29tMGEGA1UdIARaMFgwVgYGZ4EM\n" + "AQIBMEwwIwYIKwYBBQUHAgEWF2h0dHBzOi8vZC5zeW1jYi5jb20vY3BzMCUGCCsG\n" + "AQUFBwICMBkaF2h0dHBzOi8vZC5zeW1jYi5jb20vcnBhMCkGA1UdEQQiMCCkHjAc\n" + "MRowGAYDVQQDExFTeW1hbnRlY1BLSS0yLTU1NjAdBgNVHQ4EFgQUyqxd4ZAv8e+M\n" + "1J81AeEBO6DOwXcwHwYDVR0jBBgwFoAUtnf6aUhHn1MS1cLqBzJ2B9GXBxkwDQYJ\n" + "KoZIhvcNAQELBQADggEBAJRdaWPJpyEdGNMxyRkR93sap0nmeN3WoBUOJlb6lCbu\n" + "EX/5zhB1Yu9RNqdIFlVYUX/gEURpNDqKQb90xJq3jdfg3z/bQ90beLuohBLREcCm\n" + "nz78sfnyp7Z6gfXxyOYfy/MTA7866NFN5GEcQiGEBWdcitUhBPLuxzFUUGuGhiR7\n" + "f0cUVPq6ADcCDpqOwnFbFFuJ7c+RGz/HG0pqxC0dPiHo1NIF7XK23l12nurVXOmc\n" + "FJ31PEqFT0Rcd2fs+vkuVfpN+EBI+SY/gHiCOx/BvsgWiE4VXHTYIL36PUKecCQ+\n" + "+y9RlQ60UOqmSTQEQkkAUAn7w/0LjXEnfwfMhPABUcU=\n" + "-----END CERTIFICATE-----";

    /**
     * Phone MANUFACTURER
     */
    public static final String SAMSUNG = "samsung";

    /**
     * Tab colors
     */
    public static final int[] TAB_COLORS = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };


    /**
     * Main Pager
     */
    public static final String SEARCH_TEXT = "search_text";

    public static final String MENU_BUILDER = "MenuBuilder";

    public static final String LOGIN_DATA = "login_data";

    public static final String BANNER_DATA = "banner_data";

    public static final String ARTICLE_DATA = "article_data";

    /**
     * Refresh theme color
     */
    public static final int BLUE_THEME = R.color.colorPrimary;

    /**
     * Avoid double click time area
     */
    public static final long CLICK_TIME_AREA = 1000;

    public static final long DOUBLE_INTERVAL_TIME = 2000;


    public static final String ARTICLE_LINK = "article_link";

    public static final String ARTICLE_TITLE = "article_title";

    public static final String ARTICLE_ID = "article_id";

    public static final String IS_COLLECT = "is_collect";

    public static final String IS_COMMON_SITE = "is_common_site";

    public static final String TURN_TYPE = "turn_type";

    public static final String IS_COLLECT_PAGE = "is_collect_page";

    public static final String CHAPTER_ID = "chapter_id";

    public static final String IS_SINGLE_CHAPTER = "is_single_chapter";

    public static final String CHAPTER_NAME = "is_chapter_name";

    public static final String SUPER_CHAPTER_NAME = "super_chapter_name";

    static final String DB_NAME = "aws_wan_android.db";

    public static final String CURRENT_PAGE = "current_page";

    public static final String PROJECT_CURRENT_PAGE = "project_current_page";

    /**
     * Shared Preference key
     */
    public static final String ACCOUNT = "account";

    public static final String PASSWORD = "password";

    public static final String LOGIN_STATUS = "login_status";

    public static final String AUTO_CACHE_STATE = "auto_cache_state";

    public static final String NO_IMAGE_STATE = "no_image_state";

    public static final String NIGHT_MODE_STATE = "night_mode_state";

}
