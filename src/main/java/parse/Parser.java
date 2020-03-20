package parse;

public class Parser {
    public static int NAN = -1;

    public static int parseID(String num) {
        int id = NAN;
        try {
            id = Integer.parseInt(num);
        }
        catch (NumberFormatException | NullPointerException ignored) {}
        return Integer.toString(id).equals(num) ? id : NAN;
    }
}
