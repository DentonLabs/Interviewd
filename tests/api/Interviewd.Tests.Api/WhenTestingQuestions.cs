using System.Threading.Tasks;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
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
            var requestQuestion = Fixture.Create<Question>();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                requestQuestion.ToStringContent());

            var apiQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<Question>();

            Assert.AreEqual(apiQuestion, requestQuestion);
        }

        [Test]
        public async Task ShouldBeAbleToGetAQuestion()
        {
            var questionRepository = ServiceProvider.GetService<IQuestionRepository>();

            var dbQuestion = await questionRepository.InsertQuestion(Fixture.Create<Question>());

            var httpResponseMessage = await HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}/{dbQuestion.Id}");

            var apiQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<Question>();

            Assert.AreEqual(apiQuestion, dbQuestion);
        }
    }
}