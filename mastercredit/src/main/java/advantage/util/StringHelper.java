package advantage.util;

/**
 * Helpful {@link String} methods.
 * @author Binyamin Regev on 21/12/2015.
 */
public class StringHelper {

    /**
     * Receives {@link String} containing words and capitalize the first
     * letter of each word, while making all other lettes lower case.
     * @param source {@link String} to make first letter upper case and
     *                             all other letters are lower case.
     * @return {@link String} in which the first is UPPER case and all
     * other letters are lower case.
     */
    public static String capitalizeFirstLetterOfEachWord(final String source, final String seperator) {
        StringBuffer res = new StringBuffer();

        //  Split by SPACE (" ")
        String[] strArr = source.split(seperator);

        //  For Each String in the array of strings
        for (String str : strArr) {

            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(StringHelper.toInitCap(str)).append(" ");
        }

        return res.toString().trim();

    }

    /**
     * Receives {@link String} containing words seperated by a single
     * character or {@link String} and capitalize the firstletter of
     * each word, while making all other lettes lower case.
     * By default the seperator is {@code SPACE}.
     * @param source {@link String} to make first letter upper case and
     *                             all other letters are lower case.
     * @return {@link String} in which the first is UPPER case and all
     * other letters are lower case.
     */
    public static String capitalizeFirstLetterOfEachWord(final String source) {
        return capitalizeFirstLetterOfEachWord(source, "\\s");
    }

    /**
     * Receives {@link String} and capitalize its first letter.
     * @param source {@link String} to make first letter upper case and
     *                             all other letters are lower case.
     * @return {@link String} in which the first is UPPER case and all
     * other letters are lower case.
     */
    public static String toInitCap(final String source) {
        char[] stringArray = source.trim().toLowerCase().toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);

        //System.out.print("Result: " + new String(stringArray));
        return new String(stringArray);
    }
}
