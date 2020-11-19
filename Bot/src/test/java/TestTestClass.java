import functions.Works;
import org.junit.Assert;
import org.junit.Test;


public class TestTestClass {
    @Test
    public void testMethod() {
        Works works = new Works();
        Works copyWorks = works;
        Assert.assertEquals(works, copyWorks);
    }

}
