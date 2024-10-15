import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayList;


//参考配置：
//System.out.println("test");
//String filePath = "opening_templates.csv";
//String filePathTemp = "opening_templates_base.csv";
//TemplateMatch.lu_bang_xin(filePathTemp,filePath)
//之后可以使用TemplateMatch.readCsvToIntArray(filePath)读出需要的数组



public class TemplateMatch {
    //开局模板库基础版本
    //{name,x1,y1,x2,y2}
    //变化只做了旋转，对称情况需要自己考虑
    private static final int[][] Opening_template_library_stander = new int[][]{
            {1,0,-1,1,1},
            {1,0,-1,-1,1},
            //{1,-1,0,1,-1},
            //{1,-1,-1,1,0},
            //{1,-1,1,1,0},
            //{1,0,-1,-1,1},
            //{1,-1,0,1,1},
            //{1,1,-1,0,1},
            //{1,-1,-1,1,0}
            {2,-1,-1,2,0},
            {2,1,-1,-2,0},
            {3,-1,-1,1,-1},
            {4,-1,-1,0,-2},
            {4,1,-1,0,-2},
            {5,1,0,0,-1},
            {6,-1,-1,1,-2},
            {6,1,-1,-1,-2},
            {7,0,-2,2,0},
            {8,0,-1,1,-1},
            {9,0,-2,2,-2},
            {10,1,1,-1,-1},
            {11,2,0,0,-1},
            {12,-1,0,1,2},
            {13,0,-2,2,2},
            {14,0,1,0,-1},
            {15,-2,-2,0,-4},
            {16,-2,-1,-1,-2},
            {17,-1,-2,1,-2},
            {18,0,2,0,-2},
            {19,0,-2,2,-1},
            {20,-1,-2,-2,-2},
            {21,0,-1,1,-2},
            {22,0,-1,2,1},
            {23,-1,-1,2,-1},
            {24,0,-1,1,-2},
            {25,-1,-1,2,1},
            {26,-1,-1,-1,-2},
            {27,0,-1,2,2},
            {28,0,-1,2,-1},
            {29,0,-1,0,-2},
            {30,-1,-2,2,-1},
            {31,-3,-1,-1,-3}
    };
    // 静态方法，用于将数组保存为CSV文件
    public static void saveToCsv(String filePath,int[][] tt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : tt) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(Integer.toString(row[i]));
                    // 不是每行的最后一个逗号时（即不是i等于row.length-1时），写入逗号
                    if (i < row.length - 1) {
                        writer.write(",");
                    }
                }
                // 写入换行符以分隔不同的行（现在是在内部循环之后）
                writer.newLine();
            }
            System.out.println("CSV file has been created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //读取csv文件中的数组
    public static int[][] readCsvToIntArray(String filePath) {
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 使用逗号作为分隔符拆分每行
                String[] fields = line.split(",");

                // 创建一个整数数组来存储转换后的字段
                int[] intFields = new int[fields.length];
                boolean validRow = true;

                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i].trim();
                    try {
                        // 尝试将字段解析为整数
                        intFields[i] = Integer.parseInt(field);
                    } catch (NumberFormatException e) {
                        // 如果解析失败，可以记录错误、跳过该行或使用默认值
                        // 这里我们简单地设置validRow为false并跳出循环
                        validRow = false;
                        break;
                    }
                }

                // 如果整行数据都有效，则将其添加到列表中
                if (validRow) {
                    rows.add(intFields);
                }
                // 可选：如果你想记录或处理解析失败的行，可以在这里添加代码
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将List<int[]>转换为int[][]
        int[][] csvArray = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            csvArray[i] = rows.get(i);
        }
        return csvArray;
    }

    //打印二维数组
    public static void print2DArray(int[][] array) {
        // 遍历数组的行
        for (int i = 0; i < array.length; i++) {
            // 遍历数组的列
            for (int j = 0; j < array[i].length; j++) {
                // 打印当前元素，并在元素之间添加空格或制表符（Tab）进行分隔
                System.out.print(array[i][j] + "\t");
            }
            // 打印完一行后换行
            System.out.println();
        }
    }
    //鲁棒性函数:将csv中数据进行对称，旋转。
    public static void lu_bang_xin (String filePath,String filePath1) {
        int[][] array = readCsvToIntArray(filePath);
        //print2DArray(array);
        List<int[]> rows = new ArrayList<>();
        // 遍历数组的行
        for (int i = 0; i < array.length; i++) {
            int[] Xz = array[i];
            int name = Xz[0]; //第一位的编号
            //System.out.print(Xz[4]);
            //旋转90度
            int[] Xz90 = new int[array[i].length];
            Xz90[0] = name;
            Xz90[1] = -Xz[2];//棋子1的x变化
            Xz90[2] = Xz[1]; //棋子1的y变化
            Xz90[3] = -Xz[4];//棋子2的x变化
            Xz90[4] = Xz[3]; //棋子2的y变化
            //旋转180度
            int[] Xz180 = new int[array[i].length];
            Xz180[0] = name;
            Xz180[1] = -Xz[1];//棋子1的x变化
            Xz180[2] = -Xz[2]; //棋子1的y变化
            Xz180[3] = -Xz[3];//棋子2的x变化
            Xz180[4] = -Xz[4]; //棋子2的y变化

            //旋转270度
            int[] Xz270 = new int[array[i].length];
            Xz270[0] = name;
            Xz270[1] = Xz[2];//棋子1的x变化
            Xz270[2] = -Xz[1]; //棋子1的y变化
            Xz270[3] = Xz[4];//棋子2的x变化
            Xz270[4] = -Xz[3]; //棋子2的y变化
            rows.add(Xz);       // 添加原始数组
            rows.add(Xz90);     // 添加旋转90度的数组
            rows.add(Xz180);    // 添加旋转180度的数组
            rows.add(Xz270);    // 添加旋转270度的数组

        }
        // 将列表转换为二维数组
        int[][] resultArray = new int[rows.size()][rows.get(0).length];
        for (int i = 0; i < rows.size(); i++) {
            resultArray[i] = rows.get(i);
        }
        //print2DArray(resultArray);
        //将数据保存
        saveToCsv(filePath1,resultArray);
    }


}


