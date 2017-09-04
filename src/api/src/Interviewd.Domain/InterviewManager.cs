using System.Threading.Tasks;
using AutoMapper;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Domain
{
    public class InterviewManager : IInterviewManager
    {
        private readonly IInterviewRepository _InterviewRepository;

        private readonly IMapper _Mapper;

        public InterviewManager(
            IMapper mapper,
            IInterviewRepository interviewRepository)
        {
            _Mapper = mapper;
            _InterviewRepository = interviewRepository;
        }

        public async Task<InterviewDto> CreateInterview()
        {
            var createdInterview = await _InterviewRepository.InsertInterview();
            return _Mapper.Map<InterviewDto>(createdInterview);
        }
    }
}