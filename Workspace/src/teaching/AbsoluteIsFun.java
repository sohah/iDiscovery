package teaching;

import java.util.Scanner;

public class AbsoluteIsFun {

    static int bugMsgCounter = 0;
    static int noBugMsgCounter = 0;

    static String[] bugAttemptMsgs = new String[4]; // teasing messages
    static String[] noBugAttemptMsgs = new String[4]; //encouraging messages

    static boolean[] bugsFound = new boolean[3];

    public static void step(String[] args) throws ArithmeticException {
        if (args.length != 2) {
            doNoBugEncountered(" you must add only 2 numbers separated by space.");
            return;
        }

        String num1Str = args[0];
        String num2Str = args[1];
        int num1Int = 0;
        long num1Long = 0;
        int num1FractionInt = 0;
        long num1FractionLong = 0;

        int num2Int = 0;
        long num2Long = 0;
        int num2FractionInt = 0;
        long num2FractionLong = 0;

        int sumInt = 0;
        long sumFraction = 0;

        if (num1Str.contains(".")) {
            String[] partitions = num1Str.split("\\.");
            if (partitions.length != 2) {
                doNoBugEncountered(" first number cannot have two decimal points");
                return;
            }
            num1Long = convert(partitions[0]);
            num1FractionLong = convert(partitions[1]);
        } else num1Long = convert(num1Str);

        if (num1Long > Integer.MAX_VALUE || num1FractionLong > Integer.MAX_VALUE) {
            doNoBugEncountered(" first number must be an 32-bit integer.");
            return;
        } else {
            num1Int = (int) num1Long;
            num1FractionInt = (int) num1FractionLong;
        }

        if (num2Str.contains(".")) {
            String[] partitions = num2Str.split("\\.");
            if (partitions.length != 2) {
                if (bugsFound[0]) {
                    System.out.println("You have already found this bug, I suggest you'd try something else (. \\|/ .)");
                    return;
                } else {
                    doBugEncountered("BUG: the second number has two decimal points!");
                    bugsFound[0] = true;
                    return;
                }
            }
            num2Long = convert(partitions[0]);
            num2FractionLong = convert(partitions[1]);
        } else num2Long = convert(num2Str);

        if (num2Long > Integer.MAX_VALUE || num2FractionLong > Integer.MAX_VALUE) {
            doNoBugEncountered(" second number must be an 32-bit integer.");
            return;
        } else {
            num2Int = (int) num2Long;
            num2FractionInt = (int) num2FractionLong;
        }

        try {
            sumInt = Math.addExact(num1Int, num2Int);
            sumFraction = Math.addExact(num1FractionInt, num2FractionInt);
        } catch (ArithmeticException e) {
            if (bugsFound[1]) {
                System.out.println("You have already found this bug, I suggest you'd try something else (. \\|/ .)");
                return;
            } else {
                doBugEncountered("BUG: Integer overflow is not handled.");
                bugsFound[1] = true;
                return;
            }
        }
        if (num1FractionInt > 0 || num2FractionInt > 0) {
            float fl1 = new Float(num1Int + "." + num1FractionInt);
            float fl2 = new Float(num2Int + "." + num2FractionInt);

            float sumfl = new Float(sumInt + "." + sumFraction);
            if (sumfl != fl1 + fl2) {
                if (bugsFound[2]) {
                    System.out.println("You have already found this bug, I suggest you'd try something else (. \\|/ .)");
                    return;
                } else {
                    doBugEncountered("BUG: Curry of fraction is not handled.");
                    bugsFound[2] = true;
                    return;
                }
            }
        }
        if (sumFraction > 0)
            System.out.println("sum = " + sumInt + "." + sumFraction);
        else
            System.out.println("sum = " + sumInt);
        doNoBugEncountered("");
    }

    private static long convert(String partition) throws ArithmeticException {

        int index = 0;
        long result = 0;
        if (partition.length() == 0)
            return 0;

        char[] partArr = partition.toCharArray();

        if (partArr[0] == '-')
            index = 1;

        for (int i = index; i < partArr.length; i++) {
            int digit = (partArr[i] - '0');
            if (digit >= 0 && digit <= 9)
                result = result * 10 + (partArr[i] - '0');
            else {
                doNoBugEncountered(" you can't write that, numbers must be made of digits.");
                throw new ArithmeticException("invalid operation");
            }
        }
        return result;
    }

    private static void doNoBugEncountered(String specializedMsg) {
        System.out.println("NOT a bug " + noBugAttemptMsgs[noBugMsgCounter] + "(" + specializedMsg + ")");
        noBugMsgCounter = noBugMsgCounter == 3 ? 0 : ++noBugMsgCounter;
    }

    private static void doBugEncountered(String specializedMsg) {
        System.out.println("No_O_o_O_o_O_o_O_o_O " + bugAttemptMsgs[bugMsgCounter] + "(" + specializedMsg + ")");
        bugMsgCounter = bugMsgCounter == 3 ? 0 : ++bugMsgCounter;
    }

    public static void main(String[] args) {
        fillMsgs();
        int bugsKilled = 0;
        do {
            System.out.println("I AM THE UNBREAKABLE (. \\|/ .)\nCan you find my 3 hidden bugs");
            System.out.println("Enter two numbers to compute their Absolute fun sum, or type \"GIVE UP\" if you Give Up");

            Scanner s = new Scanner(System.in);
            String input;

            input = s.nextLine();
            if (input.equals("GIVE UP"))
                return;
            try {
                step(input.split(" "));
            } catch (ArithmeticException e) {
            }
            bugsKilled = countBugsFound();
//            System.out.println(" try again \n\n");
        } while (bugsKilled < 3);

        System.out.println("YOU ROCK! YOU HAVE FOUND ALL MY BUGS!");
    }

    private static int countBugsFound() {
        int bugsCaught = 0;
        for (boolean b : bugsFound)
            bugsCaught = b ? bugsCaught + 1 : bugsCaught;
        System.out.println("you found " + bugsCaught + " out of " + "3 seeded bugs.\n");
        return bugsCaught;
    }

    private static void fillMsgs() {
        bugAttemptMsgs[0] = "was that intentional, or out of SUPER LUCK!";
        bugAttemptMsgs[1] = "you just broke me Man!";
        bugAttemptMsgs[2] = "this can't be happening, did you just broke me AGAIN!";
        bugAttemptMsgs[3] = "fine, you ARE GOOD!";

        noBugAttemptMsgs[0] = " (. \\|/ .) IS THAT ALL YOU GOT!";
        noBugAttemptMsgs[1] = " (. \\|/ .) NOW THAT IS IMPRESSING!";
        noBugAttemptMsgs[2] = " (. \\|/ .) I AM THE UNBREAKABLE!";
        noBugAttemptMsgs[3] = " (. \\|/ .) OH BOY, GET SERIOUS!";
    }
}
