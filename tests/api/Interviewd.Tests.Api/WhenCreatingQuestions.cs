using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using NUnit.Framework;
using Shouldly;

namespace Interviewd.Tests.Api
{
    public class WhenCreatingQuestions
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion()
        {
            var httpClient = new HttpClient();
            var question = new Question
            {
                Name = "test",
                Description = "test"
            };

            var body = JsonConvert.SerializeObject(question);

            httpClient.BaseAddress = new Uri("http://localhost:9005");
            var httpResponseMessage = await httpClient.PostAsync(
                "questions",
                new StringContent(body, Encoding.UTF8, "application/json"));

            httpResponseMessage.IsSuccessStatusCode.ShouldBeTrue();
        }

        [Test]
        public async Task ShouldBeAbleToCreateAQuestion2()
        {
            var httpClient = new HttpClient();
            var question = new Question
            {
                Name = "test",
                Description = "test"
            };

            httpClient.BaseAddress = new Uri("http://localhost:9005");

            var httpResponseMessage = await httpClient.PostAsync(
                "questions",
                question.ToStringContent());

            httpResponseMessage.IsSuccessStatusCode.ShouldBeTrue();
        }
    }
}