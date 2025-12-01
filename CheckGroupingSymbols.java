import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
    This program:

    - Uses a Stack
    - eads the file from command-line argument
    - Checks ( ), { }, and [ ]
    - Ignores characters that are not grouping symbols
    - Detects mismatched symbols and overlapping pairs
    - Reports whether the file is balanced or not
 */
public class CheckGroupingSymbols {

    public static void main(String[] args) {

        // Ensure a filename is supplied
        if (args.length != 1) {
            System.out.println("Usage: java CheckGroupingSymbols filename.java");
            System.exit(1);
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            System.out.println("File does not exist: " + args[0]);
            System.exit(2);
        }

        // Stack to hold opening symbols
        Stack<Character> stack = new Stack<>();

        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);

                    // Check opening symbols
                    if (ch == '(' || ch == '{' || ch == '[') {
                        stack.push(ch);
                    }

                    // Check closing symbols
                    else if (ch == ')' || ch == '}' || ch == ']') {
                        if (stack.isEmpty()) {
                            System.out.println("Mismatch found: Extra closing symbol '" + ch + "'");
                            System.exit(3);
                        }

                        char open = stack.pop();

                        if (!matches(open, ch)) {
                            System.out.println("Mismatch: Expected matching closing for '" + open +
                                        "', but found '" + ch + "'");
                            System.exit(4);
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + args[0]);
            System.exit(5);
        }

        // After reading all characters
        if (stack.isEmpty()) {
            System.out.println("All grouping symbols are correctly matched.");
        } else {
            System.out.println("Mismatch: Missing closing symbol(s) for opening symbol(s).");
        }
    }

    /** Check if an opening and closing symbol match */
    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')') ||
            (open == '{' && close == '}') ||
            (open == '[' && close == ']');
    }
}
