using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IInterviewTemplateManager
    {
        Task<InterviewTemplateDto> CreateInterviewTemplate(InterviewTemplateDto interviewTemplateDto);

        Task<InterviewTemplateDto> GetInterviewTemplate(string interviewTemplateId);
    }
}