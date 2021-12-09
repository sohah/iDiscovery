public class TCG_Workshop_Example {


    public static void main(String[] args) {
//        separateBits(13);
//        separateBits_16((short) 13);
//        separateBits_8( 13);
          separateBits_4(13);
        //16 bits invocation
//        separateBitsVariableSizes((short)13, 0xFFFF);
//        separateBitsVariableSizes((short)13, 0xFFFFFF, 24);
    }

    {
    /*

        public static int separateBitsVariableSizes(int i, int hexVal, int bitShift) {
            i &= hexVal;
            System.out.println(Integer.toBinaryString(hexVal));
            int j = 0;

            while (i != 0) {
                int trailHasZero = (i & 1);
                if (trailHasZero == 0) {
                    int numberOfTrailingZeros = numberOfTrailingZeros_8(i);
                    i = (i >> numberOfTrailingZeros);
                } else {
                    j = (j >>> 1);
                    j = (j ^ (hexVal &(Integer.reverse(1) >>> bitShift)));
                    i = (i >> 1);
                }
            }
            System.out.println(Integer.toBinaryString(hexVal & j));
            System.out.println(j);

            return j;
        }
        public static int numberOfTrailingZeros_8(int i) {
            // HD, Figure 5-14
            int y;
            if (i == 0) return 8;
            int n = 7;

            y = (i << 4) & 0xFFFFFF;
            if (y != 0) {
                n = (n - 4);
                i = y;
            }
            y = (i << 2) & 0xFFFFFF;
            if (y != 0) {
                n = (n - 2);
                i = y;
            }
            return (n - ((((i << 1) & 0xFFFFFF) >>> 7)));
        }
    */
    /*public static int separateBits(int i) {
        int j = 0;
        System.out.println(Integer.toBinaryString(i));

        while (i != 0) {
            int zeroCount = Integer.numberOfTrailingZeros(i);
            if (zeroCount != 0) {
                i >>= zeroCount;
            } else {
                j >>>= 1;
                j ^= Integer.reverse(1);
                i >>= 1;
            }
        }

        System.out.println(Integer.toBinaryString(j));
        System.out.println(j);
        return j;
    }
*/
   /* public static int separateBits_16(int i) {
        i &= 0xFFFF;

        int j = 0;

        while (i != 0) {
            int trailHasZero = (i & 1);
            if (trailHasZero == 0) {
                int numberOfTrailingZeros = numberOfTrailingZeros_16(i);
                i = (i >> numberOfTrailingZeros);
            } else {
                j = (j >>> 1);
                j = (j ^ (0xFFFF & (Integer.reverse(1) >>> 16)));
                i = (i >> 1);
            }
        }
        System.out.println(Integer.toBinaryString(0xFFFF & j));
        System.out.println(j);

        return j;
    }

    public static int numberOfTrailingZeros_16(int i) {
        // HD, Figure 5-14
        int y;
        if (i == 0) return 16;
        int n = 15;

        y = (i << 8);
        if (y != 0) {
            n = (n - 8);
            i = y;
        }
        y = (i << 4);
        if (y != 0) {
            n = (n - 4);
            i = y;
        }
        y = (i << 2);
        if (y != 0) {
            n = (n - 2);
            i = y;
        }
        return (n - ((((i << 1) & 0xFFFF) >>> 15)));
    }*/
    }

    public static int separateBits_16(int i) {
        int j = 0;

        while (i != 0) {
            int trailHasZero = (i & 1);
            if (trailHasZero == 0) {
                int numberOfTrailingZeros = numberOfTrailingZeros_16(i);
                i = (i >> numberOfTrailingZeros);
            } else {
                j = (j >>> 1);
                j = (j ^ 32768);
                i = (i >> 1);
            }
        }
        System.out.println(Integer.toBinaryString(j));
        System.out.println(j);

        return j;
    }

    public static int numberOfTrailingZeros_16(int i) {
        // HD, Figure 5-14
        int y;
        i = i << 16;
        if (i == 0) return 16;
        int n = 15;

        y = (i << 8);
        if (y != 0) {
            n = (n - 8);
            i = y;
        }
        y = (i << 4);
        if (y != 0) {
            n = (n - 4);
            i = y;
        }
        y = (i << 2);
        if (y != 0) {
            n = (n - 2);
            i = y;
        }
        return (n - ((((i << 1)) >>> 15)));
    }

    public static int separateBits_8(int i) {
        int j = 0;

        while (i != 0) {// the loop
            int trailHasZero = (i & 1);
            if (trailHasZero == 0) {
                int numberOfTrailingZeros = numberOfTrailingZeros_8(i);
                i = (i >> numberOfTrailingZeros);
            } else {
                j = (j >>> 1);
                j = (j ^ 128);
                i = (i >> 1);
            }
        }
        System.out.println(Integer.toBinaryString(j));
        System.out.println(j);

        return j;
    }

    public static int numberOfTrailingZeros_8(int i) {
        // HD, Figure 5-14
        int y;
        i = i << 24;
        if (i == 0) return 8;
        int n = 7;

        y = (i << 4);
        if (y != 0) {
            n = (n - 4);
            i = y;
        }
        y = (i << 2);
        if (y != 0) {
            n = (n - 2);
            i = y;
        }
        return (n - ((((i << 1)) >>> 7)));
    }

    public static int separateBits_4(int i) {
        int j = 0;

        while (i != 0) {
            int trailHasZero = (i & 1);
            if (trailHasZero == 0) {
                int numberOfTrailingZeros = numberOfTrailingZeros_4(i);
                i = (i >> numberOfTrailingZeros);
            } else {
                j = (j >>> 1);
                j = (j ^ 8);
                i = (i >> 1);
            }
        }
        System.out.println(Integer.toBinaryString(j));
        System.out.println(j);

        return j;
    }

    public static int numberOfTrailingZeros_4(int i) {
        // HD, Figure 5-14
        int y;
        i = i << 28;
        if (i == 0) return 4;
        int n = 3;


        y = (i << 2);
        if (y != 0) {
            n = (n - 2);
            i = y;
        }
        return (n - ((((i << 1)) >>> 3)));
    }
}
