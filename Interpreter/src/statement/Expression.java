package statement;

import token.ExpressionToken;
import type.StatementType;

public class Expression extends Statement {
    private ExpressionToken root;

    public Expression(){
        statementType = StatementType.EXPRESSION;
    }

    public ExpressionToken getExpressionToken() {
        return root;
    }

    public void setExpressionToken(ExpressionToken root) {
        this.root = root;
    }

	@Override
	public String display() {
		return "<Expression Statement>\n"+this.root.display()+"<Expression Statement>\n";
	}
}