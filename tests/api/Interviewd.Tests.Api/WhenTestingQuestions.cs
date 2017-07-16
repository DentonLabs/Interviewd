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
            var expectedQuestion = Fixture.Create<Question>();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                expectedQuestion.ToStringContent());

            var actualQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<Question>();

            Assert.AreEqual(actualQuestion, expectedQuestion);
        }
    }
}