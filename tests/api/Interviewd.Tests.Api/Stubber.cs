using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Marvin.JsonPatch;
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

        public JsonPatchDocument<QuestionDto> StubQuestionPatchRequest()
        {
            var patchRequest = new JsonPatchDocument<QuestionDto>();
            patchRequest.Replace(o => o.Name, _Fixture.Create<string>());
            patchRequest.Replace(o => o.Description, _Fixture.Create<string>());
            return patchRequest;
        }

        public JsonPatchDocument<CandidateDto> StubCandidatePatchRequest()
        {
            var patchRequest = new JsonPatchDocument<CandidateDto>();
            patchRequest.Replace(o => o.GivenName, _Fixture.Create<string>());
            patchRequest.Replace(o => o.Surname, _Fixture.Create<string>());
            return patchRequest;
        }

        public InterviewTemplateDto StubInterviewTemplateDto()
        {
            return _Fixture.Build<InterviewTemplateDto>()
                .Without(o => o.QuestionIds)
                .Without(o => o.Id)
                .Create();
        }

        public InterviewDto StubInterviewDto()
        {
            return _Fixture.Build<InterviewDto>()
                    .Without(o => o.Id)
                    .Without(o => o.Candidate)
                    .Without(o => o.Questions)
                    .Create();
        }
    }
}