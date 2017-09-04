using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface ICandidateRepository
    {
        Task<Candidate> InsertCandidate(Candidate candidate);
    }
}