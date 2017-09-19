using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Jmansar.SemanticComparisonExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingInterviewTemplates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithoutQuestions()
        {
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var responseInterviewTemplate = await ApiClient.PostInterviewTemplate(requestInterviewTemplate)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            responseInterviewTemplate.ToLikeness().ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithQuestions()
        {
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var questions = await Arranger.CreateQuestions();
            requestInterviewTemplate.QuestionIds = questions.Select(q => q.Id);

            var responseInterviewTemplate = await ApiClient.PostInterviewTemplate(requestInterviewTemplate)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            responseInterviewTemplate
                .ToLikeness()
                .WithCollectionSequenceEquals(o => o.QuestionIds)
                .ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterviewTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var responseInterviewTemplate = await ApiClient.GetInterviewTemplate(dbInterviewTemplate.Id)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            var dbInterviewTemplateDto = Mapper.Map<InterviewTemplateDto>(dbInterviewTemplate);

            responseInterviewTemplate
                .ToLikeness()
                .WithCollectionSequenceEquals(o => o.QuestionIds)
                .ShouldEqual(dbInterviewTemplateDto);
        }
    }
}