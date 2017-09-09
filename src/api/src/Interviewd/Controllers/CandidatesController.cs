using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("candidates")]
    public class CandidatesController : Controller
    {
        private readonly ICandidateManager _CandidateManager;

        public CandidatesController(ICandidateManager candidateManager)
        {
            _CandidateManager = candidateManager;
        }

        [HttpPost]
        public async Task<CandidateDto> PostCandidate([FromBody]CandidateDto candidateDto)
        {
            return await _CandidateManager.CreateCandidate(candidateDto);
        }

        [HttpGet]
        public async Task<IEnumerable<CandidateDto>> GetAllCandidates()
        {
            return await _CandidateManager.GetAllCandidates();
        }

        [HttpGet]
        public async Task<CandidateDto> GetCandidate(string id)
        {
            return await _CandidateManager.GetCandidate(id);
        }
    }
}