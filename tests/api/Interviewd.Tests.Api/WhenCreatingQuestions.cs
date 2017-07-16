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
    public class WhenCreatingQuestions : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateAQuestion2()
        {
            var question = new Question
            {
                Name = "test",
                Description = "test"
            };

            var httpResponseMessage = await HttpClient.PostAsync(
                ApiRoutes.QuestionsRoute,
                question.ToStringContent());

            httpResponseMessage.IsSuccessStatusCode.ShouldBeTrue();
        }
    }
}