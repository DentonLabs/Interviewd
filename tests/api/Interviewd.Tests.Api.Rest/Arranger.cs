using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoFixture;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;

namespace Interviewd.Tests.Api.Rest
{
    public class Arranger
    {
        private readonly IInterviewRepository _InterviewRepository;

        private readonly IQuestionRepository _QuestionRepository;

        private readonly IInterviewTemplateRepository _InterviewTemplateRepository;

        private readonly ICandidateRepository _CandidateRepository;

        private readonly IFixture _Fixture;

        public Arranger(
            IInterviewRepository interviewRepository,
            IQuestionRepository questionRepository,
            IInterviewTemplateRepository interviewTemplateRepository,
            ICandidateRepository candidateRepository)
        {
            _InterviewRepository = interviewRepository;
            _QuestionRepository = questionRepository;
            _InterviewTemplateRepository = interviewTemplateRepository;
            _CandidateRepository = candidateRepository;
            _Fixture = new Fixture();
        }

        public async Task<IEnumerable<Interview>> CreateInterviews()
        {
            var interviews = new List<Interview>();

            for (int i = 0; i < 3; i++)
            {
                interviews.Add(await CreateInterview());
            }

            return interviews;
        }

        public async Task<Interview> CreateInterview()
        {
            var candidate = await CreateCandidate();
            var interview = await _InterviewRepository.InsertInterview(candidate.Id);
            interview.Candidate = candidate;

            interview.Questions = await CreateQuestions();
            
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
                questions.Add(await CreateQuestion());
            }

            return questions;
        }

        public async Task<Question> GetQuestion(string id)
        {
            return await _QuestionRepository.GetQuestion(id);
        }

        public async Task<IEnumerable<Candidate>> CreateCandidates()
        {
            var dbCandidates = new List<Candidate>();

            for (int i = 0; i < 3; i++)
            {
                dbCandidates.Add(await CreateCandidate());
            }

            return dbCandidates;
        }

        public async Task<Candidate> GetCandidate(string id)
        {
            return await _CandidateRepository.GetCandidate(id);
        }

        public async Task<Candidate> CreateCandidate()
        {
            var candidate = _Fixture.Create<Candidate>();
            return await _CandidateRepository.InsertCandidate(candidate);
        }

        public async Task<Question> CreateQuestion()
        {
            return await _QuestionRepository.InsertQuestion(_Fixture.Create<Question>());
        }

        public async Task<InterviewTemplate> GetInterviewTemplate(string id)
        {
            return await _InterviewTemplateRepository.GetInterviewTemplate(id);
        }

        public async Task<IEnumerable<InterviewTemplate>> CreateInterviewTemplates()
        {
            var interviewTemplates = new List<InterviewTemplate>();

            for (int i = 0; i < 3; i++)
            {
                interviewTemplates.Add(await CreateInterviewTemplate());
            }

            return interviewTemplates;
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