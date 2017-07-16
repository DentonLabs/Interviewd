using System;
using System.Net.Http;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    public class WhenTesting
    {
        protected readonly HttpClient HttpClient;

        protected readonly Fixture Fixture;

        public WhenTesting()
        {
            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri("http://localhost:9005");
            Fixture = new Fixture();
        }
    }
}