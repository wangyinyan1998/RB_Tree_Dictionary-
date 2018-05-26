import java.io.*;
import java.util.Scanner;

/**
 * Created by lenovo on 2017/10/27.
 */
public class CreateDictionary {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RB_Tree tree=new RB_Tree();
        System.out.println("You can use 'INSERT word meaning' to insert words to dictionary," +"\n" +
                "You can use 'PUT word meaning' to put words to dictionary," + "\n" +
                "You can use 'DEL word' to delete words to dictionary," + "\n" +
                "You can use 'GET word' to get words' meanings in the dictionary," + "\n" +
                "You can use 'DUMP' to get all information in the dictionary" + "\n" +
                "You can use 'quit' to quit the project!");
        /*文件的读取信息，并将信息存储到树中操作*/
        try {
            File file = new File("windows.txt");
            FileReader fileReader = new FileReader(file.getName());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String english;
            String descr;
            while((english=bufferedReader.readLine())!=null){
                ;if(!((english.charAt(0)>='a'&& english.charAt(0)<='z')||
                        (english.charAt(0)>='A' && english.charAt(0)<='Z')||(english.charAt(0)=='é'))){
                    english=english.substring(1);
                }
                descr=bufferedReader.readLine();
                Tree_Note note=new Tree_Note(english,descr);
               tree.rb_insert(note,"rb_insert");

            }
            bufferedReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String key;
        String value;
        /*读取键盘输入的指令，进行各种操作*/
            while (true) {
               String instruction= input.next();
                if (instruction.equals("INSERT")) {
                    key = input.next();
                    value = input.next();
                 control_insert(key,value,tree);
                }
                else if (instruction.equals("PUT")) {
                    key = input.next();
                    value = input.next();
                    control_put(key,value,tree);
                }
                else if(instruction.equals("DEL")){
                    key=input.next();
                  control_del(key,tree);
                }
                else if(instruction.equals("DUMP")){
                    tree.rb_print(tree.root);
                }
                else if(instruction.equals("LOAD")){
                    String fileName=input.next();
                    control_load(fileName,tree);
                }
                else if(instruction.equals("GET")){
                    key=input.next();
                    control_get(key,tree);
                }
                else if(instruction.equals("quit")) {
                    System.exit(0);
                }
        }
    }
    /*控制插入操作*/
    public static void control_insert(String key,String value,RB_Tree tree){
        try{
            File file= new File("windows.txt");
            FileWriter filewriter = new FileWriter(file.getName(),true);
            BufferedWriter bufferedWriter=new BufferedWriter(filewriter);
            Tree_Note note = new Tree_Note(key, value);
            Boolean isInserted= tree.rb_insert(note, "INSERT");
            if(isInserted){
                bufferedWriter.write(key+"\n"+value+ "\n");
                System.out.println("YES!You have inserted!");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void control_put(String key,String value,RB_Tree tree){
        try {
            File file = new File("windows.txt");
            FileWriter filewriter = new FileWriter(file.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            Tree_Note note = new Tree_Note(key, value);
            Boolean isPUT = tree.rb_insert(note, "PUT");
            bufferedWriter.write( key + "\n" + value+ "\n");
            System.out.println("YES!You have put!");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /*控制查找工作*/
    public static void control_get(String key,RB_Tree tree){
        Tree_Note note =tree.rb_search(key);
        if(note!=tree.NIL){
            System.out.println(note.descr);
        }
        else
            System.out.println("Error:key missing.");
    }
    /*控制删除操作*/
    public static void control_del(String key,RB_Tree tree){
        try {
            Tree_Note note = tree.rb_search(key);
            if (note == tree.NIL)
                System.out.println("Error:key missing");
            else {
                tree.rb_delete(note);
                String s="";
                String totalInfor = "";
                File file = new File("windows.txt");
              totalInfor=tree.rb_getString(tree.root,s);
                FileWriter filewriter = new FileWriter(file.getName(), false);
                BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
                bufferedWriter.write(totalInfor);
                bufferedWriter.flush();
                bufferedWriter.close();
                System.out.println("YES!You have deleted successfully!");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /*控制读取文本文件指令的操作*/
    public static void control_load(String fileName,RB_Tree tree) {
        File file3 = new File(fileName);
        if (!file3.exists())
            System.out.println("NO such a file");
        else {
            try {
                FileReader fileReader3 = new FileReader(file3.getName());
                BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
                String consoleInput;

                while ((consoleInput = bufferedReader3.readLine()) != null) {
                    ;if(!((consoleInput.charAt(0)>'a'&&consoleInput.charAt(0)<'z')||
                            (consoleInput.charAt(0)>'A' && consoleInput.charAt(0)<'Z')||(consoleInput.charAt(0)=='é'))){
                        consoleInput=consoleInput.substring(1);
                    }
                    String[] instr = consoleInput.split(" ");
                    if (instr[0].equals("INSERT")) {
                        if(instr.length==3)
                           control_insert(instr[1], instr[2], tree);
                    } else if (instr[0].equals("PUT")) {
                        if(instr.length==3)
                          control_put(instr[1], instr[2], tree);
                    } else if (instr[0].equals("DUMP")) {
                        tree.rb_print(tree.root);
                    } else if (instr[0].equals("quit")) {
                        System.exit(0);
                    } else if (instr[0].equals("GET")) {
                        if(instr.length==2)
                           control_get(instr[1], tree);
                    } else if (instr[0].equals("LOAD") && instr.length == 2) {
                        control_load(instr[1], tree);
                    }
                }
                bufferedReader3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
