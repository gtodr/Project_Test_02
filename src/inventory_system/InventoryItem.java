package inventory_system;

//货物信息
public class InventoryItem {
    String itemNumber;
    int quantity;
    String supplier;
    String description;

    public InventoryItem(String itemNumber, int quantity, String supplier, String description){
        this.itemNumber=itemNumber;
        this.quantity=quantity;
        this.supplier=supplier;
        this.description=description;
    }

}
