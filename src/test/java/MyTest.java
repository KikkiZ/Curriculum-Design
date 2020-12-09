import ink.kikkiz.entity.question.Choice;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MyTest {

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>() ;
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list.get(2));
    }

}
