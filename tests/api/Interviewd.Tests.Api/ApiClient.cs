﻿using System;
using System.Net.Http;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Configuration;
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
    }
}