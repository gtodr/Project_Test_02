package test;

import inventory_system.InventoryItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class trash {
    //    private static void processTransactions(ArrayList<InventoryItem> inventory, ArrayList<String> transactions, ArrayList<String> errors, ArrayList<String> shipping) {
//        // 创建一个HashMap用于存储客户编号、货物编号和发货单数量
//        HashMap<String, Integer> shippingMap = new HashMap<>();
//        // 遍历交易记录列表
//        for (String transaction : transactions) {
//            // 按照制表符（\t）分割交易记录，获取交易类型、货物编号和其他信息
//            String[] parts = transaction.split("\t");
//            char type = parts[0].charAt(0); // 获取交易类型
//            String itemNumber = parts[1]; // 获取货物编号
//            // 在库存列表中查找货物编号对应的物品
//            int index = findItem(inventory, itemNumber);
//            int quantity;
//            // 根据交易类型执行不同的操作
//            switch (type) {
//                // 如果交易类型是O，表示客户下了订单，需要减少库存，并将发货信息添加到shippingMap中
//                case 'O':
//                    quantity = Integer.parseInt(parts[2]); // 获取订单数量
//                    String customer = parts[3]; // 获取客户编号
//                    if (index != -1 && inventory.get(index).quantity >= quantity) {
//                        // 如果找到了对应的库存，并且库存足够，减少库存数量
//                        inventory.get(index).quantity -= quantity;
//                        // 将客户编号、货物编号和订单数量作为key，发货单数量作为值，添加到shippingMap中
//                        String key = customer + "\t" + itemNumber;
//                        shippingMap.put(key, shippingMap.getOrDefault(key, 0) + quantity);
//                    } else {
//                        // 如果找不到对应的库存，或者库存不足，将客户编号、货物编号和订单数量添加到错误记录
//                        errors.add(customer + "\t" + itemNumber + "\t" + quantity);
//                    }
//                    break;
//                // 如果交易类型是R，增加库存
//                case 'R':
//                    quantity = Integer.parseInt(parts[2]); // 获取增加数量
//                    if (index != -1) {
//                        // 如果找到了对应的库存，增加库存数量
//                        inventory.get(index).quantity += quantity;
//                    } else {
//                        // 如果找不到对应的库存，将货物编号和退货数量添加到错误记录
//                        errors.add("0" + "\t" + itemNumber + "\t" + quantity);
//                    }
//                    break;
//                // 如果交易类型是A，表示要添加新的物品到库存列表中
//                case 'A':
//                    String supplier = parts[2]; // 获取供应商信息
//                    String description = parts[3]; // 获取物品描述
//                    // 将新物品添加到库存列表中
//                    inventory.add(new InventoryItem(itemNumber, 0, supplier, description));
//                    break;
//                // 如果交易类型是D，表示要删除库存中某个物品
//                case 'D':
//                    if (index != -1) {
//                        // 如果找到了对应的库存
//                        if (inventory.get(index).quantity == 0) {
//                            // 如果库存数量为0，直接从库存列表中
//                            inventory.remove(index);
//                        } else {
//                            // 添加客户编号（0）、货物编号和库存数量到错误记录
//                            errors.add("0" + "\t" + itemNumber + "\t" + inventory.get(index).quantity);
//                        }
//                    }
//                    break;
//            }
//        }
//
//
//        // 将发货信息添加到shipping列表中
//        for (Map.Entry<String, Integer> entry : shippingMap.entrySet()) {
//            shipping.add(entry.getKey() + "\t" + entry.getValue());
//        }
//    }
}

public class Inventory {
    public static void main(String[] args) throws IOException {

        ArrayList<InventoryItem> inventory = new ArrayList<>();
        ArrayList<String> transactions = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();
        ArrayList<String> shipping = new ArrayList<>();

        // 读取Inventory
        readInventoryFile(inventory, "/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/inventory_system/Inventory.txt");

        // 读取Transactions
        readTransactionsFile(transactions, "/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/inventory_system/Transactions.txt");

        // 处理交易Transactions
        processTransactions(inventory, transactions, errors, shipping);

        // 写入Shipping
        writeFile(shipping, "/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/inventory_system/Shipping.txt");

        // 写入Errors
        writeFile(errors, "/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/inventory_system/Error.txt");

        // 更新库存文件
        updateInventory(inventory, "/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/inventory_system/NewInventory.txt");

    }

