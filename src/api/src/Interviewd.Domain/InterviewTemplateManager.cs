using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Domain
{
    public class InterviewTemplateManager : IInterviewTemplateManager
    {
        private readonly IMapper _Mapper;

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        public InterviewTemplateManager(
            IMapper mapper,
            IInterviewTemplateRepository interviewTemplateRepository)
        {
            _Mapper = mapper;
            _InterviewTemplateRepository = interviewTemplateRepository;
        }

        public async Task<InterviewTemplateDto> CreateInterviewTemplate(InterviewTemplateDto interviewTemplateDto)
        {
            var interviewTemplate = _Mapper.Map<InterviewTemplate>(interviewTemplateDto);
            var createdInterviewTemplate = await _InterviewTemplateRepository.InsertInterviewTemplate(interviewTemplate);
            await _InterviewTemplateRepository.InsertInterviewTemplateQuestions(interviewTemplate.Questions.Select(q => q.Id));
            return _Mapper.Map<InterviewTemplateDto>(createdInterviewTemplate);
        }
    }
}