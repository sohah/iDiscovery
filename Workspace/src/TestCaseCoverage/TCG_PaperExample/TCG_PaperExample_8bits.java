package TestCaseCoverage.TCG_PaperExample;

public class TCG_PaperExample_8bits {

   public static void main(String[] args) {
        separateBits_8(1);
    }



    public static int separateBits_8(int i) {
        int j = 0;
        if (i >= -128 && i <= 127) {
            while (i != 0) {// the loop
                int trailHasZero = (i & 1);
                if (trailHasZero == 0) {
                    int numberOfTrailingZeros = numberOfTrailingZeros_8(i);
                    i = (i >> numberOfTrailingZeros);
                } else {
                    j = (j >>> 1);
                    j = (j ^ 128);
                    i = (i >>> 1);
                }
            }
//            System.out.println(Integer.toBinaryString(j));
//            System.out.println(j);
        }
        return j;
    }

    public static int numberOfTrailingZeros_8(int x) {
        // HD, Figure 5-14
        int y;
        x = x << 24;
        if (x == 0) return 8;
        int n = 7;

        y = (x << 4);
        if (y != 0) {
            n = (n - 4);
            x = y;
        }
        y = (x << 2);
        if (y != 0) {
            n = (n - 2);
            x = y;
        }
//        return (n - ((((i << 1)) >>> 7)));
        y = (x << 1);
        if (y != 0) {
            n = (n - 1);
        }

        return n;
    }

}
