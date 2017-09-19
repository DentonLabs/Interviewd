using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IInterviewManager
    {
        Task<IEnumerable<InterviewDto>> GetInterviews();

        Task<InterviewDto> GetInterview(string id);
        
        Task<InterviewDto> CreateInterview(string templateId = null);
    }
}