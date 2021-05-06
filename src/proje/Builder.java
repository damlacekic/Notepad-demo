/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.util.ArrayList;

/**

 * @author damla
 */
public interface Builder {
    ArrayList<String> getArrayList();
    int getLenght();
    public class DefaultBuilder implements  Builder{
    private final ArrayList<String> ArrayList;
    private final  int Lenght;

    public DefaultBuilder(ArrayList<String> ArrayList,int Lenght) {
        this.ArrayList = ArrayList;
        this.Lenght = Lenght;
        
    }
  protected DefaultBuilder(InnerBuilder innerBuilder){
      this(innerBuilder.getArrayList(),innerBuilder.getLenght());
      
      
  }
    
    
    
    @Override
    public ArrayList<String> getArrayList() {
        return  this.ArrayList;
    }

    @Override
    public int getLenght() {
       return this.Lenght;
    }

        
    
}
class InnerBuilder extends DefaultBuilder{
    
    public InnerBuilder(ArrayList<String> ArrayList,int Lenght) {
        super(ArrayList,Lenght);
    }
 
    public DefaultBuilder build(){
        return new DefaultBuilder(this);
    }
}

}
