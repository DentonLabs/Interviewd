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
            var responseInterviewTemplate = _Mapper.Map<InterviewTemplateDto>(createdInterviewTemplate);

            if (interviewTemplate.Questions != null)
            {
                await _InterviewTemplateRepository.InsertInterviewTemplateQuestions(createdInterviewTemplate.Id, interviewTemplate.Questions.Select(q => q.Id));
                responseInterviewTemplate.QuestionIds = interviewTemplateDto.QuestionIds;
            }

            return responseInterviewTemplate;
        }

        public async Task<InterviewTemplateDto> GetInterviewTemplate(string interviewTemplateId)
        {
            var interviewTemplate = await _InterviewTemplateRepository.GetInterviewTemplate(interviewTemplateId);
            var interviewTemplateDto = _Mapper.Map<InterviewTemplateDto>(interviewTemplate);
            return interviewTemplateDto;
        }
    }
}