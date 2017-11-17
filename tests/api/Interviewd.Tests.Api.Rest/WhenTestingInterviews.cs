using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Tests.Api.Rest.LikenessExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api.Rest
{
    public class WhenTestingInterviews : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnEmptyInterview()
        {
            var requestInterview = Stubber.StubInterviewDto();

            var responseInterview = await ApiClient.PostInterview(requestInterview)
                .AwaitGetSuccessfulResponse<InterviewDto>();

            Assert.IsNotNull(responseInterview.Id);
        }
        
        [Test]
        public async Task ShouldBeAbleToCreateInterviewFromTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var requestInterview = Stubber.StubInterviewDto();

            var responseInterview = await ApiClient.PostInterview(requestInterview, dbInterviewTemplate.Id)
                .AwaitGetSuccessfulResponse<InterviewDto>();

            var dbInterviewTemplateDto = Mapper.Map<IEnumerable<QuestionDto>>(dbInterviewTemplate.Questions);

            Assert.IsTrue(responseInterview.Questions.CompareCollectionsUsingLikeness(dbInterviewTemplateDto));
        }

        [Test]
        public async Task ShouldBeAbleToCreateInterviewWithCandidate()
        {
            var requestInterviewDto = Stubber.StubInterviewDto();

            var dbCandidate = await Arranger.CreateCandidate();

            requestInterviewDto.Candidate = Mapper.Map<CandidateDto>(dbCandidate);

            var responseInterviewDto = await ApiClient.PostInterview(requestInterviewDto, candidateId: dbCandidate.Id)
                .AwaitGetSuccessfulResponse<InterviewDto>();

            responseInterviewDto
                .ToLikeness()
                .WithInnerLikeness(o => o.Candidate, o => o.Candidate)
                .ShouldEqual(requestInterviewDto);
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterview()
        {
            var dbInterview = await Arranger.CreateInterview();

            var responseInterviewDto = await ApiClient.GetInterview(dbInterview.Id) 
                .AwaitGetSuccessfulResponse<InterviewDto>();

            var dbInterviewDto = Mapper.Map<InterviewDto>(dbInterview);

            responseInterviewDto
                .ToLikeness(true)
                .WithInnerLikeness(o => o.Candidate, o => o.Candidate)
                .WithCollectionInnerLikeness(
                    o => o.Questions,
                    o => o.Questions)
                .ShouldEqual(dbInterviewDto);
        }

        [Test]
        public async Task ShouldBeAbleToGetAllInterviews()
        {
            var dbInterviews = await Arranger.CreateInterviews();

            var responseInterviewDtos = await ApiClient.GetAllInterviews()
                .AwaitGetSuccessfulResponse<IEnumerable<InterviewDto>>();

            responseInterviewDtos = responseInterviewDtos.Where(i => dbInterviews.Any(di => di.Id == i.Id));

            Assert.IsTrue(responseInterviewDtos.CompareCollectionsUsingLikeness(
                Mapper.Map<IEnumerable<InterviewDto>>(dbInterviews),
                i => i
                    .WithInnerLikeness(o => o.Candidate, o => o.Candidate)
                    .Without(o => o.Questions)));
        }
    }
}