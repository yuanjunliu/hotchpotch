import cn.juns.cmpl.RegexMatcher;
import org.junit.Test;
import org.junit.Assert;

import java.util.regex.Pattern;

public class RegexpTest {
    @Test
    public void testReDoS() {
        String regex = "(a*)*";
        String str1 = "aaaaaaaaaaaaaaaaaaaaaaab";
        testFor(regex, str1);
    }

    public void testFor(String regex, String str) {
        long prev;
        prev = System.currentTimeMillis();
        final boolean expected = Pattern.compile(regex).matcher(str).matches();
        System.out.println(System.currentTimeMillis() - prev);
        prev = System.currentTimeMillis();
        boolean actual = new RegexMatcher(regex).match(str);
        System.out.println(System.currentTimeMillis() - prev);
        Assert.assertEquals(expected, actual);
    }
}
