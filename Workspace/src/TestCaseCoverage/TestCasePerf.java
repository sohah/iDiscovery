package TestCaseCoverage;

public class TestCasePerf {

    int sideEffect = 0;

    public static void main(String[] args) {
        /*veritesting.test_case_gen.A myA = new veritesting.test_case_gen.A();
        int myVal = myA.getIncA();
        System.out.println("my A value is = " + myVal);*/
//        singleBranchCov2(1, 1);
//        doubleBranchCov(1, 1);
//        complexBranchCov(1, 1);
//        complexBranchCov(1,1);
//        unoptimalDFS(1, 1);
        doubleLoopUnoptimalDFS(1, 1);
//        doubleLoop(1, 1);
//        mixOfRegions(1, 1);
//        mixOfRegions2Paths(1, 1);
//        mixOfRegions2Paths2(1, 1);
//        mixOfRegions2Paths2ComplexCond(1, 1);
//        mixOfRegions2Paths2(1,10);
//        mixOfRegions2PathsDepth3(1, 1);
//        arrayLoadStore0(1, 1);
//        testingSoundReach(1, 1);
//        testingComplexConditions2(true, true);
//        testingComplexConditions3(true, true, true, true);

//        (new TestCasePerf()).testingER1(true, 1);
//        (new TestCasePerf()).testERInline(true, 1);
//        simpleRegion(1);
    }
    public static int doubleLoopUnoptimalDFS(int x, int y) {
        int j = 0;
        if (x == y)
            return 1;

        for (int i = 0; i < 2; i++) {
            if (x == 1)
                x = y + 3;
            else if (y > 1) {
                while (j < 4) {
                    x = x + 1;
                    j++;
                }
            } else
                x = x + 2;
        }
        return x;
    }

}
