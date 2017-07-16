using System;
using System.Net.Http;

namespace Interviewd.Tests.Api
{
    public class WhenTesting
    {
        protected readonly HttpClient HttpClient;

        public WhenTesting()
        {
            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri("http://localhost:9005");
        }
    }
}