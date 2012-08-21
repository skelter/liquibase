package liquibase.database.structure;

import java.math.BigInteger;

public class DataType {

    private String typeName;

    private Integer dataTypeId;
    private Integer columnSize;
    private ColumnSizeUnit columnSizeUnit;

    private Integer decimalDigits;
    private AutoIncrementInformation autoIncrementInformation;
    private Integer radix;
    private Integer characterOctetLength;

    public DataType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public ColumnSizeUnit getColumnSizeUnit() {
        return columnSizeUnit;
    }

    public void setColumnSizeUnit(ColumnSizeUnit columnSizeUnit) {
        this.columnSizeUnit = columnSizeUnit;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public boolean isAutoIncrement() {
        return autoIncrementInformation != null;
    }

    public AutoIncrementInformation getAutoIncrementInformation() {
        return autoIncrementInformation;
    }

    public void setAutoIncrementInformation(AutoIncrementInformation autoIncrementInformation) {
        this.autoIncrementInformation = autoIncrementInformation;
    }

    @Override
    public String toString() {
        String value = typeName;
        boolean unsigned = false;
        if (value.toLowerCase().endsWith(" unsigned")) {
            value = value.substring(0, value.length()-" unsigned".length());
            unsigned = true;
        }

        if (columnSize != null) {
            value += "(";
            value += columnSize;

            if (decimalDigits != null) {
                value+= ", "+decimalDigits ;
            }

            value +=")";
        }

        if (unsigned) {
            value += " UNSIGNED";
        }

        return value;
    }

    public Integer getRadix() {
        return radix;
    }

    public void setRadix(Integer radix) {
        this.radix = radix;
    }

    public Integer getCharacterOctetLength() {
        return characterOctetLength;
    }

    public void setCharacterOctetLength(Integer characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }


    public static enum ColumnSizeUnit {
        BYTE,
        CHAR,
    }

    public static class AutoIncrementInformation {
        private BigInteger startWith;
        private BigInteger incrementBy;

        public AutoIncrementInformation() {
            this(BigInteger.valueOf(1),BigInteger.valueOf(1));
        }

        public AutoIncrementInformation(BigInteger startWith, BigInteger incrementBy) {
            this.startWith = startWith;
            this.incrementBy = incrementBy;
        }

        public BigInteger getStartWith() {
            return startWith;
        }

        public BigInteger getIncrementBy() {
            return incrementBy;
        }
    }
}
