import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String r = parseQueryString("/song?id=12345&name=&b", "id");
        System.out.println(r);
    }

    public static String parseQueryString(String matchUrl, String key) {
        String r = null;
        if (matchUrl != null && key != null) {
            String reg = "([^?&=]+)((?:=([^?&=]*))*)";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(matchUrl);
            while (m.find()) {
                if (key.equals(m.group(1))) {
                    r = m.group(3);
                }

            }
        }
        return r;
    }
}
