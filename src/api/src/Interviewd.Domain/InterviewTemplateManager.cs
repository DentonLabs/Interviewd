using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;

namespace Interviewd.Domain
{
    public class InterviewTemplateManager : IInterviewTemplateManager
    {
        public Task<InterviewTemplateDto> CreateInterviewTemplate(InterviewTemplateDto interviewTemplateDto)
        {
            throw new System.NotImplementedException();
        }
    }
}