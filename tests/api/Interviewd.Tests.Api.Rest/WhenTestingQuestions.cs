using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Tests.Api.Rest.LikenessExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api.Rest
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

            responseQuestionDto.ToLikeness(true).ShouldEqual(Mapper.Map<QuestionDto>(dbQuestion));
        }

        [Test]
        public async Task ShouldBeAbleToGetAllQuestions()
        {
            var dbQuestions = await Arranger.CreateQuestions();

            var responseQuestionDtos = await ApiClient.GetAllQuestions()
                .AwaitGetSuccessfulResponse<IEnumerable<QuestionDto>>();

            var responseQuestions = Mapper.Map<IEnumerable<Question>>(responseQuestionDtos);

            responseQuestions = responseQuestions.Where(rq => dbQuestions.Any(dq => dq.Id == rq.Id));

            Assert.IsTrue(responseQuestions.CompareCollectionsUsingLikeness(dbQuestions));
        }

        [Test]
        public async Task ShouldBeABleToUpdateQuestion()
        {
            var questionDto = Mapper.Map<QuestionDto>(await Arranger.CreateQuestion());

            var questionPatchRequest = Stubber.StubQuestionPatchRequest();
            questionPatchRequest.ApplyTo(questionDto);

            await ApiClient.PatchQuestion(questionDto.Id, questionPatchRequest);

            var updatedQuestionDto = Mapper.Map<QuestionDto>(await Arranger.GetQuestion(questionDto.Id));

            questionDto.ToLikeness(true).ShouldEqual(updatedQuestionDto);
        }
    }
}