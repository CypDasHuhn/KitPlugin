package de.CypDasHuhn.Kit.DTO.Filter;

import java.util.List;

public class ConditionSetDTO {
    public ConditionDTO condition;
    public List<ConditionSetDTO> conditionSets;
    public String operatorType;
    public ConditionSetDTO parentConditionSet;

    public ConditionSetDTO(ConditionDTO condition, List<ConditionSetDTO> conditionSets, String operatorType, ConditionSetDTO parentConditionSet) {
        this.condition = condition;
        this.conditionSets = conditionSets;
        this.operatorType = operatorType;
        this.parentConditionSet = parentConditionSet;
    }
}
