using System.Threading.Tasks;
using Newtonsoft.Json;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Ploeh.SemanticComparison;
using Ploeh.SemanticComparison.Fluent;
using Shouldly;

namespace Interviewd.Tests.Api
{
    public class WhenCreatingQuestions : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion()
        {
            var question = Fixture.Create<Question>();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                question.ToStringContent());

            httpResponseMessage.IsSuccessStatusCode.ShouldBeTrue();

            var expectedQuestion = await httpResponseMessage.GetLikenessContent<Question>();

            Assert.AreEqual(expectedQuestion, question);
        }
    }
}