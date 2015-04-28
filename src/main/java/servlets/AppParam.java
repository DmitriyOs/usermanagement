package servlets;

public class AppParam {
    private static String contextPath;

    public static void setContextPath(String contextPath) {

        AppParam.contextPath = contextPath;
    }

    static String getContextPath() {
        return contextPath;
    }
}
