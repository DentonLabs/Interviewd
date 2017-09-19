using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Jmansar.SemanticComparisonExtensions;
using NUnit.Framework;
using Ploeh.SemanticComparison.Fluent;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingQuestions : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion()
        {
            var requestQuestion = Stubber.StubQuestionDto();

            var httpResponseMessage = await ApiClient.PostQuestion(requestQuestion);

            var responseQuestion = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<QuestionDto>();

            responseQuestion.ShouldEqual(requestQuestion);
        }

        [Test]
        public async Task ShouldBeAbleToGetAQuestion()
        {
            var dbQuestion = await Arranger.CreateQuestion();

            var httpResponseMessage = await ApiClient.GetQuestion(dbQuestion.Id);

            var responseQuestionDto = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<QuestionDto>();

            responseQuestionDto.ShouldEqual(Mapper.Map<QuestionDto>(dbQuestion));
        }

        [Test]
        public async Task ShouldBeAbleToGetAllQuestions()
        {
            var dbQuestions = await Arranger.CreateQuestions();

            var httpResponseMessage = await ApiClient.GetAllQuestions();

            var responseQuestionDtos = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<IEnumerable<QuestionDto>>();

            var responseQuestions = Mapper.Map<IEnumerable<Question>>(responseQuestionDtos);

            responseQuestions = responseQuestions.Where(rq => dbQuestions.Any(dq => dq.Id == rq.Id));

            Assert.IsTrue(responseQuestions.CompareCollectionsUsingLikeness(dbQuestions));
        }
    }
}