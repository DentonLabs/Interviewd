using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Dapper;
using Interviewd.Application;
using Interviewd.Domain;
using Microsoft.Extensions.Options;

namespace Interviewd.Infrastructure
{
    public class QuestionRepository : IQuestionRepository
    {
        private readonly AppSettings _AppSettings;

        public QuestionRepository(IOptions<AppSettings> appSettings)
        {
            _AppSettings = appSettings.Value;
        }

        public async Task InsertQuestion(Question question)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters(
                    new
                    {
                        Name = question.
                        Description = question.Description
                    });

                parameters.Add("ID", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    "Insert_Question",
                    parameters,
                    commandType: CommandType.StoredProcedure);

                question.Id = parameters.Get<int>("ID").ToString();
            }
        }

        public async Task<IEnumerable<Question>> GetQuestions()
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var questionSqlModels = await connection.QueryAsync<QuestionSqlModel>("Get_Questions");

                return questionSqlModels.Select(q => 
                    new Question
                    {
                        Id = q.Id,
                        Description = q.Description
                    });
            }
        }
    }
}