using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Jmansar.SemanticComparisonExtensions;
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
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var httpResponseMessage = await ApiClient.PostInterviewTemplate(requestInterviewTemplate);

            var responseInterviewTemplate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>();

            responseInterviewTemplate.ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithQuestions()
        {
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var questions = await Arranger.CreateQuestions();
            requestInterviewTemplate.QuestionIds = questions.Select(q => q.Id);

            var httpResponseMessage = await ApiClient.PostInterviewTemplate(requestInterviewTemplate);

            var responseInterviewTemplate = (await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>())
                .WithCollectionSequenceEquals(o => o.QuestionIds);

            responseInterviewTemplate.ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterviewTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var httpResponseMessage = await ApiClient.GetInterviewTemplate(dbInterviewTemplate.Id);

            var responseInterviewTemplate = 
                (await httpResponseMessage
                    .EnsureSuccessStatusCode()
                    .GetLikenessContent<InterviewTemplateDto>())
                    .WithCollectionSequenceEquals(o => o.QuestionIds);

            var dbInterviewTemplateDto = Mapper.Map<InterviewTemplateDto>(dbInterviewTemplate);

            responseInterviewTemplate.ShouldEqual(dbInterviewTemplateDto);
        }
    }
}