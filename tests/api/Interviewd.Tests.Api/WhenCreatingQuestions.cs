using System;
using System.Net.Http;
using System.Net.Http.Headers;
using NUnit.Framework;
using Shouldly;

namespace Interviewd.Tests.Api
{
    public class WhenCreatingQuestions
    {
        [Test]
        public void ShouldBeAbleToCreateAQuestion()
        {
            true.ShouldBeTrue();

            var httpClient = new HttpClient();
            httpClient.BaseAddress = new Uri("localhost:9005");
        }
    }
}