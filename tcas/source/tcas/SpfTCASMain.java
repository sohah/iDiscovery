package tcas;

public class SpfTCASMain {

    protected SpfTCAS tcas = new SpfTCAS();


    public void DoSimSymbolic() {
        this.tcas.launch(601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1,601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1);
    }

    public void DoSimSymbolic(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12,
                              int p13, int p14, int p15, int p16, int p17, int p18, int p19, int p20, int p21, int p22, int p23, int p24) {
        this.tcas.launch(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20,p21, p22, p23,p24);
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
            tcasMain.DoSimSymbolic(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6]),Integer.parseInt(args[7]),Integer.parseInt(args[8]),Integer.parseInt(args[9]),Integer.parseInt(args[10]),Integer.parseInt(args[11]),

                    Integer.parseInt(args[12]),Integer.parseInt(args[13]),Integer.parseInt(args[14]),Integer.parseInt(args[15]),Integer.parseInt(args[16]),Integer.parseInt(args[17]),Integer.parseInt(args[18]),Integer.parseInt(args[19]),Integer.parseInt(args[20]),Integer.parseInt(args[21]),Integer.parseInt(args[22]),Integer.parseInt(args[23]));
        }
    }
}
