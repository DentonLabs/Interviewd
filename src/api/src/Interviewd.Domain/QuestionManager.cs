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

        public async Task<QuestionDto> CreateQuestion(QuestionDto questionDto)
        {
            var question = new Question
            {
                Name = questionDto.Name,
                Description = questionDto.Description
            };

            var createdQuestion = await _QuestionRepository.InsertQuestion(question);

            return new QuestionDto
            {
                Id = createdQuestion.Id,
                Name = createdQuestion.Name,
                Description = createdQuestion.Description
            };
        }

        public async Task<IEnumerable<QuestionDto>> GetQuestions()
        {
            var questions = await _QuestionRepository.GetQuestions();
            return questions.Select(q => 
                new QuestionDto
                {
                    Id = q.Id,
                    Description = q.Description
                });
        }
    }
}