using System.Threading.Tasks;
using Interviewd.Application.Dto;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingInterviewTemplates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithoutQuestions()
        {
            var requestInterviewTemplate = 
                Fixture.Build<InterviewTemplateDto>()
                    .Without(o => o.QuestionIds)
                    .Without(o => o.Id)
                    .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewTemplatesRoute,
                requestInterviewTemplate.ToStringContent());

            var apiInterviewTemplate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>();

            Assert.AreEqual(apiInterviewTemplate, requestInterviewTemplate);
        }
    }
}