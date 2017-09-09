using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Jmansar.SemanticComparisonExtensions;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Shouldly;

namespace Interviewd.Tests.Api
{
    public class WhenTestingInterviews : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnEmptyInterview()
        {
            var requestInterview = 
                Fixture.Build<InterviewDto>()
                    .Without(o => o.Id)
                    .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewsRoute,
                requestInterview.ToStringContent());

            var responseInterview = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<InterviewDto>();

            Assert.IsNotNull(responseInterview.Id);
        }
        
        [Test]
        public async Task ShouldBeAbleToCreateInterviewFromTemplate()
        {
            var interviewTemplate =
                Fixture.Build<InterviewTemplate>()
                    .Without(o => o.Questions)
                    .Without(o => o.Id)
                    .Create();

            var questionRepository = ServiceProvider.GetService<IQuestionRepository>();
            var interviewTemplateRepository = ServiceProvider.GetService<IInterviewTemplateRepository>();

            interviewTemplate = await interviewTemplateRepository.InsertInterviewTemplate(interviewTemplate);

            var question1 = await questionRepository.InsertQuestion(Fixture.Create<Question>());
            var question2 = await questionRepository.InsertQuestion(Fixture.Create<Question>());

            await interviewTemplateRepository.InsertInterviewTemplateQuestions(
                interviewTemplate.Id,
                new List<string> { question1.Id, question2.Id });

            //

            var requestInterview = 
                Fixture.Build<InterviewDto>()
                    .Without(o => o.Id)
                    .Create();

            var httpResponseMessage = await HttpClient.PostAsync(
                $"{ApiRoutes.InterviewsRoute}?templateId={interviewTemplate.Id}",
                requestInterview.ToStringContent());

            var responseInterview = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<InterviewDto>();

            // Todo: refactor
            var interviewQuestionIds = responseInterview.Questions.Select(q => q.Id).ToList();
            interviewQuestionIds.Count.ShouldBe(2);
            interviewQuestionIds.ShouldContain(question1.Id);
            interviewQuestionIds.ShouldContain(question2.Id);
        }
    }
}