using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using Dapper;
using Interviewd.Configuration;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Microsoft.Extensions.Options;

namespace Interviewd.Infrastructure
{
    public class QuestionRepository : IQuestionRepository
    {
        private readonly AppSettings _AppSettings;

        private readonly IMapper _Mapper;

        public QuestionRepository(
            IOptions<AppSettings> appSettings,
            IMapper mapper)
        {
            _AppSettings = appSettings.Value;
            _Mapper = mapper;
        }

        public async Task<Question> InsertQuestion(Question question)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var parameters = new DynamicParameters(
                    new
                    {
                        Name = question.Name,
                        Description = question.Description
                    });

                parameters.Add("Id", dbType: DbType.Int32, direction: ParameterDirection.Output);

                await connection.ExecuteAsync(
                    StoredProcedures.InsertQuestion,
                    parameters,
                    commandType: CommandType.StoredProcedure);

                question.Id = parameters.Get<int>("Id").ToString();

                return question;
            }
        }

        public async Task<IEnumerable<Question>> GetQuestions()
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var questionSqlModels = await connection.QueryAsync<QuestionSqlModel>(StoredProcedures.GetQuestions);

                return questionSqlModels.Select(q => 
                    new Question
                    {
                        Id = q.Id,
                        Name = q.Name,
                        Description = q.Description
                    });
            }
        }

        public async Task<Question> GetQuestion(string id)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var questionSqlModels = await connection.QueryAsync<QuestionSqlModel>(
                    StoredProcedures.GetQuestion,
                    new
                    {
                        Id = id
                    },
                    commandType: CommandType.StoredProcedure);

                return questionSqlModels.Select(q => 
                    new Question
                    {
                        Id = q.Id,
                        Name = q.Name,
                        Description = q.Description
                    })
                    .Single();
            }
        }

        public async Task<Question> UpdateQuestion(Question question)
        {
            using (var connection = new SqlConnection(_AppSettings.ConnectionStrings.DefaultConnection))
            {
                var questionSqlModel = _Mapper.Map<QuestionSqlModel>(question);

                await connection.ExecuteAsync(
                    StoredProcedures.UpdateQuestion,
                    questionSqlModel,
                    commandType: CommandType.StoredProcedure);

                return question;
            }
        }
    }
}