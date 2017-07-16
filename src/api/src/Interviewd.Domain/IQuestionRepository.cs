using System.Collections.Generic;
using System.Threading.Tasks;

namespace Interviewd.Domain
{
    public interface IQuestionRepository
    {
        Task<Question> InsertQuestion(Question question);

        Task<IEnumerable<Question>> GetQuestions();
    }
}