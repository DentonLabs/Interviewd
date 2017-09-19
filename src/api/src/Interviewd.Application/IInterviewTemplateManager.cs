using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IInterviewTemplateManager
    {
        Task<InterviewTemplateDto> CreateInterviewTemplate(InterviewTemplateDto interviewTemplateDto);

        Task<IEnumerable<InterviewTemplateDto>> GetInterviewTemplates()

        Task<InterviewTemplateDto> GetInterviewTemplate(string interviewTemplateId);
    }
}