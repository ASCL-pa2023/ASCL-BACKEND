package esgi.ascl.utils;

public class NumberUtils {

    public static boolean isPowerOfTwo(int n) {
        return (n & (n - 1)) == 0;
    }

    public static int nextPowerOfTwo(int n) {
        int multiple = 2;
        while (multiple < n) {
            multiple *= 2;
        }
        return multiple;
    }

    public static void main(String[] args) {
        int input = 17;
        System.out.println(isPowerOfTwo(input));
        System.out.println(nextPowerOfTwo(input));
    }
}

