using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Domain.Model;

namespace Interviewd.Infrastructure.Abstraction
{
    public interface IQuestionRepository
    {
        Task<Question> InsertQuestion(Question question);

        Task<IEnumerable<Question>> GetQuestions();

        Task<Question> GetQuestion(string id);
    }
}