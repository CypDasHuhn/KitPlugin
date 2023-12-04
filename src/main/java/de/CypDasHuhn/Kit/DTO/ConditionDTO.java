package de.CypDasHuhn.Kit.DTO;

public class ConditionDTO {
    public String field;
    public String operator;
    public Object value;
    public ConditionDTO(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}
