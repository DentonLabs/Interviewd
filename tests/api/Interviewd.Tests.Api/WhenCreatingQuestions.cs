using System;
using System.Net.Http;
using System.Net.Http.Headers;
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
            true.ShouldBeTrue();

            var httpClient = new HttpClient();
            var question = new Question
            {
                Description = "test"
            };

            var body = JsonConvert.SerializeObject(question);

            httpClient.BaseAddress = new Uri("localhost:9005");
            var httpResponseMessage = await httpClient.PostAsync(
                "questions",
                new StringContent(body));

            httpResponseMessage.IsSuccessStatusCode.ShouldBeTrue();
        }
    }
}