import static java.lang.Float.MIN_VALUE;

public class FloatTest {


    public static void main(String[] args) {
        float a = 3;
        float b = MIN_VALUE;
        assert (a + b != a || b == 0.0);
    }
}
