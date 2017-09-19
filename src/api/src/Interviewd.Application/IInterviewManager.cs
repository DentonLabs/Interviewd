using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IInterviewManager
    {
        Task<InterviewDto> GetInterview(string id);
        
        Task<InterviewDto> CreateInterview(string templateId = null);
    }
}