
/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */

public class TaxNode implements Comparable<TaxNode>{
    
    private int start_income;
    private int end_income;
    private int base_tax;
    private int tax_per_dollar;

    public TaxNode(){
    }

    public TaxNode(Integer start_income, Integer end_income, Integer base_tax, Integer tax_per_dollar){
        
        this.start_income = start_income;       //initial start income of this range
        this.end_income = end_income;           // initial end income of this range
        this.base_tax = base_tax;               // initial base tax of this range
        this.tax_per_dollar = tax_per_dollar;   // initial tax per dollar of this range
    }
    
    
    // getters and setters
    // -----------------------------------------------------------------
    public void setStart_income(Integer setStart_income){
        this.start_income = setStart_income;
    }
    public Integer getStart_income(){
        return this.start_income;
    }
    //-----------------------------------------------------------------
    public void setEnd_income(Integer setEnd_income) {
        this.end_income = setEnd_income;
    }

    public Integer getEnd_income() {
        return this.end_income;
    }

    // -----------------------------------------------------------------
    public void setBase_tax(Integer setBase_tax) {
        this.base_tax = setBase_tax;
    }

    public Integer getBase_tax() {
        return this.base_tax;
    }

    // -----------------------------------------------------------------
    public void setTax_per_dollar(Integer setTax_per_dollar) {
        this.tax_per_dollar = setTax_per_dollar;
    }

    public Integer getTax_per_dollar() {
        return this.tax_per_dollar;
    }
    // -----------------------------------------------------------------


    // need implement !!!!!!
    // standard Comparable<T> interface
    public int compareTo(TaxNode obj) {


        // when new TaxNode's start——income fall in between current's start_income and end_income
        if ((obj.start_income > this.end_income) && (this.end_income != -1) && (obj.end_income != -1)) {

            return 1;

        }else if((this.end_income!= -1)&&(obj.end_income != -1)&& (this.start_income <obj.start_income) && (obj.start_income <= this.end_income)){

            return -1;

        }else if((obj.start_income == this.start_income) && (this.end_income != -1) && (obj.end_income != -1)){

            return 2;
        }        
        else 
            return 0;

    }


}
