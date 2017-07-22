using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingQuestions : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion()
        {
            var expectedQuestion = Fixture.Create<Question>();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                expectedQuestion.ToStringContent());

            var actualQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<Question>();

            Assert.AreEqual(actualQuestion, expectedQuestion);
        }

        [Test]
        public async Task ShouldBeAbleToGetAQuestion()
        {
            var questionService = ServiceProvider.GetService<QuestionService>();

            var expectedQuestion = await questionService.InsertQuestion(Fixture.Create<Question>());

            var httpResponseMessage = await HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}/{expectedQuestion.Id}");

            var actualQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<Question>();

            Assert.AreEqual(actualQuestion, expectedQuestion);
        }
    }
}