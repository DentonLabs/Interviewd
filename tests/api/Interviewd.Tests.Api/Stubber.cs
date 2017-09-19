using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    public class Stubber
    {
        private readonly IFixture _Fixture;

        public Stubber()
        {
            _Fixture = new Fixture();
        }

        public CandidateDto StubCandidateDto()
        {
            return _Fixture.Build<CandidateDto>()
                .Without(o => o.Id)
                .Create();
        }

        public QuestionDto StubQuestionDto()
        {
            return _Fixture.Build<QuestionDto>()
                .Without(o => o.Id)
                .Create();
        }
    }
}