
CREATE TABLE [T_BGCP_SYSTEMS1](
	[S_NO] [int] NULL,
	[BASE_SYSTEM] [varchar](50) NULL,
	[SYSTEM_CODE] [varchar](10) NULL,
	[IS_ENABLED] [varchar](2) NULL,
	[ALLOW_RELOAD] [varchar](2) NULL,
	[DELETE_MECH] [varchar](2) NULL,
	[RELOAD_CONFIG] [varchar](2) NULL,
	[FILE_TYPE] [varchar](10) NULL,
	[REC_LENGTH] [int] NULL,
	[HEADER_FILE] [varchar](15) NULL,
	[DETAIL_FILE] [varchar](15) NULL,
	[FOOTER_FILE] [varchar](15) NULL,
	[CONTROL_FILE] [varchar](15) NULL,
	[FILE_FOLDER] [varchar](15) NULL,
	[ARCHIVE_FOLDER] [varchar](15) NULL,
	[HEADER_TABLE] [varchar](30) NULL,
	[DETAIL_TABLE] [varchar](30) NULL,
	[FOOTER_TABLE] [varchar](30) NULL,
	[CONTROL_TABLE] [varchar](30) NULL,
	[DATA_VERIFICATION_COL] [varchar](30) NULL,
	[DATA_PRO_METHOD] [nchar](10) NULL
) ;



CREATE TABLE [T_BGCP_SUMMARY](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[file_name] [nvarchar](255) NULL,
	[run_date] [nvarchar](10) NULL,
	[total_lines_in_file] [int] NULL,
	[total_lines_in_table] [int] NULL,
	[matched_records] [int] NULL,
	[unmatched_records] [int] NULL,
	[created_at] [datetime] NULL
);



CREATE TABLE [T_BGCP_JOB_STATUS](
	[JOB_ID] [int] IDENTITY(1,1) NOT NULL,
	[JOBS_DESC] [varchar](50) NULL,
	[SYSTEM_ID] [varchar](10) NULL,
	[JOB_NAME] [varchar](10) NULL,
	[STATUS_LEVEL] [int] NULL,
	[FUNC_NAME] [varchar](50) NULL,
	[FUNC_DESC] [varchar](50) NULL,
	[EFF_DATE] [date] NULL,
	[JOB_TIME] [timestamp] NULL,
);




CREATE TABLE [T_BGCP_EX_BACKEND_LOG](
	[JOB_ID] [int] IDENTITY(1,1) NOT NULL,
	[JOB_DATE] [datetime2](7) NULL,
	[FUN_NAME] [varchar](100) NULL,
	[USER_ID] [varchar](20) NULL,
	[ACTION_INFO] [varchar](120) NULL,
	[ERR_EXP_MESSAGE] [varchar](3000) NULL,
	[STATUS] [varchar](50) NULL,
	[PROC_DATE] [date] NULL,
	[REP_NAME] [varchar](100) NULL
) ;


CREATE TABLE [T_BGCP_DETAIL_SUMMARY](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[file_name] [nvarchar](255) NULL,
	[run_date] [nvarchar](10) NULL,
	[MATCH_OR_UNMATCH] [nvarchar](2) NULL,
	[LINE_NUMBER] [int] NULL,
	[RECORD_LINE] [nvarchar](1000) NULL,
	[created_at] [datetime] NULL
) ;



CREATE TABLE [T_BGCP_COL_CONFIG](
	[S_No] [nvarchar](max) NULL,
	[App_Code] [nvarchar](max) NULL,
	[Target_Table] [nvarchar](max) NULL,
	[Target_Attribute] [nvarchar](max) NULL,
	[Target_Data_Type] [nvarchar](max) NULL,
	[Target_Data_Size] [nvarchar](max) NULL,
	[Description] [nvarchar](max) NULL,
	[Source_Table] [nvarchar](max) NULL,
	[Direct_Or_Derived] [nvarchar](max) NULL,
	[Transformation_Logic] [nvarchar](max) NULL,
	[Source_Attribute] [nvarchar](max) NULL,
	[Start_Position] [nvarchar](max) NULL,
	[End_Position] [nvarchar](max) NULL,
	[Is_Active] [nvarchar](max) NULL,
	[is_Sum_Amt_Col] [nvarchar](max) NULL,
	[Data_Validation] [nvarchar](max) NULL,
	[Unique_Key_Column] [nvarchar](max) NULL
);




