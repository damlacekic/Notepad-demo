/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author damla
 */
public class ReadingWord {
    private ArrayList<String> wordList = new ArrayList<String>();
    private int wordListBoyut;
    private Builder builder;

    public Builder getBuilder() {                   /////////////Olusturdugumuz builderin dısari çikmasını saglıcak getter metodu
        return builder;
    }                                           ///////////////////////////
 
  

    public ReadingWord() {                            //////////////////Default Constructor
    }            ////////////////////////////////////////////////////////////

  
  
    

    public void takewords() {       /////////////////////////////////////////////////////////////////////////////////////
        try {
            BufferedReader oku = new BufferedReader(new FileReader("word.txt"));
           String word;
            while(true){
                
                try {
               word = oku.readLine();
               if(word == null){
                    break;
                }
                  else {
                      wordList.add(word);
                  }
          
                } catch (IOException ex) {
                    System.out.println("readLine Hatası"); ////////////////////////////////////////////////////////////////////////////  Word.txt den okuma yapıp bilgileri ArrayList te attıktan sonra ArrayListin içerigini ve uzunlugunu builderla nesnelestiren metod
                }
               
   
            }
           
            wordListBoyut = wordList.size();
            
                    
            oku.close();
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya bulunamadı");
        } catch (IOException ex) {
            System.out.println("Dosya kapanamadı");
        }
        
      builder = new Builder.InnerBuilder(wordList,wordListBoyut).build();
       
    }                                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
     
     
    
    
    
}
