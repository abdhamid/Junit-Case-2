import Model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @Test
    @DisplayName("Test Buy Item Function")
    void buyItem() {
        List<Item> inventory = new ArrayList<>();

        System.out.println("ADDING ITEMS:");
        Driver.addItem(inventory, "Phone", 50,100);
        Driver.addItem(inventory, "Mug", 100,20);
        Driver.addItem(inventory, "Sticker", 20,5);
        Driver.displayItems(inventory);

        System.out.println("\nBUYING ITEMS:");
        System.out.println("TEST 1:");
        assertEquals("SuccessBuyingItem", Driver.buyItem(inventory, "Mug", 5, 1000));
        Driver.displayItems(inventory);

        System.out.println("\nTEST 2:");
        assertEquals("FailedNotEnoughItems", Driver.buyItem(inventory, "Mug", 100, 1000));
        Driver.displayItems(inventory);

        System.out.println("\nTEST 3:");
        assertEquals("FailedNotEnoughPoints", Driver.buyItem(inventory, "Phone", 2, 100));
        Driver.displayItems(inventory);

        System.out.println("\nTEST 4:");
        assertEquals("SuccessBuyingItem", Driver.buyItem(inventory, "Sticker", 20, 1000));
        Driver.displayItems(inventory);

        System.out.println("\nTEST 5:");
        assertEquals("FailedItemSoldOut", Driver.buyItem(inventory, "Sticker", 50, 1000));
        Driver.displayItems(inventory);
    }
}