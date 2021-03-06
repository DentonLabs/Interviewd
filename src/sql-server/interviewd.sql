USE [master]
GO
/****** Object:  Database [Interviewd]    Script Date: 9/30/2017 10:02:41 PM ******/
CREATE DATABASE [Interviewd]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Interviewd', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.INTERVIEWD\MSSQL\DATA\Interviewd.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Interviewd_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.INTERVIEWD\MSSQL\DATA\Interviewd_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [Interviewd] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Interviewd].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Interviewd] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Interviewd] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Interviewd] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Interviewd] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Interviewd] SET ARITHABORT OFF 
GO
ALTER DATABASE [Interviewd] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Interviewd] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Interviewd] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Interviewd] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Interviewd] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Interviewd] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Interviewd] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Interviewd] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Interviewd] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Interviewd] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Interviewd] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Interviewd] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Interviewd] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Interviewd] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Interviewd] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Interviewd] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Interviewd] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Interviewd] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Interviewd] SET  MULTI_USER 
GO
ALTER DATABASE [Interviewd] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Interviewd] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Interviewd] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Interviewd] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Interviewd] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Interviewd] SET QUERY_STORE = OFF
GO
USE [Interviewd]
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [Interviewd]
GO
/****** Object:  User [interviewd]    Script Date: 9/30/2017 10:02:41 PM ******/
CREATE USER [interviewd] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [developer]    Script Date: 9/30/2017 10:02:41 PM ******/
CREATE USER [developer] FOR LOGIN [developer] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  UserDefinedTableType [dbo].[Ids]    Script Date: 9/30/2017 10:02:41 PM ******/
CREATE TYPE [dbo].[Ids] AS TABLE(
	[Id] [int] NULL
)
GO
/****** Object:  Table [dbo].[Candidates]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Candidates](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[GivenName] [nvarchar](max) NULL,
	[Surname] [nvarchar](max) NULL,
 CONSTRAINT [PK_Candidates] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[InterviewQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InterviewQuestions](
	[InterviewId] [int] NOT NULL,
	[QuestionId] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Interviews]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Interviews](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CandidateId] [int] NULL,
 CONSTRAINT [PK_Interviews] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[InterviewTemplateQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InterviewTemplateQuestions](
	[InterviewTemplateId] [int] NOT NULL,
	[QuestionId] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[InterviewTemplates]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InterviewTemplates](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_InterviewTemplates] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Questions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Questions](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Description] [nvarchar](max) NULL,
	[Name] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Questions] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
ALTER TABLE [dbo].[InterviewQuestions]  WITH CHECK ADD  CONSTRAINT [FK_InterviewQuestions_Interviews] FOREIGN KEY([InterviewId])
REFERENCES [dbo].[Interviews] ([Id])
GO
ALTER TABLE [dbo].[InterviewQuestions] CHECK CONSTRAINT [FK_InterviewQuestions_Interviews]
GO
ALTER TABLE [dbo].[InterviewQuestions]  WITH CHECK ADD  CONSTRAINT [FK_InterviewQuestions_Questions] FOREIGN KEY([QuestionId])
REFERENCES [dbo].[Questions] ([Id])
GO
ALTER TABLE [dbo].[InterviewQuestions] CHECK CONSTRAINT [FK_InterviewQuestions_Questions]
GO
ALTER TABLE [dbo].[Interviews]  WITH CHECK ADD  CONSTRAINT [FK_Interviews_Candidate] FOREIGN KEY([CandidateId])
REFERENCES [dbo].[Candidates] ([Id])
GO
ALTER TABLE [dbo].[Interviews] CHECK CONSTRAINT [FK_Interviews_Candidate]
GO
ALTER TABLE [dbo].[InterviewTemplateQuestions]  WITH CHECK ADD  CONSTRAINT [FK_InterviewTemplateQuestions_InterviewTemplate] FOREIGN KEY([InterviewTemplateId])
REFERENCES [dbo].[InterviewTemplates] ([Id])
GO
ALTER TABLE [dbo].[InterviewTemplateQuestions] CHECK CONSTRAINT [FK_InterviewTemplateQuestions_InterviewTemplate]
GO
ALTER TABLE [dbo].[InterviewTemplateQuestions]  WITH CHECK ADD  CONSTRAINT [FK_InterviewTemplateQuestions_Questions] FOREIGN KEY([QuestionId])
REFERENCES [dbo].[Questions] ([Id])
GO
ALTER TABLE [dbo].[InterviewTemplateQuestions] CHECK CONSTRAINT [FK_InterviewTemplateQuestions_Questions]
GO
/****** Object:  StoredProcedure [dbo].[Get_Candidate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Candidate]
	@Id int
AS
	SELECT c.Id, c.GivenName, c.Surname
	FROM dbo.Candidates c
	WHERE c.Id = @Id
GO
/****** Object:  StoredProcedure [dbo].[Get_Candidates]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Candidates] 
AS
	SELECT c.Id, c.GivenName, c.Surname
	FROM dbo.Candidates c
GO
/****** Object:  StoredProcedure [dbo].[Get_Interview]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Interview] (
	@Id int
) AS
	SELECT i.Id, c.Id as CandidateId, c.GivenName as CandidateGivenName, c.Surname as CandidateSurname
	FROM dbo.Interviews i
		INNER JOIN dbo.Candidates c
			ON i.CandidateId = c.Id
	WHERE i.Id = @Id
GO
/****** Object:  StoredProcedure [dbo].[Get_InterviewQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_InterviewQuestions] (
	@Id int
) AS
	SELECT q.Id, q.Name, q.Description
	FROM dbo.InterviewQuestions iq
		INNER JOIN dbo.Questions q
			ON iq.QuestionId = q.Id
	WHERE iq.InterviewId = @Id
	
GO
/****** Object:  StoredProcedure [dbo].[Get_Interviews]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Interviews] 
AS
	SELECT i.Id, c.Id as CandidateId, c.GivenName as CandidateGivenName, c.Surname as CandidateSurname
	FROM dbo.Interviews i
		INNER JOIN dbo.Candidates c 
			ON i.CandidateId = c.Id
GO
/****** Object:  StoredProcedure [dbo].[Get_InterviewTemplate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_InterviewTemplate] (
	@InterviewTemplateId int
) AS
SELECT it.Id, it.Name
FROM dbo.InterviewTemplates it
WHERE it.Id = @InterviewTemplateId
GO
/****** Object:  StoredProcedure [dbo].[Get_InterviewTemplateQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_InterviewTemplateQuestions] (
	@InterviewTemplateId int
) AS
SELECT q.Id, q.Name, q.Description
FROM dbo.InterviewTemplateQuestions itq
	INNER JOIN dbo.Questions q
		ON itq.QuestionId = q.Id
WHERE itq.InterviewTemplateId = @InterviewTemplateId
GO
/****** Object:  StoredProcedure [dbo].[Get_InterviewTemplates]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_InterviewTemplates]
AS
	SELECT it.Id, it.Name
	FROM dbo.InterviewTemplates it
GO
/****** Object:  StoredProcedure [dbo].[Get_Question]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Question]
	@Id int
AS
	SELECT q.Id, q.Name, q.Description
	FROM dbo.Questions q
	WHERE q.Id = @Id
GO
/****** Object:  StoredProcedure [dbo].[Get_Questions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Get_Questions]
AS
	SELECT q.Id, q.Name, q.Description
	FROM dbo.Questions q
GO
/****** Object:  StoredProcedure [dbo].[Insert_Candidate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_Candidate] (
	@GivenName nvarchar(MAX),
	@Surname nvarchar(MAX),
	@Id int output
) AS
	INSERT INTO dbo.Candidates (GivenName, Surname)
	VALUES (@GivenName, @Surname)

	SELECT @Id = SCOPE_IDENTITY();
GO
/****** Object:  StoredProcedure [dbo].[Insert_Interview]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_Interview] (
	@CandidateId int = NULL,
	@Id int output
)
AS
	INSERT INTO dbo.Interviews (CandidateId)
	VALUES (@CandidateId)

	SELECT @Id = SCOPE_IDENTITY();

GO
/****** Object:  StoredProcedure [dbo].[Insert_InterviewQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_InterviewQuestions]
	@InterviewId int,
	@QuestionIds Ids readonly
AS
	INSERT INTO dbo.InterviewQuestions (InterviewId, QuestionId)
		SELECT @InterviewId, Id
		FROM @QuestionIds
GO
/****** Object:  StoredProcedure [dbo].[Insert_InterviewTemplate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_InterviewTemplate]
	@Name nvarchar(max),
	@Id int output
AS
	INSERT INTO dbo.InterviewTemplates (Name)
	VALUES (@Name)

	SELECT @Id = SCOPE_IDENTITY();

GO
/****** Object:  StoredProcedure [dbo].[Insert_InterviewTemplateQuestions]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_InterviewTemplateQuestions]
	@InterviewTemplateId int,
	@QuestionIds Ids readonly
AS
	INSERT INTO dbo.InterviewTemplateQuestions (InterviewTemplateId, QuestionId)
		SELECT @InterviewTemplateId, Id
		FROM @QuestionIds

GO
/****** Object:  StoredProcedure [dbo].[Insert_Question]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Insert_Question]
	@Name nvarchar(max),
	@Description nvarchar(max),
	@Id int output
AS
	INSERT INTO dbo.Questions (Name, Description)
	VALUES (@Name, @Description)

	SELECT @Id = SCOPE_IDENTITY();

GO
/****** Object:  StoredProcedure [dbo].[Update_Candidate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Update_Candidate] (
	@Id int,
	@GivenName nvarchar(max),
	@Surname nvarchar(max)
)
AS
	UPDATE dbo.Candidates 
	SET GivenName = @GivenName, Surname = @Surname
	WHERE Id = @Id

GO
/****** Object:  StoredProcedure [dbo].[Update_InterviewTemplate]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Update_InterviewTemplate] (
	@Id int,
	@Name nvarchar(max)
) AS
	UPDATE dbo.InterviewTemplates
	SET Name = @Name
	WHERE Id = @Id
GO
/****** Object:  StoredProcedure [dbo].[Update_Question]    Script Date: 9/30/2017 10:02:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[Update_Question] (
	@Id int,
	@Name nvarchar(max),
	@Description nvarchar(max)
) AS
	UPDATE dbo.Questions 
	SET Name=@Name, Description=@Description
	WHERE Id = @Id
GO
USE [master]
GO
ALTER DATABASE [Interviewd] SET  READ_WRITE 
GO
