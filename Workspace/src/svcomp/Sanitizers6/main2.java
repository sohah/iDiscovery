package svcomp.Sanitizers6;

public class main2 {
        public static void main(String[] args) {
            String s1 = "<bad/>";
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < s1.length(); i++) {
                char ch = s1.charAt(i);

                if (Character.isLetter(ch)|| ch == '_') {
                    buf.append(ch);
                }
            }

            String s = buf.toString();
            if (s.contains("<bad/>")) {
                assert false;
            }
        }
}
