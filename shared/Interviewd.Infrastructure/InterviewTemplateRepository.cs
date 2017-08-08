using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;
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

        public InterviewTemplateRepository(IOptions<AppSettings> appSettings)
        {
            _AppSettings = appSettings.Value;
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
    }
}