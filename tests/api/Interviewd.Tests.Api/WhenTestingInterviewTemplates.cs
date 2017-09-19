﻿using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Jmansar.SemanticComparisonExtensions;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;

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

            var questions = await Arranger.CreateQuestions();
            requestInterviewTemplate.QuestionIds = questions.Select(q => q.Id);

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.InterviewTemplatesRoute,
                requestInterviewTemplate.ToStringContent());

            var apiInterviewTemplate = (await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<InterviewTemplateDto>())
                .WithCollectionSequenceEquals(o => o.QuestionIds);

            apiInterviewTemplate.ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterviewTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var responseInterviewTemplate = 
                (await (await HttpClient.GetAsync($"{ApiRoutes.InterviewTemplatesRoute}/{dbInterviewTemplate.Id}"))
                    .EnsureSuccessStatusCode()
                    .GetLikenessContent<InterviewTemplateDto>())
                    .WithCollectionSequenceEquals(o => o.QuestionIds);

            var dbInterviewTemplateDto = Mapper.Map<InterviewTemplateDto>(dbInterviewTemplate);

            responseInterviewTemplate.ShouldEqual(dbInterviewTemplateDto);
        }
    }
}