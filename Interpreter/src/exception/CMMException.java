package exception;

public class CMMException extends Exception {

    protected String exceptionType;
    protected int lines;
    protected int pos;

    public CMMException(String exceptionType, int lines, int pos, String msg) {
        super(msg);
        this.exceptionType = exceptionType;
        this.lines = lines;
        this.pos = pos;
    }

    public CMMException(int lines, int pos, String msg) {
        this("CMM Exception", lines, pos, msg);
    }

    public CMMException(int lines, String msg) {
        this("CMM Exception", lines, -1, msg);
    }

    public CMMException(String msg) {
        this("CMM Exception", -1, -1, msg);
    }

    public CMMException() {
        this("CMM Exception", -1, -1, "Unknown error");
    }

    @Override
    public String toString() {
        String msg;
        if (pos == -1 && lines == -1)
            msg = String.format("%s : %s", exceptionType, this.getMessage());
        else if (pos == -1 && lines != -1)
            msg = String.format("%s in line %d : %s", exceptionType, lines, this.getMessage());
        else
            msg = String.format("%s in line %d, position %d : %s", exceptionType, lines, pos, this.getMessage());
        return msg;
    }

}