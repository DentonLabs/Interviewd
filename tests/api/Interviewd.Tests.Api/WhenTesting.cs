using System;
using System.Net.Http;
using AutoMapper;
using Interviewd.Configuration;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using NUnit.Framework;
using Ploeh.AutoFixture;
using Interviewd.Infrastructure.Abstraction;
using Interviewd.Infrastructure;
using Interview.Mapping;

namespace Interviewd.Tests.Api
{
    public class WhenTesting
    {
        protected HttpClient HttpClient;

        protected Fixture Fixture;

        protected IServiceProvider ServiceProvider;

        protected IMapper Mapper;

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
                .AddSingleton<IInterviewTemplateRepository, InterviewTemplateRepository>()
                .AddSingleton<IMapper>(new Mapper(new MapperConfiguration(c => c.AddProfile(new MappingProfile()))))
                .BuildServiceProvider();

            var appSettings = ServiceProvider.GetService<IOptions<AppSettings>>().Value;

            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri(appSettings.ApiUri);

            Fixture = new Fixture();

            var config = new MapperConfiguration(cfg =>
            {
                cfg.AddProfile<MappingProfile>();
            });

            Mapper = config.CreateMapper();
        }
    }
}