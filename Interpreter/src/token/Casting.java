package token;

import type.ValuesType;
import type.KeywordType;

public class Casting {

    public static ValuesType KeywordTypeToValueType(KeywordType type) {
        if (type == KeywordType.INT)
            return ValuesType.INTEGER;
        else if (type == KeywordType.DOUBLE)
            return ValuesType.DOUBLE;
        else if (type == KeywordType.STRING)
            return ValuesType.STRING;
        else if (type == KeywordType.BOOL)
            return ValuesType.BOOLEAN;
        else if (type == KeywordType.CHAR)
            return ValuesType.CHAR;
        else if (type == KeywordType.VOID)
            return ValuesType.CHAR;
        else
            return null;
    }

}