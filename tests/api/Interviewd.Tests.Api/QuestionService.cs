using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using Dapper;

namespace Interviewd.Tests.Api
{
    public class QuestionService
    {
        public static async Task<Question> GetQuestion(string id)
        {
            using (var connection = new SqlConnection("Server=localhost\\interviewd;Database=Interviewd;Trusted_Connection=True;MultipleActiveResultSets=true"))
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