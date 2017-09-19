using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DeepEqual.Syntax;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Jmansar.SemanticComparisonExtensions;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Ploeh.SemanticComparison.Fluent;
using Shouldly;

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
            var dbQuestion = await Arranger.CreateQuestion();

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

        [Test]
        public async Task ShouldBeAbleToGetAllQuestions()
        {
            var dbQuestions = await Arranger.CreateQuestions();

            var httpResponseMessage = await HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}");

            var responseQuestionDtos = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<IEnumerable<QuestionDto>>();

            var responseQuestions = Mapper.Map<IEnumerable<Question>>(responseQuestionDtos);

            responseQuestions = responseQuestions.Where(rq => dbQuestions.Any(dq => dq.Id == rq.Id));

            Assert.IsTrue(responseQuestions.CompareCollectionsUsingLikeness(dbQuestions));
        }
    }
}