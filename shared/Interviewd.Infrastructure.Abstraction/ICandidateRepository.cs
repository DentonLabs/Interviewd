using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface ICandidateRepository
    {
        Task<Candidate> InsertCandidate(Candidate candidate);

        Task<IEnumerable<Candidate>> GetAllCandidates();
    }
}