using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;

namespace Interviewd.Domain
{
    public class QuestionManager : IQuestionManager
    {
        private readonly IQuestionRepository _QuestionRepository;

        public QuestionManager(IQuestionRepository questionRepository)
        {
            _QuestionRepository = questionRepository;
        }

        public async Task CreateQuestion(QuestionDto questionDto)
        {
            var question = new Question
            {
                Description = questionDto.Description
            };

            await _QuestionRepository.InsertQuestion(question);
        }

        public async Task<IEnumerable<QuestionDto>> GetQuestions()
        {
            var questions = await _QuestionRepository.GetQuestions();
            return questions.Select(q =>
            {
                return new QuestionDto
                {
                    Id = q.Id,
                    Description = q.Description
                };
            });
        }
    }
}