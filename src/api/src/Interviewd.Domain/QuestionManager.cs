using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;

namespace Interviewd.Domain
{
    public class QuestionManager : IQuestionManager
    {
        private readonly IQuestionRepository _QuestionRepository;

        private readonly IMapper _Mapper;

        public QuestionManager(
            IQuestionRepository questionRepository,
            IMapper mapper)
        {
            _QuestionRepository = questionRepository;
            _Mapper = mapper;
        }

        public async Task<QuestionDto> CreateQuestion(QuestionDto questionDto)
        {
            var question = _Mapper.Map<Question>(questionDto);

            var createdQuestion = await _QuestionRepository.InsertQuestion(question);

            return _Mapper.Map<QuestionDto>(createdQuestion);
        }

        public async Task<IEnumerable<QuestionDto>> GetQuestions()
        {
            var questions = await _QuestionRepository.GetQuestions();
            return questions.Select(q => _Mapper.Map<QuestionDto>(q));
        }

        public async Task<QuestionDto> GetQuestion(string id)
        {
            var question = await _QuestionRepository.GetQuestion(id);
            return _Mapper.Map<QuestionDto>(question);
        }
    }
}