using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IInterviewRepository
    {
        Task<IEnumerable<Interview>> GetInterviews();

        Task<IEnumerable<Question>> GetInterviewQuestions(string id);

        Task<Interview> InsertInterview(string candidateId);

        Task InsertInterviewQuestions(string interviewId, IEnumerable<string> questionIds);
    }
}