/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import com.sun.scenario.effect.Merge;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import javax.management.openmbean.TabularType;
import javax.swing.JOptionPane;

/**
 *
 * @author damla
 */
public class GirilenMetin extends ReadingWord {

    private String alinanText;
    private String MetinString;
    private Component Editor;
    public InterPreterContext interPreterContext;

    public String interpret(String[] array) {                                                          /////////
        Expression expression = null;                                                                     ////////
          expression = new ArrayToStringExpression(array);                               //Interpreter ı kullanmamı saglıcak metod
        return expression.Interpreter(interPreterContext);                                 /////////////////////
    }                                                                                                                       ///////

    public GirilenMetin(InterPreterContext interPreterContext) {                    ///////Constructror
        this.interPreterContext = interPreterContext;                                          ////////////
       }

    public String getMetinString() {                                                                  ///////
            return MetinString;                                                                             ////MetinString için getter
    }                                                                                                                        ///////////
                                                                                                          

 

    public GirilenMetin() {                                                                         /////Default Constructor
    }                                                                                                              ////////

    public String[] readingFromArrayList() {                                                ////////////////////
        takewords();                                                                                            
        String[] wordArray = new String[getBuilder().getLenght()];              
        Iterator<String> it = getBuilder().getArrayList().iterator();                //Word Listesini ReadingWord sınıfından alıp  array e çevirimi saglayan metod 
        int i = 0;                                                                                                  
        while (it.hasNext()) {
            wordArray[i] = it.next();   
            i++;
        }
        return wordArray;

    }                                                                                               /////////////////////////////////

    public String takeChars(String kelimeYanlıs) {                                                                                                  ///////////
        String[] takeWordArrayForChar = readingFromArrayList();
        ArrayList<String> takeWordArrayList = new ArrayList<>(Arrays.asList(takeWordArrayForChar));
        for (int i = 0; i < kelimeYanlıs.length(); i++) {
            char ch[] = kelimeYanlıs.toCharArray();
            if (i != kelimeYanlıs.length() - 1) {
                char temp = ch[i];
                ch[i] = ch[i + 1];                                                                                                                                  //////Okunan Text teki Metindeki yanllıs kelimeleri düzelten metod
                ch[i + 1] = temp;
                String sonString = String.valueOf(ch);
                for (int a = 0; a < takeWordArrayForChar.length; a++) {
                    if (sonString.equals(takeWordArrayForChar[a])) {
                        kelimeYanlıs = takeWordArrayForChar[a];
                    }

                }
            }
        }
        if (!takeWordArrayList.contains(kelimeYanlıs)) {
            if ((!kelimeYanlıs.matches("\\d+")) || (kelimeYanlıs != null)) {
                JOptionPane.showMessageDialog(Editor, "Hatalı giris " + kelimeYanlıs);
            }
        }
        return kelimeYanlıs;
    }                                                                                                                                                   //////////////////////////////////////////

    public void readingfromtext(String textString) {                                                                    /////////////////////////////////////////////////
        alinanText = textString;
        takewords();
        ArrayList<String> arrayListWord = new ArrayList<>();
        arrayListWord = getBuilder().getArrayList();
        int sayi = arrayListWord.size();
        String[] MetinArray = alinanText.split(" ");

        for (int i = 0; i < MetinArray.length; i++) {
            StringBuilder kelimeYanlisString = new StringBuilder(MetinArray[i]);
            int charNokta = 0;
            int charVirgul = 0;
            int charNoktaliVirgul = 0;
            for (int b = 0; b < MetinArray[i].length(); b++) {
                char chvirgul = MetinArray[i].charAt(b);
                if (chvirgul == ',') {
                    charVirgul = b;
                }
            }
            if (MetinArray[i].contains(",")) {
                MetinArray[i] = kelimeYanlisString.deleteCharAt(charVirgul).toString();                                                 /// Editör arayüzünden okuma yapıp yazılan yanlısları bulan ve düzeltilmis halini editore geri gönderen metod

            }
            for (int k = 0; k < MetinArray[i].length(); k++) {
                char chNokta = MetinArray[i].charAt(k);
                if (chNokta == '.') {
                    charNokta = k;
                }
            }
            if (MetinArray[i].contains(".")) {
                MetinArray[i] = kelimeYanlisString.deleteCharAt(charNokta).toString();

            }
            for (int n = 0; n < MetinArray[i].length(); n++) {
                char chNoktaliVirgul = MetinArray[i].charAt(n);
                if (chNoktaliVirgul == ';') {
                    charNoktaliVirgul = n;
                }
            }
            if (MetinArray[i].contains(";")) {
                MetinArray[i] = kelimeYanlisString.deleteCharAt(charNoktaliVirgul).toString();
            }
            if (!arrayListWord.contains(MetinArray[i])) {
                MetinArray[i] = takeChars(MetinArray[i]);
            }
            if (charNokta != 0) {
                MetinArray[i] = MetinArray[i] + ".";
            }
            if (charVirgul != 0) {
                MetinArray[i] = MetinArray[i] + ",";
            }
            if (charNoktaliVirgul != 0) {
                MetinArray[i] = MetinArray[i] + ";";
            }
        }
        GirilenMetin interpreter = new GirilenMetin(new InterPreterContext());
        MetinString = interpreter.interpret(MetinArray);

    }                                                                                                                                                            ////////////////////////////////////////////////////////////////

}
