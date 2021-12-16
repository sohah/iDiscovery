package TestCaseCoverage.TCG_PaperExample;

import static java.lang.Integer.numberOfTrailingZeros;

public class TCG_PaperExample_4bits {

   public static void main(String[] args) {
        separateBits_4(-6);
    }

    public static int separateBits_4(int i) {
        int j = 0;
        if (i >= -8 && i <= 7) {
//        if (i >= 2 && i <4) {
            while (i != 0) {
                int trailHasZero = (i & 1);
                if (trailHasZero == 0) {
//                    int numberOfTrailingZeros = numberOfTrailingZeros_4(i);
                    int numberOfTrailingZeros;
                    int y;
                    int x;
                    x = i << 28;
                    if (x == 0) numberOfTrailingZeros = 4; else {
                        int n = 3;


                        y = (x << 2);
                        if (y != 0) {
                            n = (n - 2);
                            x = y;
                        }
                        y = (x << 1);
                        if (y != 0) {
                            n = (n - 1);
                        }
                        numberOfTrailingZeros = n;
                    }
                    i = (i >> numberOfTrailingZeros);
                } else {
                    j = (j >>> 1);
                    j = (j ^ 8);
                    i = (i >>> 1);
                }
            }
//            System.out.println(Integer.toBinaryString(j));
//            System.out.println(j);
        }
        return j;
    }

//    public static int numberOfTrailingZeros_4(int x) {
//        // HD, Figure 5-14
//        int y;
//        x = x << 28;
//        if (x == 0) return 4;
//        int n = 3;
//
//
//        y = (x << 2);
//        if (y != 0) {
//            n = (n - 2);
//            x = y;
//        }
//        y = (x << 1);
//        if (y != 0) {
//            n = (n - 1);
//        }
//
//        return n;
//    }

}
