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

            var responseQuestion = await ApiClient.PostQuestion(requestQuestion)
                .AwaitGetSuccessfulResponse<QuestionDto>();

            responseQuestion.ToLikeness().ShouldEqual(requestQuestion);
        }

        [Test]
        public async Task ShouldBeAbleToGetAQuestion()
        {
            var dbQuestion = await Arranger.CreateQuestion();

            var responseQuestionDto = await ApiClient.GetQuestion(dbQuestion.Id)
                .AwaitGetSuccessfulResponse<QuestionDto>();

            responseQuestionDto.ToLikeness().ShouldEqual(Mapper.Map<QuestionDto>(dbQuestion));
        }

        [Test]
        public async Task ShouldBeAbleToGetAllQuestions()
        {
            var dbQuestions = await Arranger.CreateQuestions();

            var responseQuestionDtos = await ApiClient.GetAllQuestions()
                .AwaitGetSuccessfulResponse<QuestionDto>();

            var responseQuestions = Mapper.Map<IEnumerable<Question>>(responseQuestionDtos);

            responseQuestions = responseQuestions.Where(rq => dbQuestions.Any(dq => dq.Id == rq.Id));

            Assert.IsTrue(responseQuestions.CompareCollectionsUsingLikeness(dbQuestions));
        }
    }
}