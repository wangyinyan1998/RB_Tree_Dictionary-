import java.util.GregorianCalendar;

/**
 * Created by lenovo on 2017/10/25.
 */
/*红黑树的节点类*/
public class Tree_Note {
    Tree_Note parent;
    Tree_Note left;
    Tree_Note right;
    String color;
    String keye;
    String descr;
    Tree_Note(String type){
        color="BLACK";
        descr=null;
        keye=null;
    }
    Tree_Note(String e,String d){
        keye=e;
        descr=d;
    }

}

