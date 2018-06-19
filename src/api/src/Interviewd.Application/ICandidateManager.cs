using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface ICandidateManager
    {
        Task<CandidateDto> CreateCandidate(CandidateDto candidateDto);

        Task<CandidateDto> GetCandidate(string id);

        Task<CandidateDto> UpdateCandidate(CandidateDto candidate);

        Task<IEnumerable<CandidateDto>> GetAllCandidates();
    }
}