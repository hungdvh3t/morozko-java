/*
 * @(#)TestTableModelHelper.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.model.helpers
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.model.helpers;

import test.daogen.bean.TestTableBean;

/**
 * <p>Oggetto di modello per TestTable.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestTableModelHelper extends org.morozko.java.mod.daogen.helpers.model.BasicModel {

	private final static long serialVersionUID = 130570515769191L;


	public static final String ATT_NAME = "testTableModel";

    // campi relativi alla tabella - START 

	private org.morozko.java.mod.db.dao.DAOID testId;

    /** 
     * <p>Restituisce il valore di testId</p> 
     * 
     * @return      restituisce il valore di testId
     */ 
    public org.morozko.java.mod.db.dao.DAOID getTestId() {
        return this.testId;
    }
    /** 
     * <p>Imposta il valore di testId</p> 
     * 
     * @param      testId il valore di testId da impostare
     */ 
    public void setTestId( org.morozko.java.mod.db.dao.DAOID testId ) {
        this.testId = testId;
    }

	private Integer testInt;

    /** 
     * <p>Restituisce il valore di testInt</p> 
     * 
     * @return      restituisce il valore di testInt
     */ 
    public Integer getTestInt() {
        return this.testInt;
    }
    /** 
     * <p>Imposta il valore di testInt</p> 
     * 
     * @param      testInt il valore di testInt da impostare
     */ 
    public void setTestInt( Integer testInt ) {
        this.testInt = testInt;
    }

	private java.sql.Date testDate;

    /** 
     * <p>Restituisce il valore di testDate</p> 
     * 
     * @return      restituisce il valore di testDate
     */ 
    public java.sql.Date getTestDate() {
        return this.testDate;
    }
    /** 
     * <p>Imposta il valore di testDate</p> 
     * 
     * @param      testDate il valore di testDate da impostare
     */ 
    public void setTestDate( java.sql.Date testDate ) {
        this.testDate = testDate;
    }

	private String testString;

    /** 
     * <p>Restituisce il valore di testString</p> 
     * 
     * @return      restituisce il valore di testString
     */ 
    public String getTestString() {
        return this.testString;
    }
    /** 
     * <p>Imposta il valore di testString</p> 
     * 
     * @param      testString il valore di testString da impostare
     */ 
    public void setTestString( String testString ) {
        this.testString = testString;
    }

	private java.math.BigDecimal testDecimal;

    /** 
     * <p>Restituisce il valore di testDecimal</p> 
     * 
     * @return      restituisce il valore di testDecimal
     */ 
    public java.math.BigDecimal getTestDecimal() {
        return this.testDecimal;
    }
    /** 
     * <p>Imposta il valore di testDecimal</p> 
     * 
     * @param      testDecimal il valore di testDecimal da impostare
     */ 
    public void setTestDecimal( java.math.BigDecimal testDecimal ) {
        this.testDecimal = testDecimal;
    }
    // campi relativi alla tabella - END 

    // alias della tabellea - START 
    // alias della tabellea - END 
    public TestTableBean getBean() {
        TestTableBean bean = new TestTableBean();
        bean.setTestId( formatObject(testId) );
        bean.setTestInt( formatObject(testInt) );
        bean.setTestDate( formatObject(testDate) );
        bean.setTestString( formatObject(testString) );
        bean.setTestDecimal( formatObject(testDecimal) );
        return bean;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append( this.getClass().getName() );
        buffer.append( "[ " );
        buffer.append( "testId=" );
        buffer.append( this.testId );
        buffer.append( "; " );
        buffer.append( "testInt=" );
        buffer.append( this.testInt );
        buffer.append( "; " );
        buffer.append( "testDate=" );
        buffer.append( this.testDate );
        buffer.append( "; " );
        buffer.append( "testString=" );
        buffer.append( this.testString );
        buffer.append( "; " );
        buffer.append( "testDecimal=" );
        buffer.append( this.testDecimal );
        buffer.append( "; " );
        buffer.append( "]" );
        return buffer.toString();
    }

}
