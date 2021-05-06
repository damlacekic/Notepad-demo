/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

/**
 *
 * @author damla
 */
public class ArrayToStringExpression implements Expression{
    private String[] i;

    public ArrayToStringExpression(String[] i) {
        this.i = i;
    }

    @Override
    public String Interpreter(InterPreterContext interPreterContext) {
       return interPreterContext.getArrayToStringFormat(i);
    }
}
