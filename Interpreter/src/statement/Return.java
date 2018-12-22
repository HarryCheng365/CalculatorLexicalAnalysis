package statement;
import token.ExpressionToken;
import type.StatementType;
public class Return extends Statement{
	private ExpressionToken returnValue;

    public Return(){
        statementType = StatementType.RETURN;
    }

    public ExpressionToken getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ExpressionToken returnValue) {
        this.returnValue = returnValue;
    }

	@Override
	public String display() {
		return "<Return Statement>"+"\n"+this.returnValue.display()+"\n"+"<ReturnStatement>";
	
	}
    
}




