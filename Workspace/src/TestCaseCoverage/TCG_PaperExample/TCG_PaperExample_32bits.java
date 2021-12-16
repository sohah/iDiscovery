package TestCaseCoverage.TCG_PaperExample;

public class TCG_PaperExample_32bits {

   public static void main(String[] args) {
        separateBits_32(1);
    }



    public static int separateBits_32(int i) {
        int j = 0;
//        if (i >= -2147483648 && i <= 2147483647) {
//        if (i >= -1 && i < 20) {
        if (i >= -3 && i < 5) {
            while (i != 0) {
                int trailHasZero = (i & 1);
                if (trailHasZero == 0) {
                    int numberOfTrailingZeros = numberOfTrailingZeros(i);
                    i = (i >> numberOfTrailingZeros);
                } else {
                    j = (j >>> 1);
                    j = j ^ (Integer.reverse(1));
                    i = (i >>> 1);
                }
            }
//            System.out.println(Integer.toBinaryString(j));
//            System.out.println(j);
        }
        return j;
    }

    public static int numberOfTrailingZeros(int x) {
        // HD, Figure 5-14
        int y;
        if (x == 0) return 32;
        int n = 31;
        y = x <<16; if (y != 0) { n = n -16; x = y; }
        y = x << 8; if (y != 0) { n = n - 8; x = y; }
        y = x << 4; if (y != 0) { n = n - 4; x = y; }
        y = x << 2; if (y != 0) { n = n - 2; x = y; }
        y = (x << 1);
        if (y != 0) {
            n = (n - 1);
        }

        return n;
//        return n - ((x << 1) >>> 31);
    }
}
