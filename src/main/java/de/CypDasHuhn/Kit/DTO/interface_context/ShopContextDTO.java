package de.CypDasHuhn.Kit.DTO.interface_context;

import de.CypDasHuhn.Kit.DTO.ShopDTO;

public class ShopContextDTO {
    public ShopDTO shop;
    public boolean playing;
    public boolean editing;
    public boolean fromInterface;
    public boolean moving;
    public int money;

    public ShopContextDTO(ShopDTO shop, boolean playing, boolean editing, boolean moving, boolean fromInterface, int money) {
        this.shop = shop;
        this.playing = playing;
        this.editing = editing;
        this.moving = moving;
        this.fromInterface = fromInterface;
        this.money = money;
    }
}
