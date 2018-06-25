import cn.juns.cmpl.RegexMatcher;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.regex.Pattern;

public class RegexpTest {
    @Test
    public void testReDoS() {
        String regex = "(a*)*";
        String str1 = "aaaaaaaaaaaaaaaaaaaaaaab";
        testFor(regex, str1);
    }

    @Test
    public void testReSearch() {
        String regex = "cat+";
        String str1 = "whos cat is named catty";
        List<String> result = new RegexMatcher(regex).search(str1);
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), "cat");
        Assert.assertEquals(result.get(1), "catt");
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
