package tcas;

public class SpfTCASMain {

    protected SpfTCAS tcas = new SpfTCAS();


    public void DoSimSymbolic() {
        this.tcas.mainProcess(601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1);
    }

    public void DoSimSymbolic(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12) {
        this.tcas.mainProcess(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
    }

    public static void main(String[] args) {
        SpfTCASMain tcasMain;
        if (args.length < 2) { // Run symbolically if no args
            tcasMain = new SpfTCASMain();
            tcasMain.DoSimSymbolic();
        }
        // else {
        // rjcmain = new RJCMain(args[0], args[1]);
        // rjcmain.DoSim();
        // }
        else {
            tcasMain = new SpfTCASMain();
            tcasMain.DoSimSymbolic(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6]),Integer.parseInt(args[7]),Integer.parseInt(args[8]),Integer.parseInt(args[9]),Integer.parseInt(args[10]),Integer.parseInt(args[11]));
        }
    }
}
