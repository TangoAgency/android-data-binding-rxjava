package agency.tango.databindingrxjava;


public class StringUtils {
    public static String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String string) {
        return !isNotNullOrEmpty(string);
    }

    public static boolean isNotNullOrEmpty(String string) {
        return string != null && !string.equals(EMPTY);
    }
}
