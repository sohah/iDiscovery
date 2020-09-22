package wbs;


public class WBSMain {

    protected WBS wbs = new WBS();


    public void DoSimSymbolic() {
        this.wbs.launch(0, false, false);
    }

    public void DoSimSymbolic(int p1, boolean p2, boolean p3) {
        this.wbs.launch(p1, p2, p3);
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
                    Boolean.parseBoolean(args[1]), Boolean.parseBoolean(args[2]));
        }
    }
}
