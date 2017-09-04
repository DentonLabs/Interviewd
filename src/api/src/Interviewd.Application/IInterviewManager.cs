using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IInterviewManager
    {
        Task<InterviewDto> CreateInterview();
    }
}