import java.util.Scanner;

public class Main {

    interface CharStack {
        void push(char c);

        char pop();

        char peek();

        boolean isEmpty();
    }

    static class ArrayCharStack implements CharStack {
        private char[] data;
        private int top;

        ArrayCharStack(int capacity) {
            data = new char[Math.max(4, capacity)];
            top = -1;
        }

        @Override
        public void push(char c) {
            if (top + 1 == data.length) {
                char[] nd = new char[data.length * 2];
                System.arraycopy(data, 0, nd, 0, data.length);
                data = nd;
            }
            data[++top] = c;
        }

        @Override
        public char pop() {
            if (isEmpty())
                throw new IllegalStateException("Stack kosong");
            return data[top--];
        }

        @Override
        public char peek() {
            if (isEmpty())
                throw new IllegalStateException("Stack kosong");
            return data[top];
        }

        @Override
        public boolean isEmpty() {
            return top < 0;
        }
    }

    static class DoubleStack {
        private double[] data;
        private int top = -1;

        DoubleStack(int cap) {
            data = new double[cap];
        }

        void push(double v) {
            data[++top] = v;
        }

        double pop() {
            return data[top--];
        }
    }

    enum TokenType {
        OPERAND, OPERATOR, LPAREN, RPAREN
    }

    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    static boolean isValidInfix(String input, String stackImpl) {
        if (input == null)
            return false;
        String s = input.trim();
        if (s.isEmpty())
            return false;

        ArrayCharStack st = new ArrayCharStack(s.length());
        TokenType prev = null;
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c)) {
                if (prev == TokenType.OPERAND || prev == TokenType.RPAREN)
                    return false;
                while (i < s.length() && Character.isDigit(s.charAt(i)))
                    i++;
                prev = TokenType.OPERAND;
                continue;
            }

            if (c == '(') {
                if (prev == TokenType.OPERAND || prev == TokenType.RPAREN)
                    return false;
                st.push(c);
                prev = TokenType.LPAREN;
                i++;
                continue;
            }

            if (c == ')') {
                if (st.isEmpty() || prev == TokenType.OPERATOR || prev == TokenType.LPAREN)
                    return false;
                st.pop();
                prev = TokenType.RPAREN;
                i++;
                continue;
            }

            if (isOperator(c)) {
                if (prev == null || prev == TokenType.OPERATOR || prev == TokenType.LPAREN)
                    return false;
                prev = TokenType.OPERATOR;
                i++;
                continue;
            }
            return false;
        }
        return prev != TokenType.OPERATOR && st.isEmpty();
    }

    static int getOperatorPriority(char op) {
        if (op == '+' || op == '-')
            return 1;
        if (op == '*' || op == '/')
            return 2;
        return 0;
    }

    static String toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        ArrayCharStack stack = new ArrayCharStack(infix.length());

        for (char c : infix.toCharArray()) {
            if (Character.isWhitespace(c))
                continue;

            if (Character.isDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    output.append(stack.pop());
                stack.pop();
            } else {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                        getOperatorPriority(stack.peek()) >= getOperatorPriority(c))
                    output.append(stack.pop());
                stack.push(c);
            }
        }

        while (!stack.isEmpty())
            output.append(stack.pop());

        return output.toString();
    }

    static String toPrefix(String infix) {
        String rev = new StringBuilder(infix).reverse().toString();
        rev = rev.replace('(', '#').replace(')', '(').replace('#', ')');
        return new StringBuilder(toPostfix(rev)).reverse().toString();
    }

    static double evaluatePostfixWithSteps(String postfix) {
        DoubleStack stack = new DoubleStack(postfix.length());

        System.out.println("\nProses evaluasi postfix:");
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
                System.out.println("Push operand: " + (c - '0'));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                double r = switch (c) {
                    case '+' -> a + b;
                    case '-' -> a - b;
                    case '*' -> a * b;
                    case '/' -> a / b;
                    default -> 0;
                };
                System.out.println(a + " " + c + " " + b + " = " + r);
                stack.push(r);
            }
        }
        double result = stack.pop();
        System.out.println("Hasil akhir postfix: " + result);
        return result;
    }

    static double evaluatePrefixWithSteps(String prefix) {
        DoubleStack stack = new DoubleStack(prefix.length());

        System.out.println("\nProses evaluasi prefix:");
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
                System.out.println("Push operand: " + (c - '0'));
            } else {
                double a = stack.pop();
                double b = stack.pop();
                double r = switch (c) {
                    case '+' -> a + b;
                    case '-' -> a - b;
                    case '*' -> a * b;
                    case '/' -> a / b;
                    default -> 0;
                };
                System.out.println(a + " " + c + " " + b + " = " + r);
                stack.push(r);
            }
        }
        double result = stack.pop();
        System.out.println("Hasil akhir prefix: " + result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Masukkan notasi infix: ");
            String infix = sc.nextLine();

            if (isValidInfix(infix, "array")) {
                System.out.println("✅ Infix VALID");

                String postfix = toPostfix(infix);
                String prefix = toPrefix(infix);

                System.out.println("Postfix : " + postfix);
                System.out.println("Prefix  : " + prefix);

                evaluatePostfixWithSteps(postfix);
                evaluatePrefixWithSteps(prefix);
                break;
            } else {
                System.out.println("❌ Infix TIDAK valid\n");
                System.out.println("Contoh valid: 5 + 4 / 5");
                System.out.println("Contoh tidak valid: 5 * 5 +");
            }
        }
        sc.close();
    }
}