package state;

import type.StatementType;
import type.ValuesType;
import token.Values;

public class Initialization extends Statement {

    private String id;
    private Values value;

    public Initialization(String id, Values value){
        statementType = StatementType.INITIALIZATION;
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Values getValue() {
        return value;
    }
}
