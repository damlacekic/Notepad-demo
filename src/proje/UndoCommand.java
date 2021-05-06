/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import javax.swing.JTextField;

/**
 *
 * @author damla
 */
public class UndoCommand implements UndoableCommand{
    private Editor editor;
    private  JTextField jTextField;

    UndoCommand(Editor editor, JTextField jTextField) {
        this.editor = editor;
        this.jTextField = jTextField;
    }    
    @Override
    public void undo() {
        jTextField.setText(jTextField.getText().substring(0, jTextField.getText().length()-1));
    }

    @Override
    public void execute() {
          jTextField = editor.getTextField();
    }
    
}
