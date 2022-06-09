import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Calcing {
    public static void main(String[] args) throws IOException {
        String result = "";
        System.out.println("Введите выражение:");
        Scanner in1 = new Scanner(System.in);

        String znach = in1.nextLine().toUpperCase();

                    if (!znach.equals(znach.replaceAll ("[^IVX+-/*0-9 ]",""))) {
                        throw new ArithmeticException("Неверная запись выражения");
                    }
        result = calc(znach);
        System.out.println("Результат вычисления: " + result);

    }

    public static String calc(String input) {
        String result = "";
        int res;
        String[] Element;
        Element = input.split(" ");

        String first = Element[0];
        String z = Element[1];
        String second = Element[2];
        int r = 0;
        int e = 0;

        if (first.contains("I") || first.contains("V") || first.contains("X")) { // проверяю является ли римской цифрой
            r += 1;
        } else {e += 1;}
        if (second.contains("I") || second.contains("V") || second.contains("X")) {
            r += 1;
        } else {e += 1;}

        if(r<2 && e<2){throw new ArithmeticException("Числа указаны в разных системах исчисления");}

        if (r == 2) {
          //  System.out.println("Обе цифры римские");
            result = ArabicToRoman(Raschet(romanToArabic(first), romanToArabic(second), z)); // преобразую в арабские и получаю арабский результат и вызываю метод конвертации в римские
        }

        if (e == 2) {
           // System.out.println("Обе цифры арабские");
            int a = Integer.parseInt(first);
            int b = Integer.parseInt(second);
               if (a<1 || a>10 || b<1 || b>10){
                  throw new ArithmeticException("Одно или несколько чисел выходят за диапазон");
               }
            result = String.valueOf(Raschet(a, b, z));
        }

        return result;
    }

    public static int Raschet(int a, int b, String z) {
        int schet = 0;
        switch (z) {
            case "*":
                schet = a * b;
                break;
            case "/":
                schet = a / b;
                break;
            case "-":
                schet = a - b;
                break;
            case "+":
                schet = a + b;
                break;
        }
        return schet;
    }

    enum RomanNumeral implements CharSequence {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            return 0;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return null;
        }
    }

    public static int romanToArabic(String rn) {
        int che = 0;
        int s = 0;
        if (rn.contains(RomanNumeral.X) || rn.contains(RomanNumeral.V)) {
            if (rn.startsWith(RomanNumeral.I.toString())) {
                int i = 0;
                while (rn.charAt(i) == RomanNumeral.I.toString().charAt(0) && i < rn.length()) {
                    che = che + 1;
                    s = i;
                    i++;
                }
                switch (rn.charAt(s + 1)) {
                    case 'V':
                        che = RomanNumeral.V.getValue() - che;
                        break;
                    case 'X':
                        che = RomanNumeral.X.getValue() - che;
                        break;
                }
            }
            if (rn.endsWith(RomanNumeral.I.toString())) {
                int i = rn.length() - 1;
                while (rn.charAt(i) == RomanNumeral.I.toString().charAt(0) && i >= 0) {
                    che = che + 1;
                    s = i;
                    i--;
                }
                switch (rn.charAt(s - 1)) {
                    case 'V':
                        che = RomanNumeral.V.getValue() + che;
                        break;
                    case 'X':
                        che = RomanNumeral.X.getValue() + che;
                        break;
                }
            }
            if (!rn.contains(RomanNumeral.I)) {
                switch (rn.charAt(0)) {
                    case 'V':
                        che = RomanNumeral.V.getValue();
                        break;
                    case 'X':
                        che = RomanNumeral.X.getValue();
                        break;
                }
            }
        } else {
            if (rn.startsWith(RomanNumeral.I.toString())) {
                for (int i = 0; i < rn.length(); i++) {
                    che = che + 1;
                }
            }

        }
        if (che<1 || che>10){
            throw new ArithmeticException("Одно или несколько чисел выходят за диапазон");
        }
        return che;
    }

    public static String ArabicToRoman(int arb) {
        if (arb<1){
            throw new ArithmeticException("Недопустимое выражение в римских цифрах");
        }
    String rim="";
    int des=arb/10;
    int ed=arb%10;
    int sot=arb/100;
    String desToSt="";
    String edToSt="";
    String sotToSt="";

        if (sot>0){
            des=des/10;
            sotToSt=sotToSt+RomanNumeral.C;
        }
        if (des<4) {
            for (int i=1; i<=des;i++) {
                desToSt = desToSt + RomanNumeral.X;
            }
        }
        if (des==4) {desToSt = desToSt + RomanNumeral.XL;}
        if (des>=5 && des<9) {
                desToSt = desToSt + RomanNumeral.L;
            for (int i=1; i<=(des-5);i++) {
                desToSt = desToSt + RomanNumeral.X;
            }
        }
        if (des==9) {
                desToSt = desToSt + RomanNumeral.XC;
        }
        if (ed<4) {
            for (int i=1; i<=ed;i++) {
                edToSt = edToSt + RomanNumeral.I;
            }
        }
        if (ed==4) {edToSt = edToSt + RomanNumeral.IV;}
        if (ed>=5 && ed<9) {
            edToSt = edToSt + RomanNumeral.V;
            for (int i=1; i<=(ed-5);i++) {
                edToSt = edToSt + RomanNumeral.I;
            }
        }
        if (ed==9) {
            edToSt = edToSt + RomanNumeral.IX;
        }

    rim=sotToSt+desToSt+edToSt;

    return rim;
    }
}
