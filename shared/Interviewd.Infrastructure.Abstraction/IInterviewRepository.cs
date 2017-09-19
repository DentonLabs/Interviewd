using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IInterviewRepository
    {
        Task<IEnumerable<Question>> GetInterviewQuestions(string id);

        Task<Interview> InsertInterview();

        Task InsertInterviewQuestions(string interviewId, IEnumerable<string> questionIds);
    }
}