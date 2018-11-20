package exception;

public class SyntaxException extends CMMException {
	public static String message;

    public SyntaxException(int lines, int pos, String msg) {
    	super("Syntax Exception", lines, pos, msg);
    	message=msg;
    }

    public SyntaxException(int lines, String msg) {
        this(lines, -1, msg);
        message =msg;
    }

    public SyntaxException(String msg) {
        this(-1, -1, msg);
        message=msg;
    }

    public SyntaxException() {
        this(-1, -1, "Unknown error");
        message="Unknown error";
    }

}