    //读取Inventory
    private static void readInventoryFile(ArrayList<InventoryItem> inventory, String fileName) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s");
                inventory.add(new InventoryItem(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取Transactions
    private static void readTransactionsFile(ArrayList<String> transactions, String fileName) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 写入文件
    private static void writeFile(ArrayList<String> list, String fileName) throws IOException {
//未释放资源（writer.close();），写入失败
//        try {
//            BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
        for (String line : list) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();

    }


        private static void processTransactions(ArrayList<InventoryItem> inventory, ArrayList<String> transactions, ArrayList<String> errors, ArrayList<String> shipping) {
        // 创建一个HashMap用于存储客户编号、货物编号和发货单数量
        HashMap<String, Integer> shippingMap = new HashMap<>();
        // 遍历交易记录列表
        for (String transaction : transactions) {
            // 按照制表符（\t）分割交易记录，获取交易类型、货物编号和其他信息
            String[] parts = transaction.split("\t");
            char type = parts[0].charAt(0); // 获取交易类型
            String itemNumber = parts[1]; // 获取货物编号
            // 在库存列表中查找货物编号对应的物品
            int index = findItem(inventory, itemNumber);
            int quantity;
            // 根据交易类型执行不同的操作
            switch (type) {
                // 如果交易类型是O，表示客户下了订单，需要减少库存，并将发货信息添加到shippingMap中
                case 'O':
                    quantity = Integer.parseInt(parts[2]); // 获取订单数量
                    String customer = parts[3]; // 获取客户编号
                    if (index != -1 && inventory.get(index).quantity >= quantity) {
                        // 如果找到了对应的库存，并且库存足够，减少库存数量
                        inventory.get(index).quantity -= quantity;
                        // 将客户编号、货物编号和订单数量作为key，发货单数量作为值，添加到shippingMap中
                        String key = customer + "\t" + itemNumber;
                        shippingMap.put(key, shippingMap.getOrDefault(key, 0) + quantity);
                    } else {
                        // 如果找不到对应的库存，或者库存不足，将客户编号、货物编号和订单数量添加到错误记录
                        errors.add(customer + "\t" + itemNumber + "\t" + quantity);
                    }
                    break;
                // 如果交易类型是R，增加库存
                case 'R':
                    quantity = Integer.parseInt(parts[2]); // 获取增加数量
                    if (index != -1) {
                        // 如果找到了对应的库存，增加库存数量
                        inventory.get(index).quantity += quantity;
                    } else {
                        // 如果找不到对应的库存，将货物编号和退货数量添加到错误记录
                        errors.add("0" + "\t" + itemNumber + "\t" + quantity);
                    }
                    break;
                // 如果交易类型是A，表示要添加新的物品到库存列表中
                case 'A':
                    String supplier = parts[2]; // 获取供应商信息
                    String description = parts[3]; // 获取物品描述
                    // 将新物品添加到库存列表中
                    inventory.add(new InventoryItem(itemNumber, 0, supplier, description));
                    break;
                // 如果交易类型是D，表示要删除库存中某个物品
                case 'D':
                    if (index != -1) {
                        // 如果找到了对应的库存
                        if (inventory.get(index).quantity == 0) {
                            // 如果库存数量为0，直接从库存列表中
                            inventory.remove(index);
                        } else {
                            // 添加客户编号（0）、货物编号和库存数量到错误记录
                            errors.add("0" + "\t" + itemNumber + "\t" + inventory.get(index).quantity);
                        }
                    }
                    break;
            }
        }


        // 将发货信息添加到shipping列表中
        for (Map.Entry<String, Integer> entry : shippingMap.entrySet()) {
            shipping.add(entry.getKey() + "\t" + entry.getValue());
        }
    }


    // 在库存中查找货物
    private static int findItem(ArrayList<InventoryItem> inventory, String itemNumber) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).itemNumber.equals(itemNumber)) {
                return i;
            }
        }
        return -1;
    }

    // 更新库存文件
    private static void updateInventory(ArrayList<InventoryItem> inventory, String fileName) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        for (InventoryItem item : inventory) {
            lines.add(item.itemNumber + "\t" + item.quantity + "\t" + item.supplier + "\t" + item.description);
        }
        writeFile(lines, fileName);
    }


}
