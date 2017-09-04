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
    public class CandidateRepository : ICandidateRepository
    {
        private readonly AppSettings _AppSettings;
        
        public CandidateRepository(IOptions<AppSettings> appSettings)
        {
            _AppSettings = appSettings.Value;
        }

        public async Task<Candidate> InsertCandidate(Candidate candidate)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters(
                    new
                    {
                        GivenName = candidate.GivenName,
                        Surname = candidate.Surname
                    });

                parameters.Add("Id", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    StoredProcedures.InsertCandidate,
                    parameters,
                    commandType: CommandType.StoredProcedure);

                candidate.Id = parameters.Get<int>("Id").ToString();

                return candidate;
            }
        }
    }
}