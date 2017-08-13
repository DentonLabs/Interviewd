using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IInterviewTemplateRepository
    {
        Task<InterviewTemplate> InsertInterviewTemplate(InterviewTemplate interviewTemplate);

        Task InsertInterviewTemplateQuestions(string interviewTemplateId, IEnumerable<string> questionIds);
    }
}