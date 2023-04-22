package inventory_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    // 处理交易Transactions
    private static void processTransactions(ArrayList<InventoryItem> inventory, ArrayList<String> transactions, ArrayList<String> errors, ArrayList<String> shipping) {
// 创建一个ArrayList来存储每个客户和货物的发货数量
        ArrayList<String> shippingList = new ArrayList<>();
        // 遍历所有交易
        for (String transaction : transactions) {
            // 分割交易
            String[] parts = transaction.split("\t");
            char type = parts[0].charAt(0); // 交易类型
            String itemNumber = parts[1]; // 货物编号
            int index = findItem(inventory, itemNumber); // 查找库存中的货物

            switch (type) {
                case 'O': // 出货
                    int quantity = Integer.parseInt(parts[2]); // 出货数量
                    String customer = parts[3]; // 客户名称

                    // 如果库存中存在该货物，且库存数量足够，则更新库存并记录发货数量
                    if (index != -1 && inventory.get(index).quantity >= quantity) {
                        inventory.get(index).quantity -= quantity; // 减去出货数量
                        shippingList.add(customer + "\t" + itemNumber + "\t" + quantity); // 添加发货信息到shippingList中
                    } else {
                        // 添加客户名称、货物编号和发货数量到错误记录中
                        errors.add(customer + "\t" + itemNumber + "\t" + quantity);
                    }
                    break;

                case 'R': // 收货
                    quantity = Integer.parseInt(parts[2]); // 收货数量
                    if (index != -1) {
                        inventory.get(index).quantity += quantity; // 更新库存数量
                    } else {
                        // 添加货物编号和收货数量到错误记录中
                        errors.add("0" + "\t" + itemNumber + "\t" + quantity);
                    }
                    break;

                case 'A': // 添加新货物
                    String supplier = parts[2]; // 供应商
                    String description = parts[3]; // 货物描述
                    inventory.add(new InventoryItem(itemNumber, 0, supplier, description)); // 添加新货物到库存中
                    break;

                case 'D': // 删除货物
                    if (index != -1) {
                        if (inventory.get(index).quantity == 0) { // 如果库存数量为0，则直接删除该货物
                            inventory.remove(index);
                        } else {
                            // 添加货物编号和库存数量到错误记录中
                            errors.add("0" + "\t" + itemNumber + "\t" + inventory.get(index).quantity);
                        }
                    }
                    break;
            }
        }

        // 将shippingList中的所有发货信息添加到shipping列表中
        shipping.addAll(shippingList);
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
