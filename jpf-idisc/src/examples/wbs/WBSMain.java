package wbs;


public class WBSMain {

    protected WBS wbs = new WBS();


    public void DoSimSymbolic() {
        this.wbs.launch(0, false, false,0, false, false,0, false, false,0, false, false);
    }

    public void DoSimSymbolic(int p1, boolean p2, boolean p3, int p4, boolean p5, boolean p6,
    int p7, boolean p8, boolean p9,
    int p10, boolean p11, boolean p12) {
        this.wbs.launch(p1, p2, p3,
                p4,p5,p6,
                p7,p8,p9,
                p10, p11, p12);
    }

    public static void main(String[] args) {
        WBSMain wbsMain;
        if (args.length < 2) { // Run symbolically if no args
            wbsMain = new WBSMain();
            wbsMain.DoSimSymbolic();
        }
        // else {
        // rjcmain = new RJCMain(args[0], args[1]);
        // rjcmain.DoSim();
        // }
        else {
            wbsMain = new WBSMain();
            wbsMain.DoSimSymbolic(Integer.parseInt(args[0]),
                    Boolean.parseBoolean(args[1]), Boolean.parseBoolean(args[2]),

                    Integer.parseInt(args[3]),
                    Boolean.parseBoolean(args[4]), Boolean.parseBoolean(args[5]),

                    Integer.parseInt(args[5]),
                    Boolean.parseBoolean(args[6]), Boolean.parseBoolean(args[8]),

                    Integer.parseInt(args[9]),
                    Boolean.parseBoolean(args[10]), Boolean.parseBoolean(args[11]));
        }
    }
}
