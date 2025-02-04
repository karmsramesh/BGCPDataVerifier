USE [CLS_WAA]
GO

INSERT INTO [dbo].[T_BGCP_COL_CONFIG]
           ([APP_CODE]
           ,[FILE_FIELD_NAME]
           ,[FIELD_ORDER]
           ,[FIELD_TYPE]
           ,[START_POSITION]
           ,[END_POSITION]
           ,[FIELD_LENGTH]
           ,[TABLE_FIELD_NAME]
           ,[TABLE_FIELD_TYPE]
           ,[IS_ACTIVE]
           ,[UNIQUE_KEY_COLUMN])
     VALUES
           (<APP_CODE, varchar(6),>
           ,<FILE_FIELD_NAME, varchar(100),>
           ,<FIELD_ORDER, int,>
           ,<FIELD_TYPE, varchar(10),>
           ,<START_POSITION, int,>
           ,<END_POSITION, int,>
           ,<FIELD_LENGTH, int,>
           ,<TABLE_FIELD_NAME, varchar(100),>
           ,<TABLE_FIELD_TYPE, varchar(10),>
           ,<IS_ACTIVE, varchar(1),>
           ,<UNIQUE_KEY_COLUMN, varchar(1),>)
GO


USE [CLS_WAA]
GO

INSERT INTO [dbo].[T_BGCP_SYSTEMS1]
           ([S_NO]
           ,[BASE_SYSTEM]
           ,[SYSTEM_CODE]
           ,[IS_ENABLED]
           ,[ALLOW_RELOAD]
           ,[DELETE_MECH]
           ,[RELOAD_CONFIG]
           ,[FILE_TYPE]
           ,[REC_LENGTH]
           ,[HEADER_FILE]
           ,[DETAIL_FILE]
           ,[FOOTER_FILE]
           ,[CONTROL_FILE]
           ,[FILE_FOLDER]
           ,[ARCHIVE_FOLDER]
           ,[HEADER_TABLE]
           ,[DETAIL_TABLE]
           ,[FOOTER_TABLE]
           ,[CONTROL_TABLE]
           ,[DATA_VERIFICATION_COL]
           ,[DATA_PRO_METHOD])
     VALUES(
           1
           ,'SY1'
           ,'SY1'
           ,'Y'
           ,'N'
           ,'H'
           ,'Y'
           ,'TXT'
           ,200
           ,null
           ,'SY1.txt'
           ,null
           ,null
           ,'SY1'
           ,null
           ,null
           ,'T_SY1'
           ,null
           ,null
           ,12
           ,',');
GO



USE [CLS_WAA]
GO

/****** Object:  Table [dbo].[T_BGCP_SYSTEMS1]    Script Date: 23/1/2025 8:02:54 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

drop table [T_BGCP_SYSTEMS1];

CREATE TABLE [dbo].[T_BGCP_SYSTEMS1](
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
) ON [PRIMARY]
GO









1	GCSPEXTR	SY1	Y	Y	H	Y	TXT	NULL	NULL	SY1.txt	NULL	NULL	SYS1	NULL	NULL	T_GCSPEXTR	NULL	NULL	5	FIXED     
