package de.CypDasHuhn.Kit.DTO.interface_context;

public class ConfirmationContextDTO {
    public KitContextDTO kitContext;
    public String confirmationType;

    public ConfirmationContextDTO(KitContextDTO kitContext, String confirmationType) {
        this.kitContext = kitContext;
        this.confirmationType = confirmationType;
    }
}
