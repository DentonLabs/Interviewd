using System.Linq;
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

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        public InterviewManager(
            IMapper mapper,
            IInterviewRepository interviewRepository)
        {
            _Mapper = mapper;
            _InterviewRepository = interviewRepository;
        }

        public async Task<InterviewDto> CreateInterview(string templateId = null)
        {
            var createdInterview = await _InterviewRepository.InsertInterview();

            if (!string.IsNullOrWhiteSpace(templateId))
            {
                var interviewTemplate = await _InterviewTemplateRepository.GetInterviewTemplate(templateId);

                await _InterviewRepository.InsertInterviewQuestions(
                    createdInterview.Id,
                    interviewTemplate.Questions.Select(q => q.Id));

                createdInterview.Questions = interviewTemplate.Questions;
            }

            return _Mapper.Map<InterviewDto>(createdInterview);
        }
    }
}