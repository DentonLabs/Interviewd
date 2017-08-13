using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Jmansar.SemanticComparisonExtensions;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Ploeh.SemanticComparison.Fluent;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingInterviewTemplates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithoutQuestions()
        {
            var requestInterviewTemplate = 
                Fixture.Build<InterviewTemplateDto>()
                    .Without(o => o.QuestionIds)
                    .Without(o => o.Id)
                    .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewTemplatesRoute,
                requestInterviewTemplate.ToStringContent());

            var apiInterviewTemplate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>();

            Assert.AreEqual(apiInterviewTemplate, requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithQuestions()
        {
            var requestInterviewTemplate = 
                Fixture.Build<InterviewTemplateDto>()
                    .Without(o => o.QuestionIds)
                    .Without(o => o.Id)
                    .Create();

            var questionRepository = ServiceProvider.GetService<IQuestionRepository>();

            var question1 = await questionRepository.InsertQuestion(Fixture.Create<Question>());
            var question2 = await questionRepository.InsertQuestion(Fixture.Create<Question>());

            requestInterviewTemplate.QuestionIds = new List<string> { question1.Id, question2.Id };

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewTemplatesRoute,
                requestInterviewTemplate.ToStringContent());

            var apiInterviewTemplate = (await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>())
                .WithCollectionSequenceEquals(o => o.QuestionIds);

            apiInterviewTemplate.ShouldEqual(requestInterviewTemplate);
        }
    }
}