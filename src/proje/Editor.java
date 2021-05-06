/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.applet.Applet;
import java.awt.FlowLayout;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author damla
 */
public class Editor {

    public JTextField textField;
    public JTextField aranacakKelime;
    public JTextField degistirilecekKelime;
    public String clipboard;
    private String text;

    public InterPreterContext interPreterContext;

    public String interpret(JTextField jTextField) {   ////////////////////////////////////////////////
        Expression expression = null;
        expression = new TextToStringExpression(jTextField); //////////////////////////////////////// Textfield dan String e çevirim islemleri için kullanılıcak interpretter pattern çagırım metodu
        return expression.Interpreter(interPreterContext); 
    }                                                                                 ///////////////////////////////////////////////////////////
     public String interpret(String[] array) {               /////////////////////////////////////////////////////////
        Expression expression = null;
        expression = new ArrayToStringExpression(array); //////////////////////////////////////////////////////Array den String e çevirim için kullanılacak interpretter pattern çagırım metodu
        return expression.Interpreter(interPreterContext);
    }         //////////////////////////////////////////////////////////

    public Editor() { //////////////////////////////////// default constructor
    } ////////////////////////////////////////////////////////

    public JTextField getTextField() {///////////////////JText Field ı dısarı cıkarmak icin gerekli getter metodu
        return textField;
    }     ////////////////////////////////////////////////////////////////

    private Stack<Command> Undocommands = new Stack<Command>(); ////////////////////////Undo command lerinin toplandıgı Stack

    public Editor(InterPreterContext interPreterContext) {  ///////////////////////Constructor
        this.interPreterContext = interPreterContext;
    }    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void init() { //////////////Ara yüzü saglıyacak metod
        JFrame frame = new JFrame("Text Editor");
        JPanel content = new JPanel();
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        textField = new JTextField("Enter the Text: ");
        aranacakKelime = new JTextField("Enter the word you want to change: ");
        degistirilecekKelime = new JTextField("Enter the word to replace the word you want to change: ");
        
       
        content.add(textField);
         content.add(aranacakKelime);
         content.add(degistirilecekKelime);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton duzelt = new JButton("Correction ");
        JButton geriAl = new JButton("Undo");
        JButton Kaydet = new JButton("Save");
        JButton Degistir = new JButton("Change");

 
        Editor editor = this;

       
        clipboard = textField.getSelectedText();
        duzelt.addActionListener(new ActionListener() {     //////////////////////////////////////////////////////////////
            @Override
            public void actionPerformed(ActionEvent e) { //////////////////////////////////////////////////////////////////
                Command command = null;
                Editor interPreter = new Editor(new InterPreterContext());
                text = (interPreter.interpret(textField));                       ///////////////////////////////////////////////////////// Girilen Metindeki yanlısları degistirmeyi saglayan butonun aksiyon metodu
                GirilenMetin girilenMetin1 = new GirilenMetin();
                girilenMetin1.readingfromtext(text);
                textField.setText(girilenMetin1.getMetinString());
             

            }
        });                         ///////////////////////////////////////////////////////////////////////////////////////////////////////

        geriAl.addActionListener(new ActionListener() {    //////////////////////////////////////////////////////
            @Override
            public void actionPerformed(ActionEvent e) {
                Command command = null;

                command = new UndoCommand(editor, textField);         ///////////////////////////// Geri alma islemini saglayan butonun aksiyon butonu
                if (command != null) {
                    editor.doCommand(command);
                }
                undoCommand();

            }
        });                          //////////////////////////////////////////////////////////////////////////////////////////////////
        Degistir.addActionListener(new ActionListener() {      ////////////////////////////////////////////////////////////////
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor interPreter = new Editor(new InterPreterContext());
                String  araText = (interPreter.interpret(textField));
                String[] araArray = araText.split(" ");
                ArrayList<String> araArrayList = new ArrayList<>(Arrays.asList(araArray));
                 int kelimeNerede = 0;                                                                             /////////////////////////////// Girilen Kelimenin Metindeki halini degistirme Metodu
                String aranacakKelimeString =  (interPreter.interpret(aranacakKelime));
                 if(araArrayList.contains(aranacakKelimeString)){
                     kelimeNerede = araArrayList.indexOf(aranacakKelimeString);
                 }
                 
                String stringDegistirilecekKelime = (interPreter.interpret(degistirilecekKelime));
                 araArray[kelimeNerede] = stringDegistirilecekKelime;
                String enSonMetin = interPreter.interpret(araArray);
                textField.setText(enSonMetin);
                
                
            }
        });   ///////////////////////////////////////////////////////////////////////////////////////////////////////
        Kaydet.addActionListener(new ActionListener() {   /////////////////////////////////////////////
            @Override
            public void actionPerformed(ActionEvent e) {
             BufferedWriter buffWriter = null;
             try{
                 buffWriter = new BufferedWriter(new FileWriter(new File("Metin.txt")));
                 Editor interPreter = new Editor(new InterPreterContext());
                String  writeText = (interPreter.interpret(textField));
                 buffWriter.write(writeText);
                                                                                                                                        //////////////////////////////////Kullanıcı tarafından olusturulan Metini programla aynı klasörde olusturulacak Metin.txt dosyasına kaydetme buton aksiyon metodu
             }
             catch(IOException exception){
                 System.out.println("I/O Error");
             }
             if(buffWriter!=null){
                 try {
                     buffWriter.close();
                 } catch (IOException ex) {
                     Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
       
            }
        });                             ///////////////////////////////////////////////////////////////////////////////
        buttons.add(Degistir);
        buttons.add(duzelt);
        buttons.add(geriAl);
        buttons.add(Kaydet);  
        content.add(buttons);
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void doCommand(Command command) {   ///////////////////////////////////////////
        if (command != null) {
            Undocommands.push(command);   ///////////////////////////////////////////////Eger text imizin ancak icerisine bir sey girilmis ise undo butonu calısıcagından text dolu ise stack in bos kalmamasını saglayan metod
            command.execute();
        }
    }  ////////////////////////////////////////////////////////////////////////
    public void undoCommand() {   //////////////////////////////////////////////////////////////
        if (!Undocommands.empty()) {
            Object top = Undocommands.peek();

            if (top instanceof UndoCommand) {
                Undocommands.pop();        ///////////////////////////////////////////////////////////////  Undo islemini command pattern i yardımı ile gerceklestiemeyi saglayan metod
                UndoCommand undoCommand2 = (UndoCommand) top;
                undoCommand2.undo();
            }
        }
    }                     /////////////////////////////////////////////////////////////////////////////////

}
