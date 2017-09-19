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

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        private readonly IFixture _Fixture;

        public Arranger(
            IInterviewRepository interviewRepository,
            IQuestionRepository questionRepository,
            IInterviewTemplateRepository interviewTemplateRepository)
        {
            _InterviewRepository = interviewRepository;
            _QuestionRepository = questionRepository;
            _InterviewTemplateRepository = interviewTemplateRepository;
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

        public async Task<IEnumerable<Question>> CreateQuestions()
        {
            var questions = new List<Question>();

            for (var i = 0; i < 3; i++)
            {
                questions.Add(await _QuestionRepository.InsertQuestion(_Fixture.Create<Question>()));
            }

            return questions;
        }

        public async Task<Question> CreateQuestion()
        {
            return await _QuestionRepository.InsertQuestion(_Fixture.Create<Question>());
        }

        public async Task<InterviewTemplate> CreateInterviewTemplate()
        {
            var interviewTemplate =
                _Fixture.Build<InterviewTemplate>()
                    .Without(o => o.Questions)
                    .Without(o => o.Id)
                    .Create();

            interviewTemplate = await _InterviewTemplateRepository.InsertInterviewTemplate(interviewTemplate);

            interviewTemplate.Questions = await CreateQuestions();

            await _InterviewTemplateRepository.InsertInterviewTemplateQuestions(
                interviewTemplate.Id,
                interviewTemplate.Questions.Select(q => q.Id));

            return interviewTemplate;
        }
    }
}