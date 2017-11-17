using System;
using System.Net.Http;
using AutoMapper;
using Interviewd.Configuration;
using Interviewd.Infrastructure;
using Interviewd.Infrastructure.Abstraction;
using Interviewd.Mapping;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api.Rest
{
    public class WhenTesting
    {
        protected HttpClient HttpClient;

        protected Fixture _Fixture;

        protected IServiceProvider ServiceProvider;

        protected IMapper Mapper;

        protected Arranger Arranger;

        protected Stubber Stubber;

        protected ApiClient ApiClient;

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
                .AddSingleton<IInterviewRepository, InterviewRepository>()
                .AddSingleton<IInterviewTemplateRepository, InterviewTemplateRepository>()
                .AddSingleton<ICandidateRepository, CandidateRepository>()
                .AddSingleton<IMapper>(new Mapper(new MapperConfiguration(c => c.AddProfile(new MappingProfile()))))
                .AddSingleton<Arranger, Arranger>()
                .AddSingleton<Stubber, Stubber>()
                .AddSingleton<ApiClient, ApiClient>()
                .BuildServiceProvider();

            var appSettings = ServiceProvider.GetService<IOptions<AppSettings>>().Value;

            HttpClient = new HttpClient();
            HttpClient.BaseAddress = new Uri(appSettings.ApiUri);

            Arranger = ServiceProvider.GetService<Arranger>();
            Stubber = ServiceProvider.GetService<Stubber>();
            ApiClient = ServiceProvider.GetService<ApiClient>();

            _Fixture = new Fixture();

            var config = new MapperConfiguration(cfg =>
            {
                cfg.AddProfile<MappingProfile>();
            });

            Mapper = config.CreateMapper();
        }
    }
}