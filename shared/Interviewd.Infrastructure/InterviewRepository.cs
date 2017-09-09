using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
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

        public InterviewRepository(IOptions<AppSettings> appSettings)
        {
            _AppSettings = appSettings.Value;
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