import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.reflect.generics.tree.Tree;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lenovo on 2017/10/25.
 */

public class RB_Tree {
    Tree_Note NIL = new Tree_Note("NIL");
    Tree_Note root =this.NIL;
    /*子树的替换*/
    public void rb_transplant(Tree_Note change, Tree_Note changeto) {
        if (change.parent == this.NIL) {
            this.root = changeto;
            this.root.parent=this.NIL;
        }
        else if(change.parent.left!=this.NIL && change.keye.equals(change.parent.left.keye))
            change.parent.left = changeto;
        else
            change.parent.right = changeto;
        changeto.parent = change.parent;
    }
/*左旋*/
    public void left_rotate(Tree_Note x) {
        Tree_Note y = x.right;
        x.right = y.left;
        if (y.left.keye != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == this.NIL) {
            this.root = y;
            this.root.parent=this.NIL;
        }
        else if(x==x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }
/*右旋*/
    public void right_rotate(Tree_Note x) {
        Tree_Note y = x.left;
        x.left = y.right;
        if(y.right.keye !=null){
            y.right.parent = x;
        }
        y.parent = x.parent;
       if (x.parent == this.NIL) {
            this.root = y;
            this.root.parent=this.NIL;
        }
        else if (x == x.parent.right)
            x.parent.right = y;
        else
            x.parent.left = y;
        y.right = x;
        x.parent = y;
    }
/*红黑树的插入操作*/
    public boolean rb_insert(Tree_Note note, String instruction) {
        Tree_Note y = this.NIL;
        Tree_Note x = this.root;
        int n=0;
        while(x.keye!=null && note.keye!=null){
            y = x;
           if ((note.keye).compareTo(x.keye) < 0) {
                x = x.left;

            }
            else if ((note.keye).compareTo(x.keye) > 0) {
                x = x.right;
            }
            else if((note.keye).compareTo((x.keye))==0) {
               break;
           }
        }
        if (y.keye != null && instruction.equals("INSERT")&& (note.keye).compareTo(y.keye) == 0 ) {
            System.out.println("Error:key conflict");
            return false;
        } else if (y.keye != null &&(note.keye).compareTo(y.keye) == 0) {
            y.descr=note.descr;
            return false;
        } else {
            note.parent = y;
            if(y==this.NIL){
                this.root = note;
                note.left = this.NIL;
                note.right = this.NIL;
                note.parent=this.NIL;
                note.color = "BLACK";
            } else {
                if ((note.keye).compareTo(y.keye) < 0) {
                    y.left = note;
                } else
                    y.right = note;
                note.left = this.NIL;
                note.right = this.NIL;
                note.color = "RED";
                this.root.parent=this.NIL;
                rb_insert_fixup(note);
            }
            return true;
        }
    }
    /*红黑树的插入的修复操作*/
    public void rb_insert_fixup(Tree_Note note) {
        while (note.parent.color.equals("RED")) {
            if(note.parent.keye.equals(note.parent.parent.left.keye)){
                Tree_Note y = note.parent.parent.right;
                if (y.color.equals("RED")) {
                    note.parent.color = "BLACK";
                    y.color = "BLACK";
                    note.parent.parent.color = "RED";
                    note = note.parent.parent;
                } else {
                    if(note==note.parent.right){
                        note = note.parent;
                        left_rotate(note);
                    }
                    note.parent.color = "BLACK";
                    note.parent.parent.color = "RED";
                    right_rotate(note.parent.parent);
                }
            } else {
                Tree_Note y = note.parent.parent.left;
                if (y.color.equals("RED")) {
                    note.parent.color = "BLACK";
                    y.color = "BLACK";
                    note.parent.parent.color = "RED";
                    note = note.parent.parent;
                } else {
                    if(note==note.parent.left){
                        note = note.parent;
                        right_rotate(note);
                    }
                    note.parent.color = "BLACK";
                    note.parent.parent.color = "RED";
                    left_rotate(note.parent.parent);
                }
            }
            this.root.color="BLACK";
            this.root.parent=this.NIL;
        }
    }
 /*寻找一个子树的最小值，主要用来查找前驱*/
    public Tree_Note tree_minmum(Tree_Note note) {
        while (note.left != this.NIL) {
            note = note.left;
        }
        return note;
    }
    /*红黑树节点的删除操作*/
    public void rb_delete(Tree_Note note) {
        Tree_Note y = note;
        Tree_Note x;
        String y_original_col = y.color;
        if (note.left == this.NIL) {
            x = note.right;
            rb_transplant(note, note.right);
        } else if (note.right == this.NIL) {
            x = note.left;
            rb_transplant(note, note.left);
        } else {
            y = tree_minmum(note.right);
            y_original_col = y.color;
            x = y.right;
            if(y.parent==note)
                x.parent = y;
            else {
                rb_transplant(y, y.right);
                y.right = note.right;
                y.right.parent = y;
            }
            rb_transplant(note, y);
            y.left = note.left;
            y.left.parent = y;
            y.color = note.color;
        }
        if (y_original_col.equals("BLACK"))
            rb_delete_fixup(x);
    }
/*红黑树删除的修复*/
    public void rb_delete_fixup(Tree_Note note) {
        while (note != this.root && note.color.equals("BLACK")) {
            if(note==note.parent.left){
                Tree_Note w = note.parent.right;
                if (w.color.equals("RED")) {
                    w.color = "BLACK";
                    note.parent.color = "RED";
                    left_rotate(note.parent);
                    w = note.parent.right;
                }
                if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK")) {
                    w.color = "RED";
                    note = note.parent;
                } else {
                    if (w.right.color.equals("BLACK")) {
                        w.left.color = "BLACK";
                        w.color = "RED";
                        right_rotate(w);
                        w = note.parent.right;
                    }
                    w.color = note.parent.color;
                    note.parent.color = "BLACK";
                    w.right.color="BLACK";
                    left_rotate(note.parent);
                    note = this.root;
                    this.root.parent=this.NIL;
                }
            }
            else if(note==note.parent.right){
                Tree_Note w = note.parent.left;
                if (w.color.equals("RED")) {
                    w.color = "BLACK";
                    note.parent.color = "RED";
                    right_rotate(w.parent);
                    w = note.parent.left;
                }
                if (w.right.color.equals("BLACK") && w.left.color.equals("BLACK")) {
                    w.color = "RED";
                    note = note.parent;
                } else {
                    if ( w.left.color.equals("BLACK")) {
                        w.right.color = "BLACK";
                        w.color = "RED";
                        left_rotate(w);
                        w = note.parent.left;
                    }
                    w.color = note.parent.color;
                    note.parent.color = "BLACK";
                    w.left.color="BLACK";
                    right_rotate(note.parent);
                    note = this.root;
                    this.root.parent=this.NIL;
                }
            }
        }
        note.color="BLACK";
    }
/*查找节点*/
    public Tree_Note rb_search(String keyStr) {
        Tree_Note x = this.root;
        int  isformer;
        while (x != this.NIL) {
            isformer=keyStr.compareTo(x.keye);
            if (isformer<0)
                x = x.left;
            else if(isformer==0)
                return x;
            else
                x = x.right;
        }
            return this.NIL;
    }
    /*输出整个树的信息*/
    public void rb_print(Tree_Note note) {
        if(note!=this.NIL) {
            rb_print(note.left);
            System.out.println(note.keye+"-"+note.descr);
            rb_print(note.right);
        }
    }
    /*得到整个树的信息*/
    public String rb_getString(Tree_Note note,String s){
        if(note!=this.NIL){
                s = rb_getString(note.left,s)+note.keye+ "\n"+note.descr +"\n"+rb_getString(note.right ,s);
        }
        return s;
    }
}
