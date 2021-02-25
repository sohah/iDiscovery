package TestCaseCoverage;

public class B {

    int b = 200;

    public int getIncB() {
        return ++b;
    }

    public int getIncA(A aRef) {
        return aRef.getIncB();
    }

    public int fibB(int n) {
        if (n <= 1)
            return n;
        return fibB(n - 1) + fibB(n - 2);
    }
}
