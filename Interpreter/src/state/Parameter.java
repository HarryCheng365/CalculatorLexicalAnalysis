package state;

import type.ValuesType;

public class Parameter {

    private ValuesType dataType;
    private String parameterName;

    public Parameter(ValuesType dataType, String parameterName) {
        super();
        this.dataType = dataType;
        this.parameterName = parameterName;
    }

    public ValuesType getDataType() {
        return dataType;
    }

    public void setDataType(ValuesType dataType) {
        this.dataType = dataType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }


    @Override
    public String toString() {
        return String.format("%s %s", dataType, parameterName);
    }
}

