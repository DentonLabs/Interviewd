using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Dapper;
using Microsoft.Extensions.Options;
using Interviewd.Configuration;
using Interviewd.Domain.Model;

namespace Interviewd.Tests.Api
{
    public class QuestionService
    {
        private readonly AppSettings _AppSettings;

        public QuestionService(IOptions<AppSettings> appSettings)
        {
            _AppSettings = appSettings.Value;
        }

        public async Task<Question> InsertQuestion(Question question)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters(
                    new
                    {
                        Name = question.Name,
                        Description = question.Description
                    });

                parameters.Add("Id", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    "Insert_Question",
                    parameters,
                    commandType: CommandType.StoredProcedure);

                question.Id = parameters.Get<int>("Id").ToString();

                return question;
            }
        }

        public async Task<Question> GetQuestion(string id)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var question = await connection.QueryAsync<Question>(
                    "Get_Question",
                    new
                    {
                        Id = id
                    });

                return question.Single();
            }
        }
    }
}