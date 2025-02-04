package com.dbs.bgcp.data;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_BGCP_COL_CONFIG")
public class TBgcpColConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "App_Code", nullable = false)
    private String App_Code;

    @Column(name = "Target_Table", nullable = false)
    private String Target_Table;

    @Column(name = "Target_Attribute", nullable = false)
    private String Target_Attribute;

    @Column(name = "Source_Table")
    private String Source_Table;

    @Column(name = "Direct_Or_Derived")
    private String Direct_Or_Derived;

    @Column(name = "Transformation_Logic")
    private String Transformation_Logic;

    @Column(name = "Source_Attribute")
    private String Source_Attribute;

    @Column(name = "Start_Position")
    private String Start_Position;

    @Column(name = "End_Position")
    private String End_Position;

    @Column(name = "Unique_Key_Column")
    private String Unique_Key_Column;
    
    @Column(name = "Data_Validation")
    private String Data_Validation;
    
    @Column(name = "Is_Active")
    private String Is_Active;
    
    @Column(name = "is_Sum_Amt_Col")
    private String is_Sum_Amt_Col;
    
    // Getters and Setters
    private String colValue;
    private Boolean hasError=false;
    private String errorMessage;

	public String getApp_Code() {
		return App_Code;
	}
	public void setApp_Code(String app_Code) {
		App_Code = app_Code;
	}
	public String getTarget_Table() {
		return Target_Table;
	}
	public void setTarget_Table(String target_Table) {
		Target_Table = target_Table;
	}
	
	public String getTarget_Attribute() {
		return Target_Attribute;
	}
	public void setTarget_Attribute(String target_Attribute) {
		Target_Attribute = target_Attribute;
	}
	public String getSource_Table() {
		return Source_Table;
	}
	public void setSource_Table(String source_Table) {
		Source_Table = source_Table;
	}
	public String getDirect_Or_Derived() {
		return Direct_Or_Derived;
	}
	public void setDirect_Or_Derived(String direct_Or_Derived) {
		Direct_Or_Derived = direct_Or_Derived;
	}
	public String getTransformation_Logic() {
		return Transformation_Logic;
	}
	public void setTransformation_Logic(String transformation_Logic) {
		Transformation_Logic = transformation_Logic;
	}
	public String getSource_Attribute() {
		return Source_Attribute;
	}
	public void setSource_Attribute(String source_Attribute) {
		Source_Attribute = source_Attribute;
	}
	public String getStart_Position() {
		return Start_Position;
	}
	public void setStart_Position(String start_Position) {
		Start_Position = start_Position;
	}
	public String getEnd_Position() {
		return End_Position;
	}
	public void setEnd_Position(String end_Position) {
		End_Position = end_Position;
	}
	public String getUnique_Key_Column() {
		return Unique_Key_Column;
	}
	public void setUnique_Key_Column(String unique_Key_Column) {
		Unique_Key_Column = unique_Key_Column;
	}
	public String getData_Validation() {
		return Data_Validation;
	}
	public void setData_Validation(String data_Validation) {
		Data_Validation = data_Validation;
	}
	public String getIs_Active() {
		return Is_Active;
	}
	public void setIs_Active(String is_Active) {
		Is_Active = is_Active;
	}
	public String getIs_Sum_Amt_Col() {
		return is_Sum_Amt_Col;
	}
	public void setIs_Sum_Amt_Col(String is_Sum_Amt_Col) {
		this.is_Sum_Amt_Col = is_Sum_Amt_Col;
	}
	public String getColValue() {
		return colValue;
	}
	public void setColValue(String colValue) {
		this.colValue = colValue;
	}
	public Boolean getHasError() {
		return hasError;
	}
	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
}
