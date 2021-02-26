package teaching;

import java.util.Scanner;

public class AbsoluteIsFun {

    static int bugMsgCounter = 0;
    static int noBugMsgCounter = 0;

    static String[] bugAttemptMsgs = new String[4]; // teasing messages
    static String[] noBugAttemptMsgs = new String[4]; //encouraging messages

    static boolean[] bugsFound = new boolean[2];

    public static void step(String[] args) {
        if (args.length != 2) {
            doNoBugEncountered("Not a bug: you must add only 2 numbers.");
            return;
        }

        String num1Str = args[0];
        String num2Str = args[1];
        int num1Int = 0;
        int num1Fraction = 0;

        int num2Int = 0;
        int num2Fraction = 0;

        int sumInt = 0;
        int sumFraction = 0;

        if (num1Str.contains(".")) {
            String[] partitions = num1Str.split("\\.");
            if (partitions.length != 2) {
                doNoBugEncountered("Not a bug: first number cannot have two two decimal points");
                return;
            }
            num1Int = convert(partitions[0]);
            num1Fraction = convert(partitions[1]);
        } else num1Int = convert(num1Str);

        if (num2Str.contains(".")) {
            String[] partitions = num2Str.split("\\.");
            if (partitions.length != 2) {
                doBugEncountered("BUG: the second number has two decimal points!");
                bugsFound[0] = true;
                return;
            }
            num2Int = convert(partitions[0]);
            num2Fraction = convert(partitions[1]);
        } else num2Int = convert(num2Str);

        try {
            sumInt = Math.addExact(num1Int, num2Int);
            sumFraction = Math.addExact(num1Fraction, num2Fraction);
        } catch (ArithmeticException e) {
            doBugEncountered("BUG: Integer overflow is not handled.");
            bugsFound[1] = true;
        }


        float fl1 = new Float(num1Int + "." + num1Fraction);
        float fl2 = new Float(num2Int + "." + num2Fraction);

        float sumfl = new Float(sumInt + "." + sumFraction);
        if (sumfl != fl1 + fl2) {
            doBugEncountered("BUG: Curry of fraction is not handled.");
            bugsFound[2] = true;
            return;
        }

        if (sumFraction > 0)
            System.out.println("sum = " + sumInt + "." + sumFraction);
        else
            System.out.println("sum = " + sumInt);
        doNoBugEncountered("");
    }

    private static int convert(String partition) {

        int index = 0;
        int result = 0;
        if (partition.length() == 0)
            return 0;

        char[] partArr = partition.toCharArray();

        if (partArr[0] == '-')
            index = 1;

        for (int i = index; i < partArr.length; i++)
            result = result * 10 + (partArr[index] - '0');

        return result;
    }

    private static void doNoBugEncountered(String specializedMsg) {
        System.out.println(" ^^-^^ smiling evilly ^^-^^" + noBugAttemptMsgs[noBugMsgCounter] + "(" + specializedMsg + ")");
        noBugMsgCounter = noBugMsgCounter == 3 ? 0 : ++noBugMsgCounter;
    }

    private static void doBugEncountered(String specializedMsg) {
        System.out.println("No_O_o_O_o_O_o_O_o_O " + bugAttemptMsgs[bugMsgCounter] + "(" + specializedMsg + ")");
        bugMsgCounter = bugMsgCounter == 3 ? 0 : ++bugMsgCounter;
    }

    public static void main(String[] args) {
        fillMsgs();
        do {
            System.out.println("ABSOLUTE FUN CALLING ON YOU -- Can you find my 3 hidden bugs");
            System.out.println("Enter two numbers to compute their Absolute fun sum, or GIVE UP if you Give Up");

            Scanner s = new Scanner(System.in);
            String input;

            input = s.nextLine();
            if (input.equals("GIVE UP")) {
                int bugsCaught = 0;
                for (boolean b : bugsFound)
                    bugsCaught = b ? bugsCaught + 1 : bugsCaught;
                System.out.println("you found " + bugsCaught + " out of " + "3 seeded bugs.");
                return;
            }
            step(input.split(" "));

            System.out.println(" try again \n\n");
        } while (true);
    }

    private static void fillMsgs() {
        bugAttemptMsgs[0] = "was that intentional, or out of SUPER LUCK!";
        bugAttemptMsgs[1] = "you just broke me Man!";
        bugAttemptMsgs[2] = "you can't be doing this to me, you just broke me AGAIN";
        bugAttemptMsgs[3] = "fine, you ARE GOOD!";

        noBugAttemptMsgs[0] = " COME ON, IS THAT ALL YOU GOT!";
        noBugAttemptMsgs[1] = " YOU CAN'T BE TRYING TO BREAK ME!";
        noBugAttemptMsgs[2] = " NICE TRY!";
        noBugAttemptMsgs[3] = " OH BOY, GET SERIOUS!";
    }
}
