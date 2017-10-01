using System;
using System.Net.Http;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Configuration;
using Marvin.JsonPatch;
using Microsoft.Extensions.Options;

namespace Interviewd.Tests.Api
{
    public class ApiClient
    {
        private readonly HttpClient _HttpClient;

        public ApiClient(IOptions<AppSettings> appSettings)
        {
            _HttpClient = new HttpClient();
            _HttpClient.BaseAddress = new Uri(appSettings.Value.ApiUri);
        }

        public async Task<HttpResponseMessage> PostQuestion(QuestionDto questionDto)
        {
            return await _HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                questionDto.ToStringContent());
        }

        public async Task<HttpResponseMessage> PatchQuestion(string id, JsonPatchDocument<QuestionDto> patchRequest)
        {
            return await _HttpClient.PatchAsync(
                $"{ApiRoutes.QuestionsRoute}/{id}",
                patchRequest.Operations.ToStringContent());
        }

        public async Task<HttpResponseMessage> GetQuestion(string id)
        {
            return await _HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}/{id}");
        }

        public async Task<HttpResponseMessage> GetAllQuestions()
        {
            return await _HttpClient.GetAsync(
                $"{ApiRoutes.QuestionsRoute}");
        }

        public async Task<HttpResponseMessage> PostInterviewTemplate(InterviewTemplateDto interviewTemplateDto)
        {
            return await _HttpClient.PostAsync(
                ApiRoutes.InterviewTemplatesRoute,
                interviewTemplateDto.ToStringContent());
        }

        public async Task<HttpResponseMessage> GetInterviewTemplate(string id)
        {
            return await _HttpClient.GetAsync($"{ApiRoutes.InterviewTemplatesRoute}/{id}");
        }

        public async Task<HttpResponseMessage> GetInterviewTemplates()
        {
            return await _HttpClient.GetAsync(ApiRoutes.InterviewTemplatesRoute);
        }

        public async Task<HttpResponseMessage> PatchInterviewTemplate(
            string id,
            JsonPatchDocument<InterviewTemplateDto> patchRequest)
        {
            return await _HttpClient.PatchAsync(
                $"{ApiRoutes.InterviewTemplatesRoute}/{id}",
                patchRequest.ToStringContent());
        }

        public async Task<HttpResponseMessage> PostInterview(InterviewDto interviewDto, string interviewTemplateId = null, string candidateId = null)
        {
            var queryStringBuilder = new QueryStringBuilder();

            if (interviewTemplateId != null)
            {
                queryStringBuilder.AddParameter("templateId", interviewTemplateId);
            }

            if (candidateId != null)
            {
                queryStringBuilder.AddParameter("candidateId", candidateId);
            }

            return await _HttpClient.PostAsync(
                $"{ApiRoutes.InterviewsRoute}{queryStringBuilder}",
                interviewDto.ToStringContent());
        }

        public async Task<HttpResponseMessage> GetInterview(string id)
        {
            return await _HttpClient.GetAsync(
                $"{ApiRoutes.InterviewsRoute}/{id}");
        }

        public async Task<HttpResponseMessage> GetAllInterviews()
        {
            return await _HttpClient.GetAsync(ApiRoutes.InterviewsRoute);
        }

        public async Task<HttpResponseMessage> PostCandidate(CandidateDto candidateDto)
        {
            return await _HttpClient.PostAsync(
                ApiRoutes.CandidatesRoute,
                candidateDto.ToStringContent());
        }

        public async Task<HttpResponseMessage> GetAllCandidates()
        {
            return await _HttpClient.GetAsync(
                ApiRoutes.CandidatesRoute);
        }

        public async Task<HttpResponseMessage> PatchCandidate(string id, JsonPatchDocument<CandidateDto> patchRequest)
        {
            return await _HttpClient.PatchAsync(
                $"{ApiRoutes.CandidatesRoute}/{id}",
                patchRequest.ToStringContent());
        }

        public async Task<HttpResponseMessage> GetCandidate(string id)
        {
            return await _HttpClient.GetAsync(
                $"{ApiRoutes.CandidatesRoute}/{id}");
        }
    }
}