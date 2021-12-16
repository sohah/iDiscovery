package TestCaseCoverage.TCG_PaperExample;

public class TCG_PaperExample_16bits {

   public static void main(String[] args) {
        separateBits_16(1);
    }



    public static int separateBits_16(int i) {
        int j = 0;
        if (i >= -32768 && i <= 32767) {
            while (i != 0) {
                int trailHasZero = (i & 1);
                if (trailHasZero == 0) {
                    int numberOfTrailingZeros = numberOfTrailingZeros_16(i);
                    i = (i >> numberOfTrailingZeros);
                } else {
                    j = (j >>> 1);
                    j = (j ^ 32768);
                    i = (i >>> 1);
                }
            }
//            System.out.println(Integer.toBinaryString(j));
//            System.out.println(j);
        }
        return j;
    }

    public static int numberOfTrailingZeros_16(int x) {
        // HD, Figure 5-14
        int y;
        x = x << 16;
        if (x == 0) return 16;
        int n = 15;

        y = (x << 8);
        if (y != 0) {
            n = (n - 8);
            x = y;
        }
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
        y = (x << 1);
        if (y != 0) {
            n = (n - 1);
        }

        return n;
//        return (n - ((((x << 1)) >>> 15)));
    }

}
