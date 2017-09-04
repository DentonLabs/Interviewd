using AutoMapper;
using Interviewd.Application;
using Interviewd.Configuration;
using Interviewd.Domain;
using Interviewd.Infrastructure;
using Interviewd.Infrastructure.Abstraction;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace Interviewd
{
    public class Startup
    {
        public IConfigurationRoot Configuration { get; }

        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appSettings.json", optional: true, reloadOnChange: true);

            Configuration = builder.Build();
        }

        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit http://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddOptions();

            services.Configure<AppSettings>(Configuration);

            services.AddMvc();
            services.AddAutoMapper();

            services.AddSingleton<IInterviewTemplateManager, InterviewTemplateManager>();
            services.AddSingleton<IInterviewTemplateRepository, InterviewTemplateRepository>();
            services.AddSingleton<IQuestionManager, QuestionManager>();
            services.AddSingleton<IQuestionRepository, QuestionRepository>();
            services.AddSingleton<ICandidateManager, CandidateManager>();
            services.AddSingleton<ICandidateRepository, CandidateRepository>();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory)
        {
            loggerFactory.AddConsole();

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseMvc();
        }
    }
}
