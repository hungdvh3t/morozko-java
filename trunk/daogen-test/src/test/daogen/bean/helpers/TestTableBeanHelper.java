/*
 * @(#)TestTableBeanHelper.java
 *
 * @project    : Daogen Test
 * @package    : test.daogen.bean.helpers
 * @creation   : 18/05/2011 09/52/37
 */
package test.daogen.bean.helpers;

import test.daogen.model.TestTableModel;

/**
 * <p>Bean per oggetti di tipo TestTableModel.</p>
 *
 * @author Matteo a.k.a. Fugerit
 */
public class TestTableBeanHelper extends org.morozko.java.mod.daogen.helpers.bean.SimpleBasicBean {

	private final static long serialVersionUID = 130570515772351L;

	private String testId;

    /** 
     * <p>Restituisce il valore di testId</p> 
     * 
     * @return      restituisce il valore di testId
     */ 
    public String getTestId() {
        return this.testId;
    }
    /** 
     * <p>Imposta il valore di testId</p> 
     * 
     * @param      testId il valore di testId da impostare
     */ 
    public void setTestId( String testId ) {
        this.testId = testId;
    }
	private String testInt;

    /** 
     * <p>Restituisce il valore di testInt</p> 
     * 
     * @return      restituisce il valore di testInt
     */ 
    public String getTestInt() {
        return this.testInt;
    }
    /** 
     * <p>Imposta il valore di testInt</p> 
     * 
     * @param      testInt il valore di testInt da impostare
     */ 
    public void setTestInt( String testInt ) {
        this.testInt = testInt;
    }
	private String testDate;

    /** 
     * <p>Restituisce il valore di testDate</p> 
     * 
     * @return      restituisce il valore di testDate
     */ 
    public String getTestDate() {
        return this.testDate;
    }
    /** 
     * <p>Imposta il valore di testDate</p> 
     * 
     * @param      testDate il valore di testDate da impostare
     */ 
    public void setTestDate( String testDate ) {
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
	private String testDecimal;

    /** 
     * <p>Restituisce il valore di testDecimal</p> 
     * 
     * @return      restituisce il valore di testDecimal
     */ 
    public String getTestDecimal() {
        return this.testDecimal;
    }
    /** 
     * <p>Imposta il valore di testDecimal</p> 
     * 
     * @param      testDecimal il valore di testDecimal da impostare
     */ 
    public void setTestDecimal( String testDecimal ) {
        this.testDecimal = testDecimal;
    }
    // alias della tabellea - START 
    // alias della tabellea - END 
    public TestTableModel getModel() {
        TestTableModel model = new TestTableModel();
        model.setTestId( toDAOID(testId) );
        model.setTestInt( toInteger(testInt) );
        model.setTestDate( toDate(testDate) );
        model.setTestString( toString(testString) );
        model.setTestDecimal( toBigDecimal(testDecimal) );
        return model;
    }

}
