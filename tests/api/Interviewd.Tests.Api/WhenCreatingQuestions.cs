using System.Threading.Tasks;
using Newtonsoft.Json;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Ploeh.SemanticComparison;
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

            var content = await httpResponseMessage.Content.ReadAsStringAsync();
            var createdQuestion = JsonConvert.DeserializeObject<Question>(content);

            var expectedQuestion = new Likeness<Question, Question>(question)
                .Without(q => q.Id);

            Assert.AreEqual(expectedQuestion, createdQuestion);
        }
    }
}