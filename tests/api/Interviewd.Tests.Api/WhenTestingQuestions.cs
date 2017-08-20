using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Ploeh.SemanticComparison.Fluent;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingQuestions : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion()
        {
            var requestQuestion = Fixture.Create<QuestionDto>();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                requestQuestion.ToStringContent());

            var apiQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<QuestionDto>();

            Assert.AreEqual(apiQuestion, requestQuestion);
        }

        [Test]
        public async Task ShouldBeAbleToGetAQuestion()
        {
            var questionRepository = ServiceProvider.GetService<IQuestionRepository>();

            var dbQuestion = await questionRepository.InsertQuestion(Fixture.Create<Question>());

            var httpResponseMessage = await HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}/{dbQuestion.Id}");

            var responseQuestionDto = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<QuestionDto>();

            var responseQuestion = Mapper.Map<Question>(responseQuestionDto)
                .AsSource()
                .OfLikeness<Question>();

            responseQuestion.ShouldEqual(dbQuestion);
        }
    }
}