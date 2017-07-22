using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface IQuestionManager
    {
        Task<QuestionDto> CreateQuestion(QuestionDto questionDto);

        Task<IEnumerable<QuestionDto>> GetQuestions();

        Task<QuestionDto> GetQuestion(string id);
    }
}