using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Jmansar.SemanticComparisonExtensions;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    public class WhenTestingInterviews : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnEmptyInterview()
        {
            var requestInterview = Stubber.StubInterviewDto();

            var httpResponseMessage = await ApiClient.PostInterview(requestInterview);

            var responseInterview = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<InterviewDto>();

            Assert.IsNotNull(responseInterview.Id);
        }
        
        [Test]
        public async Task ShouldBeAbleToCreateInterviewFromTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var requestInterview = Stubber.StubInterviewDto();

            var httpResponseMessage = await ApiClient.PostInterview(requestInterview, dbInterviewTemplate.Id);

            var responseInterview = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<InterviewDto>();

            var dbInterviewTemplateDto = Mapper.Map<IEnumerable<QuestionDto>>(dbInterviewTemplate.Questions);

            Assert.IsTrue(responseInterview.Questions.CompareCollectionsUsingLikeness(dbInterviewTemplateDto));
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterview()
        {
            var dbInterview = await Arranger.CreateInterview();

            var httpResponseMessage = await ApiClient.GetInterview(dbInterview.Id);

            var responseInterviewDto = (await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewDto>())
                .WithCollectionInnerLikeness(
                    o => o.Questions,
                    o => o.Questions);

            var test = Mapper.Map<InterviewDto>(dbInterview);

            responseInterviewDto.ShouldEqual(test);
        }
    }
}