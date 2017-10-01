using System.Collections.Generic;
using System.Threading.Tasks;
using AutoMapper;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Domain
{
    public class CandidateManager : ICandidateManager
    {
        private readonly ICandidateRepository _CandidateRepository;

        private readonly IMapper _Mapper;

        public CandidateManager(
            IMapper mapper,
            ICandidateRepository candidateRepository)
        {
            _Mapper = mapper;
            _CandidateRepository = candidateRepository;
        }

        public async Task<CandidateDto> CreateCandidate(CandidateDto candidateDto)
        {
            var createdCandidate = await _CandidateRepository.InsertCandidate(_Mapper.Map<Candidate>(candidateDto));
            return _Mapper.Map<CandidateDto>(createdCandidate);
        }

        public async Task<CandidateDto> GetCandidate(string id)
        {
            var candidate = await _CandidateRepository.GetCandidate(id);
            return _Mapper.Map<CandidateDto>(candidate);
        }

        public async Task<CandidateDto> UpdateCandidate(CandidateDto candidate)
        {
            var updatedCandidate = await _CandidateRepository.UpdateCandidate(_Mapper.Map<Candidate>(candidate));
            return _Mapper.Map<CandidateDto>(updatedCandidate);
        }

        public async Task<IEnumerable<CandidateDto>> GetAllCandidates()
        {
            var candidates = await _CandidateRepository.GetAllCandidates();
            return _Mapper.Map<IEnumerable<CandidateDto>>(candidates);
        }
    }
}