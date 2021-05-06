/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author damla
 */
public class TextToStringExpression implements  Expression{
    private JTextField i ;

    public TextToStringExpression(JTextField i) {
        this.i = i;
    }
  
    @Override
    public String Interpreter(InterPreterContext interPreterContext) {
           return  interPreterContext.getStringFormat(i);
    }
    
   
    
}
