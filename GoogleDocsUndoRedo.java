package practice;

import java.util.*;

public class GoogleDocsUndoRedo {
    private StringBuilder document;              
    private Deque<Action> undoStack;          
    private Deque<Action> redoStack;        
    private int totalOperations;               
  
    private static class Action {
        String type;      
        String text;      
        int position;     
        int length;     

        Action(String type, int pos, String text, int len) {
            this.type = type;
            this.position = pos;
            this.text = text;
            this.length = len;
        }
    }

    public GoogleDocsUndoRedo() {
        this.document = new StringBuilder();
        this.undoStack = new ArrayDeque<>(51);   
        this.redoStack = new ArrayDeque<>();
        this.totalOperations = 0;
    }

    public void insert(int position, String text) {
        if (position < 0 || position > document.length()) {
            throw new IllegalArgumentException("Invalid position");
        }

        
        document.insert(position, text);

        pushUndo(new Action("INSERT", position, text, text.length()));

        totalOperations++;
    }

 
    public void delete(int position, int k) {
        if (position < 0 || position >= document.length() || k <= 0) {
            return; 
        }

        int end = Math.min(position + k, document.length());
        String removed = document.substring(position, end);

        document.delete(position, end);

      
        pushUndo(new Action("DELETE", position, removed, removed.length()));

        totalOperations++;
    }

    public void undo() {
        if (undoStack.isEmpty()) return;

        Action action = undoStack.pop();
        redoStack.push(action);

        if (action.type.equals("INSERT")) {
            document.delete(action.position, action.position + action.text.length());
        } else if (action.type.equals("DELETE")) {
            document.insert(action.position, action.text);
        }
    }

    public void redo() {
        if (redoStack.isEmpty()) return;

        Action action = redoStack.pop();
        undoStack.push(action); 

        if (action.type.equals("INSERT")) {
            document.insert(action.position, action.text);
        } else if (action.type.equals("DELETE")) {
            document.delete(action.position, action.position + action.text.length());
        }
    }


    private void pushUndo(Action action) {
        undoStack.push(action);
        if (undoStack.size() > 50) {
            undoStack.removeLast(); 
        }
        redoStack.clear(); 
    }

    
    public String getDocument() {
        return document.toString();
    }

    public int getTotalOperations() {
        return totalOperations;
    }

  
    public static void main(String[] args) {
        GoogleDocsUndoRedo doc = new GoogleDocsUndoRedo();

        doc.insert(0, "Hello");
        doc.insert(5, " World!");
        doc.delete(5, 1); 
        doc.insert(5, "!");
        System.out.println(doc.getDocument()); 
        System.out.println("Total ops: " + doc.getTotalOperations());

        doc.undo(); 
        doc.undo(); 
        doc.undo(); 
        System.out.println("After undo: " + doc.getDocument()); 

        doc.redo(); 
        System.out.println("After redo: " + doc.getDocument());
    }
}