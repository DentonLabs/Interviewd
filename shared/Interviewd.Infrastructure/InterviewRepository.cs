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
    public class InterviewRepository : IInterviewRepository
    {
        private readonly AppSettings _AppSettings;

        private readonly IMapper _Mapper;

        public InterviewRepository(
            IOptions<AppSettings> appSettings,
            IMapper mapper)
        {
            _AppSettings = appSettings.Value;
            _Mapper = mapper;
        }

        public async Task<IEnumerable<Interview>> GetInterviews()
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var interviewSqlModels = await connection.QueryAsync<InterviewSqlModel>(
                    StoredProcedures.GetInterviews,
                    commandType: CommandType.StoredProcedure);

                return _Mapper.Map<IEnumerable<Interview>>(interviewSqlModels);
            }
        }

        public async Task<IEnumerable<Question>> GetInterviewQuestions(string id)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var questionSqlModels = await connection.QueryAsync<QuestionSqlModel>(
                    StoredProcedures.GetInterviewQuestions,
                    new
                    {
                        Id = id
                    },
                    commandType: CommandType.StoredProcedure);

                return _Mapper.Map<IEnumerable<Question>>(questionSqlModels);
            }
        }

        public async Task<Interview> InsertInterview()
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters();

                parameters.Add("Id", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    StoredProcedures.InsertInterview,
                    parameters,
                    commandType: CommandType.StoredProcedure);

                var interview = new Interview();

                interview.Id = parameters.Get<int>("Id").ToString();

                return interview;
            }
        }

        public async Task InsertInterviewQuestions(string interviewId, IEnumerable<string> questionIds)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                await connection.ExecuteAsync(
                    StoredProcedures.InsertInterviewQuestions,
                    new
                    {
                        InterviewId = interviewId,
                        QuestionIds = new IdCustomParameter(questionIds.Select(id => Convert.ToInt32(id)))
                    },
                    commandType: CommandType.StoredProcedure);
            }
        }
    }
}