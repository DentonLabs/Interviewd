using System;
using System.Net.Http;
using Interviewd.Configuration;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Interviewd.Infrastructure.Abstraction;
using Interviewd.Infrastructure;

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
                .AddSingleton<IQuestionRepository, QuestionRepository>()
                .BuildServiceProvider();

            var appSettings = ServiceProvider.GetService<IOptions<AppSettings>>().Value;

            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri(appSettings.ApiUri);

            Fixture = new Fixture();
        }
    }
}