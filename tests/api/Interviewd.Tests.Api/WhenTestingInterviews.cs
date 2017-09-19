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
        public async Task ShouldBeAbleToGetAnInterview()
        {
            var dbInterview = await Arranger.CreateInterview();

            var responseInterviewDto = await ApiClient.GetInterview(dbInterview.Id) 
                .AwaitGetSuccessfulResponse<InterviewDto>();

            var dbInterviewDto = Mapper.Map<InterviewDto>(dbInterview);

            responseInterviewDto
                .ToLikeness()
                .WithCollectionInnerLikeness(
                    o => o.Questions,
                    o => o.Questions)
                .ShouldEqual(dbInterviewDto);
        }
    }
}