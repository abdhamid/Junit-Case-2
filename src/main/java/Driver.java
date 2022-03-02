import Model.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    static void menu() {
        System.out.println();
        System.out.println("1 - Add Item");
        System.out.println("2 - Update Item");
        System.out.println("3 - Display Items");
        System.out.println("4 - Buy Items");
        System.out.println("5 - Show Number of Sold Items");
        System.out.println("0 - Exit Program");
    }

    public static void main(String[] args) {
        List<Item> inventory = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int pointer; //For navigating the menu

        do {
            menu();
            pointer = scanner.nextInt();
            switch (pointer) {
                case 1 : {
                    System.out.println("Enter item name : ");
                    String name = scanner.next();
                    System.out.println("Enter item stock : ");
                    int itemStock = scanner.nextInt();
                    System.out.println("Enter item points : ");
                    int itemPoint = scanner.nextInt();
                    addItem(inventory, name, itemStock, itemPoint);
                    break;
                }
                case 2 : {
                    System.out.println("Enter item name : ");
                    String name = scanner.next();
                    System.out.println("Enter new item stock : ");
                    int itemStock = scanner.nextInt();
                    System.out.println("Enter new item points : ");
                    int itemPoint = scanner.nextInt();
                    updateItem(inventory, name, itemStock, itemPoint);
//                    updateItem(inventory, "Phone", 50,30);
//                    System.out.println("Item Added");
                    break;
                }
                case 3 : {
                    displayItems(inventory);
                    break;
                }
                case 4: {
                    System.out.println("Enter item name : ");
                    String name = scanner.next();
                    System.out.println("Enter quantity : ");
                    int qty = scanner.nextInt();
                    System.out.println("Enter your current points : ");
                    int userPoints = scanner.nextInt();
                    buyItem(inventory, name, qty, userPoints);
                    break;
                }
                case 5: {
                    System.out.println("Enter item name : ");
                    String name = scanner.next();
                    showItemSold(inventory, name);
                    break;
                }
            }
        }
        while (pointer !=  0);
    }

    public static void addItem(List<Item> inventory, String itemName, int itemStock, int itemPoint){
        boolean isItemExist = false;
        if (inventory.size() > 0){
            for (Item item : inventory){
                if (itemName.equals(item.getItemName())) {
                    isItemExist = true;
                    break;
                }
            }
            if (isItemExist)
                System.out.println("Cannot duplicate item!");
            else {
                inventory.add(new Item(itemName, itemStock, itemPoint));
                System.out.println(itemName + " is added");
            }
        }
        else {
            inventory.add(new Item(itemName, itemStock, itemPoint));
            System.out.println(itemName + " is added");
        }
        
    }

    public static void displayItems(List<Item> inventory){
        System.out.println("================================\n" +
                            "Item\t\t" + "Stock\t\t" + "Points" + "\n" +
                            "================================");
        for (Item item: inventory) {
            System.out.println(item);
        }
    }

    public static void updateItem(List<Item> inventory, String itemName, int itemStock, int itemPoint){
        for(Item item : inventory){
            if(itemName.equals(item.getItemName())){
                System.out.println("Item name   : " + item.getItemName());
//                item.setItemName(itemName);
                System.out.println("Stock Changed   : " + item.getItemStock() +"    =>  " + itemStock);
                item.setItemStock(itemStock);
                System.out.println("Points Changed  : " + item.getItemPoints() +"   =>  " + itemPoint);
                item.setItemPoints(itemPoint);
//                System.out.println(item);
            } else {
                System.out.println("Item not found");
            }
        }
    }

    public static void showItemSold(List<Item> inventory, String itemName){
        for(Item item : inventory){
            if (itemName.equals(item.getItemName()))
                System.out.println("Sold Quantity of " + itemName + " = " + item.getItemSold());
        }
    }

    public static String buyItem(List<Item> inventory, String itemName, int qty, int userPoints){
        String result = null;
        for (Item item : inventory){
            if (itemName.equals(item.getItemName())){
                if(item.getItemStock() > 0 && qty > 0 ){
                    if (qty > item.getItemStock()){
                        System.out.println("[FAILED - BUY ITEM]: " + "Cannot buy " + qty + " " + item.getItemName() + "s. There's only " + item.getItemStock() + " of " + item.getItemName());
                        result = "FailedNotEnoughItems";
                        break;
                    }
                    if (userPoints >= qty * item.getItemPoints()){
                        item.setItemSold(item.getItemSold() + qty);
                        item.setItemStock(item.getItemStock() - qty);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        if (qty > 1)
                            System.out.println("[SUCCESS - BUY ITEM]: " + qty + " " + item.getItemName() + "s have been purchased [" + dtf.format(now) + "]");
                        else
                            System.out.println("[SUCCESS - BUY ITEM]: " + qty + " " + item.getItemName() +  " has been purchased [" + dtf.format(now) + "]");
                        System.out.println(item.getItemName() + "'s current stock = " + item.getItemStock());
                        result = "SuccessBuyingItem";
                    } else {
                        System.out.println("[FAILED - BUY ITEM]: Not enough points to buy " + qty + " " + item.getItemName());
                        result = "FailedNotEnoughPoints";
                    }
                } else {
                    System.out.println("[FAILED - BUY ITEM]: Cannot buy " + item.getItemName() + ". "+ item.getItemName() + " is sold out");
                    result = "FailedItemSoldOut";
                }
            }
        }
        return result;
    }

}
