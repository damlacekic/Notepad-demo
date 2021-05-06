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
public class InterPreterContext {
    public String getStringFormat(JTextField jTextField){
        return jTextField.getText().toString();
    }
    public String getArrayToStringFormat(String[] array){
        StringBuffer stringBuffer = new StringBuffer();
        for(int a = 0; a<array.length ; a++){
            stringBuffer.append(array[a]);
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
}
