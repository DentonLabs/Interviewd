using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;
using Dapper;
using Interviewd.Configuration;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Infrastructure
{
    public class InterviewRepository : IInterviewRepository
    {
        private readonly AppSettings _AppSettings;

        public InterviewRepository(AppSettings appSettings)
        {
            _AppSettings = appSettings;
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
    }
}