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
        private readonly IMapper _Mapper;

        private readonly IInterviewRepository _InterviewRepository;

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        public InterviewManager(
            IMapper mapper,
            IInterviewRepository interviewRepository,
            IInterviewTemplateRepository interviewTemplateRepository)
        {
            _Mapper = mapper;
            _InterviewRepository = interviewRepository;
            _InterviewTemplateRepository = interviewTemplateRepository;
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