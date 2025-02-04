package com.dbs.bgcp.data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_BGCP_SYSTEMS")
public class TBgcpSystems {

	public TBgcpSystems()
	{
		
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BASE_SYSTEM", nullable = false)
    private String baseApp;

    @Column(name = "SYSTEM_CODE", nullable = false)
    private String appCode;

    @Column(name = "IS_ENABLED")
    private String isEnabled;

    @Column(name = "REC_LENGTH")
    private Integer recLength;

    @Column(name = "HEADER_FILE")
    private String headerFile;

    @Column(name = "HEADER_FORMAT")
    private String headerFormat;

    @Column(name = "DETAIL_FILE")
    private String detailFile;

    @Column(name = "DETAIL_FORMAT")
    private String detailFormat;

    @Column(name = "FOOTER_FILE")
    private String footerFile;

    @Column(name = "FOOTER_FORMAT")
    private String footerFormat;

    @Column(name = "CONTROL_FILE")
    private String controlFile;

    @Column(name = "CONTROL_FORMAT")
    private String controlFormat;

    @Column(name = "BASE_FOLDER")
    private String baseFolder;
    
    @Column(name = "FILE_FOLDER")
    private String fileFolder;

    @Column(name = "DATA_VERIFICATION_COL", nullable = false)
    private String dataVerificationCol;
    
    @Column(name = "DATA_PRO_METHOD", nullable = false)
    private String dataProcessMetod;
    
    
     @Column(name = "DETAIL_TABLE")
    private String DETAIL_TABLE;
    
	public String getDETAIL_TABLE() {
		return DETAIL_TABLE;
	}

	public void setDETAIL_TABLE(String dETAIL_TABLE) {
		DETAIL_TABLE = dETAIL_TABLE;
	}

	public String getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(String fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getDataProcessMetod() {
		return dataProcessMetod;
	}

	public void setDataProcessMetod(String dataProcessMetod) {
		this.dataProcessMetod = dataProcessMetod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaseApp() {
		return baseApp;
	}

	public void setBaseApp(String baseApp) {
		this.baseApp = baseApp;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getRecLength() {
		return recLength;
	}

	public void setRecLength(Integer recLength) {
		this.recLength = recLength;
	}

	public String getHeaderFile() {
		return headerFile;
	}

	public void setHeaderFile(String headerFile) {
		this.headerFile = headerFile;
	}

	public String getHeaderFormat() {
		return headerFormat;
	}

	public void setHeaderFormat(String headerFormat) {
		this.headerFormat = headerFormat;
	}

	public String getDetailFile() {
		return detailFile;
	}

	public void setDetailFile(String detailFile) {
		this.detailFile = detailFile;
	}

	public String getDetailFormat() {
		return detailFormat;
	}

	public void setDetailFormat(String detailFormat) {
		this.detailFormat = detailFormat;
	}

	public String getFooterFile() {
		return footerFile;
	}

	public void setFooterFile(String footerFile) {
		this.footerFile = footerFile;
	}

	public String getFooterFormat() {
		return footerFormat;
	}

	public void setFooterFormat(String footerFormat) {
		this.footerFormat = footerFormat;
	}

	public String getControlFile() {
		return controlFile;
	}

	public void setControlFile(String controlFile) {
		this.controlFile = controlFile;
	}

	public String getControlFormat() {
		return controlFormat;
	}

	public void setControlFormat(String controlFormat) {
		this.controlFormat = controlFormat;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	public String getDataVerificationCol() {
		return dataVerificationCol;
	}

	public void setDataVerificationCol(String dataVerificationCol) {
		this.dataVerificationCol = dataVerificationCol;
	}

	@Override
	public String toString() {
		return "TBgcpSystems [id=" + id + ", baseApp=" + baseApp + ", appCode=" + appCode + ", isEnabled=" + isEnabled
				+ ", recLength=" + recLength + ", headerFile=" + headerFile + ", headerFormat=" + headerFormat
				+ ", detailFile=" + detailFile + ", detailFormat=" + detailFormat + ", footerFile=" + footerFile
				+ ", footerFormat=" + footerFormat + ", controlFile=" + controlFile + ", controlFormat=" + controlFormat
				+ ", baseFolder=" + baseFolder + ", dataVerificationCol=" + dataVerificationCol + "]";
	}

   
}
