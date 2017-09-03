using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Dapper;
using Interviewd.Configuration;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Microsoft.Extensions.Options;

namespace Interviewd.Infrastructure
{
    public class InterviewTemplateRepository : IInterviewTemplateRepository
    {
        private readonly AppSettings _AppSettings;

        private readonly IMapper _Mapper;

        public InterviewTemplateRepository(
            IOptions<AppSettings> appSettings,
            IMapper mapper)
        {
            _AppSettings = appSettings.Value;
            _Mapper = mapper;
        }

        public async Task<InterviewTemplate> InsertInterviewTemplate(InterviewTemplate interviewTemplate)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters(
                    new
                    {
                        Name = interviewTemplate.Name,
                    });

                parameters.Add("Id", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    StoredProcedures.InsertInterviewTemplate,
                    parameters,
                    commandType: CommandType.StoredProcedure);

                interviewTemplate.Id = parameters.Get<int>("Id").ToString();

                return interviewTemplate;
            }
        }

        public async Task InsertInterviewTemplateQuestions(string interviewTemplateId, IEnumerable<string> questionIds)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                await connection.ExecuteAsync(
                    StoredProcedures.InsertInterviewTemplateQuestions,
                    new
                    {
                        InterviewTemplateId = interviewTemplateId,
                        QuestionIds = new IdCustomParameter(questionIds.Select(id => Convert.ToInt32(id)))
                    },
                    commandType: CommandType.StoredProcedure);
            }
        }

        public async Task<InterviewTemplate> GetInterviewTemplate(string interviewTemplateId)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var interviewTemplateSqlModel = (await connection.QueryAsync<InterviewTemplateSqlModel>(
                    StoredProcedures.GetInterviewTemplate,
                    new
                    {
                        InterviewTemplateId = interviewTemplateId,
                    },
                    commandType: CommandType.StoredProcedure))
                    .Single();

                var interviewTemplateQuestionSqlModels = await connection.QueryAsync<QuestionSqlModel>(
                    StoredProcedures.GetInterviewTemplateQuestions,
                    new
                    {
                        InterviewTemplateId = interviewTemplateId
                    },
                    commandType: CommandType.StoredProcedure);

                var interviewTemplateQuestions = _Mapper.Map<IEnumerable<Question>>(interviewTemplateQuestionSqlModels);
                var interviewTemplate = _Mapper.Map<InterviewTemplate>(interviewTemplateSqlModel);
                interviewTemplate.Questions = interviewTemplateQuestions;

                return interviewTemplate;
            }
        }
    }
}