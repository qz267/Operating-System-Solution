import java.io.BufferedReader;
import java.io.InputStreamReader;
public class LRU {
 int blockCount;
 int seriaCount;
 int[] address;
 int[] stack;
 BufferedReader br;
 public static void main(String[] args) {
  // int address[] = { 1, 2, 3, 2, 8, 4, 3, 9, 4, 1, 2, 4, 6, 8, 2 };
  LRU lru = new LRU();
  lru.init();
  lru.display();
  System.out.println("=====LRU演示算法结束=====");
  System.out.println("系统退出！！！");
  
 }
 public void init() {
  try {
   br = new BufferedReader(new InputStreamReader(System.in));
  } catch (Exception e) {
   e.printStackTrace();
   System.exit(0);
  }
  System.out.println("===LRU页面置换算法演示===");
  System.out.println("请输入物理块数:");
  blockCount = readInt();
  stack = new int[blockCount];
  System.out.println("请输入访问内存的块序列的个数:");
  seriaCount = readInt();
  System.out.println("请输入访问内存的" + seriaCount + "块序列,中间已空格分隔:");
  address = readIntArray();
 }
 public void display() {
  // int[] stack = new int[4];
  boolean flag;
  System.out.println("地址序列：");
  for (int m = 0; m < address.length; m++)
   System.out.print(address[m] + " ");
  System.out.print("\n");
  System.out.println("逐次的交换情况：");
  for (int i = 0; i < address.length; i++) {
   int j = 0;
   flag = false;
   int t, temp = address[i];
   while (stack[j] != address[i]) {
    t = stack[j];
    stack[j] = temp;
    temp = t;
    j++;
    if (temp == 0 || j == stack.length)
     break;
   }
   if (j < stack.length)
    stack[j] = temp;
   if (temp != 0 && j != stack.length)
    flag = true;
   try {
    java.lang.Thread.sleep(500);
   } catch (InterruptedException e) {
   }
   for (int m = 0; m < i - blockCount + 1; m++)
    System.out.print("  ");
   for (int m = 0; m < stack.length; m++)
    System.out.print(stack[m] + " ");
   if (flag)
    System.out.print("页面直接可用");
   System.out.print("\n");
  }
 }
 public int readInt() {
  try {
   String s = br.readLine();
   System.out.println(s);
   return Integer.parseInt(s);
  } catch (Exception e) {
   System.out.println(e);
   return 3;
  }
 }
 public int[] readIntArray() {
  try {
   String s = br.readLine();
   System.out.println(s);
   String tmp[] = s.split(" ");
   int value[] = new int[tmp.length];
   for (int i = 0; i < value.length; i++)
    value[i] = Integer.parseInt(tmp[i]);
   return value;
  } catch (Exception e) {
   System.out.println(e);
   return null;
  }
 }
}
