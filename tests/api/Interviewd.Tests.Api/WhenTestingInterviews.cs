using System.Threading.Tasks;
using Interviewd.Application.Dto;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    public class WhenTestingInterviews : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnEmptyInterview()
        {
            var requestInterviewTemplate = 
                Fixture.Build<InterviewDto>()
                    .Without(o => o.Id)
                    .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewsRoute,
                requestInterviewTemplate.ToStringContent());

            var responseInterview = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<InterviewDto>();

            Assert.IsNotNull(responseInterview.Id);
        }
    }
}