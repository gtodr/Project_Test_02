package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter("/Users/dwayne/Desktop/CS/Java/CourseTest/Project_Test_02/src/test/Shipping1.txt"));
            for (int i = 0; i < 3; i++) {
                writer.write("11");
                writer.write("\n");
            }
            writer.close();

    }

}
