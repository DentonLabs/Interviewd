using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    public class Arranger
    {
        private readonly IInterviewRepository _InterviewRepository;

        private readonly IQuestionRepository _QuestionRepository;

        private readonly IFixture _Fixture;

        public Arranger(
            IInterviewRepository interviewRepository,
            IQuestionRepository questionRepository)
        {
            _InterviewRepository = interviewRepository;
            _QuestionRepository = questionRepository;
            _Fixture = new Fixture();
        }

        public async Task<Interview> CreateInterview()
        {
            var question1 = _Fixture.Build<Question>()
                .Without(o => o.Id)
                .Create();

            var question2 = _Fixture.Build<Question>()
                .Without(o => o.Id)
                .Create();

            await _QuestionRepository.InsertQuestion(question1);
            await _QuestionRepository.InsertQuestion(question2);

            var interview = await _InterviewRepository.InsertInterview();
            interview.Questions = new List<Question> { question1, question2 };
            await _InterviewRepository.InsertInterviewQuestions(
                interview.Id, 
                interview.Questions.Select(q => q.Id));

            return interview;
        }
    }
}