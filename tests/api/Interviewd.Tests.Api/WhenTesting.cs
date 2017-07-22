using System;
using System.IO;
using System.Net.Http;
using System.Reflection;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTesting
    {
        protected HttpClient HttpClient;

        protected Fixture Fixture;

        protected IServiceProvider ServiceProvider;

        [OneTimeSetUp]
        public void Setup()
        {
            var builder = new ConfigurationBuilder()
                .AddJsonFile("appSettings.json");

            var configuration = builder.Build();

            ServiceProvider = new ServiceCollection()
                .AddOptions()
                .Configure<AppSettings>(configuration)
                .AddSingleton<QuestionService>()
                .BuildServiceProvider();

            var appSettings = ServiceProvider.GetService<IOptions<AppSettings>>().Value;

            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri(appSettings.ApiUrl);

            Fixture = new Fixture();
        }
    }
}