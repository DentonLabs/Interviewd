using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Tests.Api.Rest.LikenessExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api.Rest.Tests
{
    [TestFixture]
    public class WhenTestingInterviewTemplates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithoutQuestions()
        {
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var responseInterviewTemplate = await ApiClient.PostInterviewTemplate(requestInterviewTemplate)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            responseInterviewTemplate.ToLikeness().ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToCreateAnInterviewTemplateWithQuestions()
        {
            var requestInterviewTemplate = Stubber.StubInterviewTemplateDto();

            var questions = await Arranger.CreateQuestions();
            requestInterviewTemplate.QuestionIds = questions.Select(q => q.Id);

            var responseInterviewTemplate = await ApiClient.PostInterviewTemplate(requestInterviewTemplate)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            responseInterviewTemplate
                .ToLikeness()
                .WithCollectionSequenceEquals(o => o.QuestionIds)
                .ShouldEqual(requestInterviewTemplate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAnInterviewTemplate()
        {
            var dbInterviewTemplate = await Arranger.CreateInterviewTemplate();

            var responseInterviewTemplate = await ApiClient.GetInterviewTemplate(dbInterviewTemplate.Id)
                .AwaitGetSuccessfulResponse<InterviewTemplateDto>();

            var dbInterviewTemplateDto = Mapper.Map<InterviewTemplateDto>(dbInterviewTemplate);

            responseInterviewTemplate
                .ToLikeness(true)
                .WithCollectionSequenceEquals(o => o.QuestionIds)
                .ShouldEqual(dbInterviewTemplateDto);
        }

        [Test]
        public async Task ShouldBeAbleToPatchAnInterviewTemplate()
        {
            var interviewTemplateDto = Mapper.Map<InterviewTemplateDto>(await Arranger.CreateInterviewTemplate());

            var patchRequest = Stubber.StubInterviewTemplatePatchRequest();
            patchRequest.ApplyTo(interviewTemplateDto);

            (await ApiClient.PatchInterviewTemplate(interviewTemplateDto.Id, patchRequest))
                .EnsureSuccessStatusCode();

            var updatedInterviewTemplateDto = Mapper.Map<InterviewTemplateDto>(
                await Arranger.GetInterviewTemplate(interviewTemplateDto.Id));

            SemanticComparisonExtensions.ToLikeness<InterviewTemplateDto>(interviewTemplateDto)
                .Without(o => o.QuestionIds)
                .ShouldEqual(updatedInterviewTemplateDto);
        }

        [Test]
        public async Task ShouldBeAbleToGetAllInterviewTemplates()
        {
            var dbInterviewTemplates = await Arranger.CreateInterviewTemplates();

            var responseInteviewTemplateDtos = await ApiClient.GetInterviewTemplates()
                .AwaitGetSuccessfulResponse<IEnumerable<InterviewTemplateDto>>();

            responseInteviewTemplateDtos
                .CompareCollectionsUsingLikeness<InterviewTemplateDto, InterviewTemplateDto>(
                    Mapper.Map<IEnumerable<InterviewTemplateDto>>(dbInterviewTemplates));
        }
    }
}