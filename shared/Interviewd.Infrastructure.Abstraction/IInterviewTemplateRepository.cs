using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IInterviewTemplateRepository
    {
        Task<InterviewTemplate> InsertInterviewTemplate(InterviewTemplate interviewTemplate);
    }
}